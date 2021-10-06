package org.swdc.swt;

import groovy.lang.GroovyClassLoader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swdc.swt.layouts.SWTRowLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;
import org.swdc.swt.widgets.base.SWTView;
import org.swdc.swt.widgets.pane.SWTPane;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SWTViewLoader {

    private Logger logger = LoggerFactory.getLogger(SWTViewLoader.class);

    public static class DefaultControllerFactory implements ControllerFactory {

        @Override
        public Object createController(Class view) {
            ViewController controller =(ViewController) view.getAnnotation(ViewController.class);
            if (controller == null) {
                return null;
            }
            Class controllerClazz = controller.value();
            try {
                return controllerClazz
                        .getConstructor()
                        .newInstance();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String path;

    private Map<String, Object> controllerViewMap = new HashMap<>();

    private Map<String,Class> loadedViewClasses = new HashMap<>();

    private ControllerFactory factory = new DefaultControllerFactory();

    private SWTView root;

    private GroovyClassLoader loader = new GroovyClassLoader();

    private SWTWidget parent;

    private Module module;

    private SWTViewLoader parentLoader;

    public SWTViewLoader(String path) {
        this(path,null);
    }

    public SWTViewLoader(String path, SWTWidget parent) {
       this(
               path,
               parent,
               StackWalker
                       .getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                       .getCallerClass()
                       .getModule()
       );
    }

    public SWTViewLoader(String path, SWTWidget parent, Module module) {
        this.module = module;
        this.parent = parent;
        this.path = path;
    }

    public SWTWidget loadView(){
        return this.load(this.path);
    }

    private String loadClass(String path) {
        try {

            Module self = SWTWidgets.class.getModule();

            if (!self.canRead(module)) {
                logger.error("can not read resource from " + module.getName());
                throw new Exception("can not read resource");
            }

            InputStream in = module.getResourceAsStream(path + ".groovy");
            Class viewClass = loader.parseClass(new BufferedReader(new InputStreamReader(in,StandardCharsets.UTF_8)),path);

            if (!SWTWidget.class.isAssignableFrom(viewClass)) {
                logger.error("the class : " + viewClass.getName() + " is not a groovy view");
                throw new Exception(path + " is not a groovy view");
            }

            loadedViewClasses.put(viewClass.getSimpleName(),viewClass);
            logger.info("the class : " + viewClass.getName() + " loaded");

            return viewClass.getName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SWTWidget load(String path){
        String className = loadClass(path);

        logger.info("loading view : " + path);

        try {

            if (parent == null) {
                Stage stage = new Stage();
                stage.layout(new SWTRowLayout(SWT.HORIZONTAL));
                this.parent = stage;
            }

            if (parent.getLoader() == null) {
                parent.setLoader(this);
            } else {
                parentLoader = parent.getLoader();
            }

            Class<SWTView> widgetClass = loadedViewClasses.get(className);
            SWTView view = widgetClass
                    .getConstructor()
                    .newInstance();

            ViewRequire require = widgetClass.getAnnotation(ViewRequire.class);

            if (require != null) {
                String[] paths = require.value();
                for (String item: paths) {
                    this.loadClass(item);
                }
            }


            Object controller = this.factory.createController(widgetClass);

            if (controller != null) {
                controllerViewMap.put(parent.getControlId(),controller);
            }

            SWTWidget viewPage = view.getView(this);
            SWTWidget widget = viewPage;
            while (widget != null) {

                Object subController = factory.createController(widget.getClass());
                if (subController != null) {
                    controllerViewMap.put(widget.getControlId(),subController);
                }

                widget.create((Composite) parent.getWidget(),(SWTContainer) parent,this);
                widget = widget.getNext();
            }

            // 调用ready，因为parent内部创建了新的组件。
            parent.ready();

            logger.info(" view : " + path + " was loaded");

            return parent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getController(SWTWidget widget) {
        if (controllerViewMap.containsKey(widget.getControlId())) {
            return (T) controllerViewMap.get(widget.getControlId());
        } else {
            SWTWidget item = widget;
            while (item != null) {
                if (controllerViewMap.containsKey(item.getControlId())) {
                    return (T)controllerViewMap.get(item.getControlId());
                } else {
                    ViewController controller = item.getClass().getAnnotation(ViewController.class);
                    if (controller != null ) {
                        Object subController = factory.createController(widget.getClass());
                        controllerViewMap.put(widget.getControlId(),subController);
                        return (T)subController;
                    }
                }
                item = (SWTWidget) item.getParent();
            }



        }
        return null;
    }

    public SWTWidget create(String name) {
        Class viewClass = this.loadedViewClasses.get(name);
        if (viewClass == null && parentLoader !=null) {
            return parentLoader.create(name);
        }
        try {

            SWTWidget widget =  (SWTWidget) viewClass
                    .getConstructor()
                    .newInstance();

            widget.setLoader(this);

            return widget;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
