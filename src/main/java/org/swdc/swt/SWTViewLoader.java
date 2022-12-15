package org.swdc.swt;

import groovy.lang.GroovyClassLoader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scrollable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swdc.swt.layouts.SWTRowLayout;
import org.swdc.swt.widgets.*;
import org.swdc.swt.widgets.base.SWTView;

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

    private Map<String,String> loadedPathNameMap = new HashMap<>();

    private ControllerFactory factory = new DefaultControllerFactory();

    private SWTView<Scrollable> root;

    private GroovyClassLoader loader = new GroovyClassLoader();

    private SWTContainer parent;

    private Module module;

    private SWTViewLoader parentLoader;

    public SWTViewLoader(String path) {
        this(path,null);
    }

    public SWTViewLoader(String path, SWTContainer parent) {
       this(
               path,
               parent,
               StackWalker
                       .getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                       .getCallerClass()
                       .getModule()
       );
    }

    public SWTViewLoader(String path, SWTContainer parent, Module module) {
        this.module = module;
        this.parent = parent;
        this.path = path;
    }

    public void setFactory(ControllerFactory factory) {
        this.factory = factory;
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

            if (loadedPathNameMap.containsKey(path)) {
                return loadedPathNameMap.get(path);
            }

            InputStream in = module.getResourceAsStream(path + ".groovy");
            Class viewClass = loader.parseClass(new BufferedReader(new InputStreamReader(in,StandardCharsets.UTF_8)),path);

            if (!SWTWidget.class.isAssignableFrom(viewClass)) {
                logger.error("the class : " + viewClass.getName() + " is not a groovy view");
                throw new Exception(path + " is not a groovy view");
            }

            loadedViewClasses.put(viewClass.getSimpleName(),viewClass);
            loadedPathNameMap.put(path,viewClass.getSimpleName());
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
            SWTWidget viewPage = view.getView(this);

            if (parent == null) {
                Stage stage = new Stage();
                stage.layout(view.getLayout() == null ? new SWTRowLayout(SWT.HORIZONTAL) : view.getLayout());
                stage.children(viewPage);
                this.parent = stage;
            } else {
                parent.children(viewPage);
            }

            if (parent.getLoader() == null) {
                parent.setLoader(this);
            } else {
                parentLoader = parent.getLoader();
            }

            SWTWidget parentWidget = (SWTWidget)parent;

            Object controller = this.factory.createController(widgetClass);
            if (controller != null) {
                controllerViewMap.put(parentWidget.getControlId(),controller);
            }

            parentWidget.getWidget(null);
            logger.info(" view : " + path + " was loaded");

            return (SWTWidget) parent;
        } catch (Exception e) {
            logger.error("failed load view :  " + path + "caused by",e);
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
                        Object subController = factory.createController(item.getClass());
                        controllerViewMap.put(item.getControlId(),subController);
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

            SWTView widget =  (SWTView) viewClass
                    .getConstructor()
                    .newInstance();

            widget.setLoader(this);
            return widget;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
