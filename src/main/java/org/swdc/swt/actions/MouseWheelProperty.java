package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.lang.reflect.Method;

public class MouseWheelProperty implements SWTProperty<String> {

    private SWTWidget widget;

    private ObservableValue<String> mouseWheelMethodName = new ObservableValue<>();
    private Method mouseWheelMethod;
    private Closure mouseWheelClosure;

    private MouseWheelListener dispatcher = new MouseWheelListener() {
        @Override
        public void mouseScrolled(MouseEvent mouseEvent) {
            MouseWheelProperty self = MouseWheelProperty.this;
            if (self.mouseWheelMethod != null) {
                self.call(mouseEvent,self.mouseWheelMethod);
            } else if (self.mouseWheelClosure != null){
                self.mouseWheelClosure.call(mouseEvent);
            }
        }
    };

    private void onWhellMethodChange(String oldVal,String newVal) {
        if (mouseWheelMethodName.isEmpty() || widget == null) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                mouseWheelMethodName,
                widget,
                prop -> prop.mouseWheelMethod,
                (Method method) -> this.mouseWheelMethod = method,
                MouseEvent.class
        );
    }

    private void call(MouseEvent event, Method finalMethod) {
        if (widget == null) {
            return;
        }
        Object controller = widget.getController();
        if (controller == null) {
            return;
        }

        if (finalMethod == null) {
            return;
        }

        try {
            if (finalMethod.getParameterCount() > 0) {
                finalMethod.invoke(controller,event);
            } else {
                finalMethod.invoke(controller);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getWheelMethodName() {
        return mouseWheelMethodName.isEmpty() ? "" : mouseWheelMethodName.get();
    }

    public void setMouseWheelMethod(String method) {
        this.mouseWheelMethodName.set(method);
    }

    @Override
    public void manage(SWTWidget widget) {
        unlink();
        this.widget = widget;
        if (!mouseWheelMethodName.isEmpty() && widget.getController() != null) {
            this.onWhellMethodChange(null,null);
        }
        mouseWheelMethodName.addListener(this::onWhellMethodChange);
    }

    public MouseWheelListener dispatcher(){
        return dispatcher;
    }

    public void closure(Closure closure) {
        this.mouseWheelClosure = closure;
    }

    @Override
    public void unlink() {
        mouseWheelMethodName.removeListener(this::onWhellMethodChange);
        this.mouseWheelMethod = null;
    }
}
