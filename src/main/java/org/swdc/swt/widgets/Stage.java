package org.swdc.swt.widgets;


import groovy.lang.Closure;
import org.eclipse.swt.widgets.Shell;
import org.swdc.swt.layouts.SWTLayout;

import java.util.*;

public class Stage implements SWTContainer  {

    private Shell shell = new Shell();

    private Object controller;

    private SWTLayout layout;

    private SWTWidget widget;

    private boolean initialized;

    private Map<String, SWTWidget> widgetMap = new HashMap<>();

    public Stage controller(Object controller) {
        this.controller = controller;
        return this;
    }

    public Object getController() {
        return controller;
    }


    public Stage text(String text) {
        shell.setText(text);
        return this;
    }

    public Stage size(int width, int height) {
        shell.setSize(width, height);
        return this;
    }

    public Stage layout(SWTLayout layout) {
        this.layout = layout;
        shell.setLayout(layout.getLayout());
        return this;
    }

    public SWTLayout getLayout() {
        return layout;
    }

    @Override
    public void children(SWTWidget widget) {
        this.widget = widget;
    }

    /**
     * 在窗口中创建组件。
     * @param closure
     * @return
     */
    @Override
    public SWTWidget widget(Closure<SWTWidget> closure) {
        Object result = closure.call();
        if (!(result instanceof SWTWidget)) {
            throw new RuntimeException("ui定义中，widget必须为SWTWidget的类型或者其子类");
        }
        SWTWidget target = closure.call();
        target.create(this.shell,this);
        target.initStage(this);
        target.ready(this);
        if (target.getId() != null) {
            widgetMap.put(target.getId(),target);
        }

        return target;
    }

    public Stage show() {
        if (!initialized) {
            if (this.controller != null && this.controller instanceof Initialize) {
                Initialize initialize = (Initialize) controller;
                initialize.initialize();
            }
            initialized = true;
        }
        shell.open();
        return this;
    }

    public Boolean isDisposed() {
        return shell.isDisposed();
    }

    public Shell getShell() {
        return shell;
    }

    @Override
    public List<SWTWidget> children() {
        return new ArrayList<>(widgetMap.values());
    }
}
