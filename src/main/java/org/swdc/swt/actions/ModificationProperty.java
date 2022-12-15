package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;

import java.lang.reflect.Method;

public class ModificationProperty implements SWTProperty<String, ModifyEvent> {

    private SWTWidget widget;
    private ObservableValue<String> modifyMethodName = new ObservableValue<>();
    private Method modifyMethod;
    private Closure modifyClosure;

    private ModifyListener dispatcher = (e) -> {
        if (modifyMethod != null) {
            call(widget,e,modifyMethod);
        } else if (modifyClosure != null) {
            modifyClosure.call(e);
        }
    };

    private void onModifyMethodChanged(String valOld, String valNew) {
        if (modifyMethodName.isEmpty() || widget == null) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                modifyMethodName,
                widget,
                prop -> prop.modifyMethod,
                method -> modifyMethod = method,
                KeyEvent.class
        );
    }

    public ModifyListener dispatcher() {
        return dispatcher;
    }

    public void closure(Closure closure) {
        this.modifyClosure = closure;
    }

    public void setModifyMethod(String name) {
        this.modifyMethodName.set(name);
    }

    @Override
    public void manage(SWTWidget widget) {
        unlink();
        this.widget = widget;
        if (!modifyMethodName.isEmpty() && widget.getController() != null) {
            this.onModifyMethodChanged(null,null);
        }
        this.modifyMethodName.addListener(this::onModifyMethodChanged);
    }

    @Override
    public void unlink() {
        modifyMethodName.removeListener(this::onModifyMethodChanged);
        this.modifyMethod = null;
    }
}
