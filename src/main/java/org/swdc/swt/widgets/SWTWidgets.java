package org.swdc.swt.widgets;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.tools.GroovyClass;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.ViewRequire;
import org.swdc.swt.layouts.LayoutData;
import org.swdc.swt.layouts.SWTFormData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;


public class SWTWidgets {

    private static final Display display = new Display();
    private static final FormToolkit toolkit = new FormToolkit(SWTWidgets.getDisplay());
    private static final GroovyClassLoader loader = new GroovyClassLoader();

    private static final ConcurrentHashMap<String,Class> loadedViews = new ConcurrentHashMap<>();

    public static FormToolkit factory() {
        return toolkit;
    }

    public static boolean isFormAPI(Composite w) {
        boolean isFormWidget = w.getClass().getPackage().getName().contains("org.eclipse.ui.forms");
        if (w.getParent() == null && !isFormWidget) {
            return false;
        } else if (w.getParent() != null && !isFormWidget) {
            return isFormAPI(w.getParent());
        } else if (w.getParent() == null && isFormWidget) {
            return true;
        } else {
            return w.getParent() != null && isFormWidget;
        }
    }

    public static Display getDisplay() {
        return display;
    }

    public static SWTColor colorHex(String colorStr){
        int[] rgb = hex2RGB(colorStr);
        if (rgb == null || rgb.length < 3) {
            return null;
        }
        return new SWTColor(rgb[0],rgb[1],rgb[2]);
    }

    public String colorHex(int r,int g,int b){
        return String.format("#%02X%02X%02X", r,g,b);
    }

    public static SWTColor color(String color) {
        try {
            if (color.startsWith("#")) {
                return colorHex(color);
            } else if (color.toLowerCase().startsWith("rgb")) {

                color = color.toLowerCase();
                color = color.substring(color.indexOf("(") + 1, color.indexOf(")"));
                String[] rgb = color.split(",");

                if (rgb.length < 3) {
                    return null;
                }

                int r = Integer.parseInt(rgb[0]);
                int g = Integer.parseInt(rgb[1]);
                int b = Integer.parseInt(rgb[2]);

                return new SWTColor(r,g,b);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 16进制颜色字符串转换成rgb
     * @param hexStr
     * @return rgb
     */
    public static int[] hex2RGB(String hexStr){
        if(hexStr != null && !"".equals(hexStr) && hexStr.length() == 7){
            int[] rgb = new int[3];
            rgb[0] = Integer.valueOf(hexStr.substring( 1, 3 ), 16);
            rgb[1] = Integer.valueOf(hexStr.substring( 3, 5 ), 16);
            rgb[2] = Integer.valueOf(hexStr.substring( 5, 7 ), 16);
            return rgb;
        } else if (hexStr != null && !"".equals(hexStr) && hexStr.length() == 4) {
            int[] rgb = new int[3];
            rgb[0] = Integer.valueOf(hexStr.substring( 1, 2 ) + hexStr.substring( 1, 2 ), 16);
            rgb[1] = Integer.valueOf(hexStr.substring( 2, 3 ) + hexStr.substring( 2, 3 ), 16);
            rgb[2] = Integer.valueOf(hexStr.substring( 3, 4 ) + hexStr.substring( 3, 4 ), 16);
            return rgb;
        }
        return null;
    }

    /**
     * 初始化view的layout，在ready调用
     * 本方法即可。
     * @param widget 组件
     * @param target SWT的组件。
     */
    public static void setupLayoutData(SWTWidget widget, Control target) {
        // LayoutData
        if (widget.getLayoutData() != null) {
            LayoutData data = widget.getLayoutData();
            if (data instanceof SWTFormData) {
                SWTFormData formData = (SWTFormData) data;
                target.setLayoutData(formData.get(widget));
            } else {
                target.setLayoutData(data.get());
            }
        }
    }

    /**
     * 按照类名创建窗口Stage。
     * @param name 类名 java.lang.Class#getSimpleName
     * @param <T> Stage的继承类。
     * @return Stage对象。
     */
    public static <T extends Stage> T createStage(String name) {
        Class stageClass = loadedViews.get(name);
        if (stageClass == null) {
            throw new RuntimeException(new ClassNotFoundException());
        }
        if (!Stage.class.isAssignableFrom(stageClass)) {
            throw new RuntimeException(new ClassCastException());
        }
        try {
            return (T) stageClass
                    .getConstructor()
                    .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void loadViewsByAnnotation(Object object) {
        ViewRequire require = object.getClass().getAnnotation(ViewRequire.class);
        if (require == null) {
            return;
        }
        String[] loadingViews = require.value();
        for (String location: loadingViews) {
            try {
                SWTWidgets.loadFormResource(object.getClass().getModule(),location);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 根据类名创建view，创建的view可以
     * 放入任一groovy的组件中。
     *
     * @param name 类名Class#getSimpleName
     * @return SWTWidget组件
     */
    public static SWTWidget create(String name) {
        Class viewClazz = loadedViews.get(name);
        if (viewClazz == null) {
            throw new RuntimeException(new ClassNotFoundException());
        }
        try {
            return (SWTWidget) viewClazz
                    .getConstructor()
                    .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从Resources文件夹加载view。
     * @param mod JPMS的Module
     * @param path View在resources文件夹中的路径
     * @return 加载到的ViewClass
     * @throws Exception 创建失败
     */
    public static Class<SWTWidget> loadFormResource(Module mod,String path) throws Exception {
        Module self = SWTWidgets.class.getModule();

        if (!self.canRead(mod)) {
            throw new Exception("can not read resource");
        }

        InputStream in = mod.getResourceAsStream(path + ".groovy");
        Class viewClass = loader.parseClass(new BufferedReader(new InputStreamReader(in,StandardCharsets.UTF_8)),path);

        if (!SWTWidget.class.isAssignableFrom(viewClass)) {
            throw new Exception(path + " is not a groovy view");
        }

        loadedViews.put(viewClass.getSimpleName(),viewClass);

        return viewClass;
    }

}
