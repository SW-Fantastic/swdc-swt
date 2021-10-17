package org.swdc.swt.actions;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;

import java.lang.reflect.Method;

public class DisposeProperty implements SWTProperty<String,DisposeEvent> {

    private ObservableValue<String> disposeName = new ObservableValue<>();
    private Method disposeMethod;
    private SWTWidget widget;

    private DisposeListener dispatcher = new DisposeListener() {
        @Override
        public void widgetDisposed(DisposeEvent disposeEvent) {
            if (disposeMethod != null) {
                call(widget,disposeEvent,disposeMethod);
            }
        }
    };

    public String getDisposeMethod() {
        return disposeName.isEmpty() ? "" : disposeName.get();
    }

    public void setDisposeMethod(String method) {
        disposeName.set(method);
    }


    private void onDisposeChange(String oldMethod, String newMethod) {
        if (disposeName.isEmpty() || widget == null || widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                disposeName,
                widget,
                prop -> prop.disposeMethod,
                method -> disposeMethod = method,
                MouseEvent.class
        );
    }

    public DisposeListener dispatcher() {
        return dispatcher;
    }

    @Override
    public void manage(SWTWidget widget) {
        this.unlink();
        this.widget = widget;
        if (!disposeName.isEmpty() && widget.getController() != null) {
            this.onDisposeChange(null,null);
        }
        disposeName.addListener(this::onDisposeChange);
    }

    @Override
    public void unlink() {
        disposeName.removeListener(this::onDisposeChange);
        disposeMethod = null;
    }
}
