package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.lang.reflect.Method;

public class SelectionProperty implements SWTProperty<String,SelectionEvent> {
    
    private ObservableValue<String> methodName = new ObservableValue<>();

    private SWTWidget widget;
    private Method actionMethod;

    private Closure closure;

    private SelectionAdapter dispatcher = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent selectionEvent) {
            SelectionProperty self = SelectionProperty.this;
            if (self.actionMethod != null) {
                self.call(widget,selectionEvent,actionMethod);
            } else if (closure != null){
                closure.call(selectionEvent);
            }
        }
    };

    public void setSelectionMethod(String s) {
        methodName.set(s);
    }

    public String getSelectionMethod() {
        return methodName.get();
    }

    private void onNameChange(String oldName, String newName) {
        if (widget == null) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }

        SWTWidgets.setupMethod(
                this,
                methodName,
                widget,
                prop -> prop.actionMethod,
                (Method method) -> this.actionMethod = method,
                SelectionEvent.class
        );

    }


    @Override
    public void manage(SWTWidget widget) {
        unlink();
        this.widget = widget;
        if (!methodName.isEmpty() && widget.getController() != null) {
            this.onNameChange(null,null);
        }
        methodName.addListener(this::onNameChange);
    }

    public SelectionAdapter dispatcher() {
        return dispatcher;
    }

    public SelectionAdapter closure(Closure closure) {
        this.closure = closure;
        return dispatcher;
    }

    @Override
    public void unlink() {
        this.methodName.removeListener(this::onNameChange);
        this.widget = null;
        this.actionMethod = null;
    }
}
