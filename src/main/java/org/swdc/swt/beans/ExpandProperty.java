package org.swdc.swt.beans;

import org.eclipse.swt.widgets.Widget;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class ExpandProperty implements Property<Boolean>{

    private ObservableValue<Boolean> expand = new ObservableValue<>();

    private Method expandSetter = null;
    private Method expandGetter = null;

    private Widget widget;

    @Override
    public void set(Boolean val) {
        this.expand.set(val);
    }

    @Override
    public Boolean get() {
        if (this.expandGetter == null) {
            return !this.expand.isEmpty() && this.expand.get();
        }
        try {
            Boolean expand = (Boolean) expandGetter.invoke(widget);
            if (expand == null) {
                return !this.expand.isEmpty() && this.expand.get();
            }
            this.expand.set(expand);
            return !this.expand.isEmpty() && this.expand.get();
        } catch (Exception e) {
            return !this.expand.isEmpty() && this.expand.get();
        }
    }

    @Override
    public void manage(Widget widget) {
        this.unlink();
        this.widget = widget;
        try {
            expandSetter = widget.getClass().getMethod("setExpanded",new Class[]{ boolean.class });
            this.onExpandChange(null,null);
            expand.addListener(this::onExpandChange);
        } catch (Exception e) {
        }

        try {
            expandGetter = widget.getClass().getMethod("isExpanded");
        } catch (Exception e) {
        }

        if (expandGetter == null) {
            try {
                expandGetter = widget.getClass().getMethod("getExpanded");
            } catch (Exception e) {
            }
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
