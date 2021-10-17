package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.lang.reflect.Method;

public class MouseMoveProperty implements SWTProperty<String,MouseEvent> {

    private ObservableValue<String> mouseMovedMethodName = new ObservableValue<>();
    private Method mouseMovedMethod;
    private Closure mouseMovedClosure;

    private SWTWidget widget;

    private MouseMoveListener dispatcher = new MouseMoveListener() {

        @Override
        public void mouseMove(MouseEvent mouseEvent) {
            MouseMoveProperty self = MouseMoveProperty.this;
            if (self.mouseMovedMethod != null) {
                self.call(widget,mouseEvent,mouseMovedMethod);
            } else if (self.mouseMovedClosure != null) {
                self.mouseMovedClosure.call(mouseEvent);
            }
        }
    };


    private void onMouseMoveMethodChange(String nameOld, String nameNew) {
        if (mouseMovedMethodName.isEmpty() || widget == null ) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }

        SWTWidgets.setupMethod(
                this,
                mouseMovedMethodName,
                widget,
                prop -> prop.mouseMovedMethod,
                method -> mouseMovedMethod = method,
                MouseEvent.class
        );

    }

    public String getMouseMoveMethod() {
        return mouseMovedMethodName.isEmpty() ? "" : mouseMovedMethodName.get();
    }

    public void setMouseMovedMethod(String method) {
        this.mouseMovedMethodName.set(method);
    }

    public void closure(Closure move) {
        this.mouseMovedClosure = move;
    }

    public MouseMoveListener dispatcher(){
        return dispatcher;
    }

    @Override
    public void manage(SWTWidget widget) {
        unlink();
        this.widget = widget;
        if (!mouseMovedMethodName.isEmpty() && widget.getController() != null) {
            this.onMouseMoveMethodChange(null,null);
        }
        this.mouseMovedMethodName.addListener(this::onMouseMoveMethodChange);
    }

    @Override
    public void unlink() {
        this.mouseMovedMethodName.removeListener(this::onMouseMoveMethodChange);
        this.mouseMovedMethod = null;
    }
}
