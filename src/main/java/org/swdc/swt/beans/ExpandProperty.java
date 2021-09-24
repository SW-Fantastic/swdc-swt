package org.swdc.swt.beans;

import org.eclipse.swt.widgets.Widget;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class ExpandProperty implements Property<Boolean>{

    private ObservableValue<Boolean> expand = new ObservableValue<>();

    private Method expandSetter = null;

    private Widget widget;

    @Override
    public void set(Boolean val) {
        this.expand.set(val);
    }

    @Override
    public Boolean get() {
        return !this.expand.isEmpty() && this.expand.get();
    }

    @Override
    public void manage(Widget widget) {
        this.unlink();
        this.widget = widget;
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            expandSetter = widget.getClass().getMethod("setExpanded",new Class[]{ boolean.class });
            this.onExpandChange(null,null);
            expand.addListener(this::onExpandChange);
        } catch (Exception e) {
        }
    }

    private void onExpandChange(Boolean oldVal, Boolean newval) {
        if (widget == null) {
            return;
        }
        if (this.expand.isEmpty()) {
            return;
        }
        try {
            expandSetter.invoke(widget,this.expand.get());
        } catch (Throwable e) {
        }
    }

    public void setDirectly(boolean directly) {
        this.expand.setWithoutListener(directly);
    }

    @Override
    public void unlink() {
        this.expandSetter = null;
        this.expand.removeListener(this::onExpandChange);
    }

    public ObservableValue<Boolean> valueExpand() {
        return expand;
    }

}
