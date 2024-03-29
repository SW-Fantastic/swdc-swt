package org.swdc.swt.widgets;

import groovy.lang.GroovyClassLoader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.ViewRequire;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.layouts.LayoutData;
import org.swdc.swt.layouts.SWTFormData;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;


public class SWTWidgets {

    private static final Display display = new Display();
    private static final FormToolkit toolkit = new FormToolkit(SWTWidgets.getDisplay());
    private static final GroovyClassLoader loader = new GroovyClassLoader();

    private static List<String> loadedFonts = new ArrayList<>();
    private static Map<String, Image> loadedImage = new HashMap<>();

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


    /**
     * 在SWTProperty的methodName改变后，重新接入其在Controller中的
     * 对应的方法（Method）
     *
     * @param property 调用此方法的SWTProperty的实例本身
     * @param nameProperty 发生修改的MethodName的ObservableValue
     * @param widget 需要重新对接方法的Widget
     * @param methodGetter 一个函数，返回调用方内的现有的Method，这个method是之前的methodName指定的method。
     * @param methodSetter 一个函数，用于修改调用放的Method，修改为新的methodName所对应的method（由Customer参数传入）。
     * @param <R> Property类型、
     */
    public static  <R extends SWTProperty, E> void setupMethod(R property,
                                                     ObservableValue<String> nameProperty,
                                                     SWTWidget widget,
                                                     Function<R, Method> methodGetter,
                                                     Consumer<Method> methodSetter, Class<E> eventType) {
        if (widget == null ) {
            return;
        }
        if (widget.getLoader().getController(widget) == null) {
            return;
        }

        Object controller = widget.getController();
        String name = nameProperty.get();
        if (controller != null && nameProperty != null) {
            Class controllerClazz = controller.getClass();
            if (methodGetter.apply(property) == null) {
                try {
                    methodSetter.accept(controllerClazz.getMethod(name));
                } catch (Exception e) {
                    try {
                        methodSetter.accept(controllerClazz.getMethod(name, eventType));
                    } catch (Exception ex) {
                        throw new RuntimeException("找不到可用的方法：" + name);
                    }
                }
            }
        }
    }


    public static Image[] loadIcons(String path,String prefix) {

        Module mod = null;

        if (path == null || path.isEmpty()) {
            prefix = "java";
            path = "icons-d";
            mod = SWTWidgets.class.getModule();

        } else {

            StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
            Module self = SWTWidgets.class.getModule();
            Module caller = walker.getCallerClass().getModule();
            if(!self.canRead(caller)) {
                throw new RuntimeException("can not read module :" + caller.getName());
            }
            mod = caller;
        }


        try {
            return new Image[]{
                    new Image(display,mod.getResourceAsStream(path + "/" + prefix + "_128.png")),
                    new Image(display,mod.getResourceAsStream(path + "/" + prefix + "_64.png"))
            };

        } catch (Exception e) {
            return null;
        }
    }

    public static boolean loadFont(File file) {
        if (!file.exists()) {
            return false;
        }
        if(loadedFonts.contains(file.getAbsolutePath())) {
            return true;
        }
        boolean loaded = display.loadFont(file.getAbsolutePath());
        if (loaded) {
            loadedFonts.add(file.getAbsolutePath());
        }
        return loaded;
    }

    public static Font getFont(String family, int size) {
        return new Font(display,family,size, SWT.NONE);
    }

    public Image imageByFile(File file) {
        if (!file.exists()) {
            return null;
        }
        if (loadedImage.containsKey(file.getAbsolutePath())) {
            return loadedImage.get(file.getAbsolutePath());
        }
        Image image = new Image(display,file.getAbsolutePath());
        loadedImage.put(file.getAbsolutePath(),image);
        return image;
    }

    public static Image imageByStream(InputStream inputStream) {
        return new Image(display,inputStream);
    }

    public static Image imageByResource(String path) {

        if (loadedImage.containsKey(path)) {
            return loadedImage.get(path);
        }

        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        Class caller = walker.getCallerClass();

        Module self = SWTWidgets.class.getModule();
        Module callerModule = caller.getModule();

        try {
            if (self.canRead(callerModule)) {
                InputStream stream = callerModule.getResourceAsStream(path);
                if (stream != null) {
                    Image image = imageByStream(stream);
                    loadedImage.put(path,image);
                }
            } else {
                InputStream inputStream = self.getResourceAsStream(path);
                if (inputStream != null) {
                    Image image = imageByStream(inputStream);
                    loadedImage.put(path,image);
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
