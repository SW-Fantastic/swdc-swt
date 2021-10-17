package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.lang.reflect.Method;

public class MouseProperty implements SWTProperty<String,MouseEvent> {

    private SWTWidget widget;

    private ObservableValue<String> mouseUpMethodName = new ObservableValue<>();
    private Method mouseUpMethod;
    private Closure closureMouseUp;

    private ObservableValue<String> mouseDownMethodName = new ObservableValue<>();
    private Method mouseDownMethod;
    private Closure closureMouseDown;

    private ObservableValue<String> mouseDoubleClickMethodName = new ObservableValue<>();
    private Method mouseDoubleClickMethod;
    private Closure closureMouseDoubleClick;

    private MouseAdapter dispatcher = new MouseAdapter() {
        @Override
        public void mouseDoubleClick(MouseEvent mouseEvent) {
            MouseProperty self = MouseProperty.this;
            if (self.mouseDoubleClickMethod != null) {
                self.call(widget,mouseEvent,mouseDoubleClickMethod);
            } else if (closureMouseDoubleClick != null) {
                closureMouseDoubleClick.call(mouseEvent);
            }
        }

        @Override
        public void mouseDown(MouseEvent mouseEvent) {
            MouseProperty self = MouseProperty.this;
            if (self.mouseDownMethod != null) {
                self.call(widget,mouseEvent, mouseDownMethod);
            } else if (closureMouseDown != null) {
                closureMouseDown.call(mouseEvent);
            }
        }

        @Override
        public void mouseUp(MouseEvent mouseEvent) {
            MouseProperty self = MouseProperty.this;
            if (self.mouseUpMethod != null) {
                self.call(widget,mouseEvent,mouseUpMethod);
            } else if (closureMouseUp != null) {
                closureMouseUp.call(mouseEvent);
            }
        }
    };



    private void onMouseDoubleClickMethodChange(String oldName, String newName) {
        if (mouseDoubleClickMethodName.isEmpty() || widget == null) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                mouseDoubleClickMethodName,
                this.widget,
                swtProperty -> swtProperty.mouseDoubleClickMethod,
                method -> mouseDoubleClickMethod = method,
                MouseEvent.class
        );
    }

    private void onMouseUpMethodChange(String oldName , String newName){
        if(mouseUpMethodName.isEmpty() || widget == null) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                mouseUpMethodName,
                this.widget,
                prop->prop.mouseUpMethod,
                method->mouseUpMethod = method,
                MouseEvent.class
        );
    }

    private void onMouseDownMethodChange(String oldName, String newName) {
        if(mouseDownMethodName.isEmpty() || widget == null) {
            return;
        }
        if (widget.getLoader().getController(widget) == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                mouseDownMethodName,
                this.widget,
                prop->prop.mouseDownMethod,
                method->mouseDownMethod = method,
                MouseEvent.class
        );
    }

    public String getMouseUpMethod() {
        return mouseUpMethodName.isEmpty() ? "" : mouseUpMethodName.get();
    }

    public void setMouseUpMethod(String method) {
        this.mouseUpMethodName.set(method);
    }

    public String getMouseDownMethod() {
        return mouseDownMethodName.isEmpty() ? "" : mouseDownMethodName.get();
    }

    public void setMouseDownMethod(String method){
        this.mouseDownMethodName.set(method);
    }

    public String getMouseDoubleClickMethod() {
        return mouseDoubleClickMethodName.isEmpty() ? "" : mouseDownMethodName.get();
    }

    public void setMouseDoubleClickMethod(String method) {
        this.mouseDoubleClickMethodName.set(method);
    }

    @Override
    public void manage(SWTWidget widget) {

        unlink();

        this.widget = widget;
        if (!mouseDoubleClickMethodName.isEmpty() && widget.getController() != null) {
            this.onMouseDoubleClickMethodChange(null,null);
        }

        if (!mouseUpMethodName.isEmpty() && widget.getController() != null) {
            this.onMouseUpMethodChange(null,null);
        }

        if (!mouseDownMethodName.isEmpty() && widget.getController() != null) {
            this.onMouseDownMethodChange(null,null);
        }

        mouseDoubleClickMethodName.addListener(this::onMouseDoubleClickMethodChange);
        mouseDownMethodName.addListener(this::onMouseDownMethodChange);
        mouseUpMethodName.addListener(this::onMouseUpMethodChange);
    }

    public void closure(Closure mouseUp, Closure mouseDown, Closure mouseDbClick) {
        this.closureMouseUp = mouseUp;
        this.closureMouseDown = mouseDown;
        this.closureMouseDoubleClick = mouseDbClick;
    }

    public MouseAdapter dispatcher() {
        return dispatcher;
    }

    @Override
    public void unlink() {

        mouseDoubleClickMethodName.removeListener(this::onMouseDoubleClickMethodChange);
        mouseDownMethodName.removeListener(this::onMouseDownMethodChange);
        mouseUpMethodName.removeListener(this::onMouseUpMethodChange);

        this.mouseDownMethod = null;
        this.mouseUpMethod = null;
        this.mouseDoubleClickMethod = null;

    }
}
