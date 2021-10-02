package org.swdc.swt.widgets.base;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Control;
import org.swdc.swt.beans.ControlProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;

public abstract class SWTControlWidget<T extends Control> extends SWTWidget<T> implements Controlable {

    private ControlProperty controlProperty = new ControlProperty();
    private Closure sizeClosure;
    private Closure moveClosure;

    @Override
    public void initStage(Stage stage) {
        super.initStage(stage);
        T widget = this.getWidget();
        if (widget != null) {
            widget.addControlListener(controlProperty.dispatcher());
            controlProperty.manage(this);
        }
    }

    @Override
    public void onResize(Closure closure) {
        this.sizeClosure = closure;
        this.controlProperty.closure(sizeClosure,moveClosure);
    }

    @Override
    public void onResize(String method) {
        this.controlProperty.setResizedMethod(method);
    }

    @Override
    public void onMove(Closure closure) {
        this.moveClosure = closure;
        this.controlProperty.closure(sizeClosure,moveClosure);
    }

    @Override
    public void onMove(String method) {
        this.controlProperty.setMoveMethod(method);
    }


}
