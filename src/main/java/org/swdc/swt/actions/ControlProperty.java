package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.lang.reflect.Method;

public class ControlProperty implements SWTProperty<String,ControlEvent> {

    private ObservableValue<String> moveMethodName = new ObservableValue<>();
    private ObservableValue<String> resizedMethodName = new ObservableValue<>();

    private SWTWidget widget;
    private Method moveMethod;
    private Method resizeMethod;

    private Closure moveClosure;
    private Closure resizeClosure;

    private ControlAdapter dispatcher = new ControlAdapter() {
        @Override
        public void controlMoved(ControlEvent controlEvent) {
           ControlProperty self = ControlProperty.this;
           if (moveMethod != null) {
               self.call(widget,controlEvent,moveMethod);
           } else if (moveClosure != null) {
               self.moveClosure.call(controlEvent);
           }
        }

        @Override
        public void controlResized(ControlEvent controlEvent) {
            ControlProperty self = ControlProperty.this;
            if (resizeMethod != null) {
                self.call(widget,controlEvent,resizeMethod);
            } else if (resizeClosure != null) {
                resizeClosure.call(controlEvent);
            }
        }
    };

    public void setMoveMethod(String name) {
        this.moveMethodName.set(name);
    }

    public String getMoveMethod() {
        return moveMethodName.isEmpty() ? "" : moveMethodName.get();
    }

    public void setResizedMethod(String name) {
        this.resizedMethodName.set(name);
    }

    public String getResizeMethod() {
        return resizedMethodName.isEmpty() ? "" : resizedMethodName.get();
    }

    private void onResizeMethodChange(String oldName, String newName) {
        if (resizedMethodName.isEmpty() || widget == null) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }

        SWTWidgets.setupMethod(
                this,
                resizedMethodName,
                widget,
                prop -> prop.resizeMethod,
                method -> resizeMethod = method,
                ControlEvent.class
        );

    }

    private void onMoveMethodChange(String oldName, String newName) {
        if (moveMethodName.isEmpty() || widget == null) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }

        SWTWidgets.setupMethod(
                this,
                moveMethodName,
                widget,
                prop -> prop.moveMethod,
                method -> moveMethod = method,
                ControlEvent.class
        );

    }

    @Override
    public void manage(SWTWidget widget) {
        unlink();
        this.widget = widget;
        if (!resizedMethodName.isEmpty() && widget.getController() != null) {
            this.onResizeMethodChange(null,null);
        }

        if (!moveMethodName.isEmpty() && widget.getController() != null) {
            this.onMoveMethodChange(null,null);
        }

        resizedMethodName.addListener(this::onResizeMethodChange);
        moveMethodName.addListener(this::onMoveMethodChange);
    }

    @Override
    public void unlink() {
        this.moveMethodName.removeListener(this::onMoveMethodChange);
        this.resizedMethodName.removeListener(this::onResizeMethodChange);
        this.moveMethod = null;
        this.resizeMethod = null;

    }

    public ControlAdapter dispatcher(){
        return dispatcher;
    }

    public void closure(Closure resize, Closure move) {
        this.moveClosure = move;
        this.resizeClosure= resize;
    }

}
