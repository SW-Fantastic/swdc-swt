package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;

import java.lang.reflect.Method;

public class ExpansionProperty implements SWTProperty<String> {

    private ObservableValue<String> value = new ObservableValue<>();
    private Method method;

    private Closure closure;

    private SWTWidget widget;

    private ExpansionAdapter dispatcher = new ExpansionAdapter() {
        @Override
        public void expansionStateChanged(ExpansionEvent e) {
            if (method != null) {
                call(e,method);
            } else if (closure != null) {
                closure.call(e);
            }
        }
    };

    private void call(ExpansionEvent event, Method finalMethod) {
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

    private void onExpansionChange(String oldVal, String newVal) {
        if (this.widget == null || widget.getController() == null || value.isEmpty()) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                value,
                widget,
                p -> p.method,
                m -> method = m,
                ExpansionEvent.class
        );
    }

    @Override
    public void manage(SWTWidget widget) {
        this.unlink();
        this.widget = widget;
        if (!value.isEmpty() && widget.getController() != null) {
            this.onExpansionChange(null,null);
            value.addListener(this::onExpansionChange);
        }
    }

    public void setExpand(String method) {
        value.set(method);
    }

    public String getExpandMethod() {
        return value.isEmpty() ? "" : value.get();
    }

    public void closure(Closure closure) {
        this.closure = closure;
    }

    public ExpansionAdapter dispatcher() {
        return dispatcher;
    }

    @Override
    public void unlink() {
        this.value.removeListener(this::onExpansionChange);
        method = null;
    }
}
