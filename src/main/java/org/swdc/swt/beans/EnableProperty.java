package org.swdc.swt.beans;

import org.eclipse.swt.widgets.Widget;

import java.lang.reflect.Method;

public class EnableProperty implements Property<Boolean> {

    private Widget widget;
    private ObservableValue<Boolean> enable = new ObservableValue<>(true);

    private Method getter;
    private Method setter;

    @Override
    public void set(Boolean aBoolean) {
        enable.set(aBoolean);
    }

    @Override
    public Boolean get() {
        if (this.getter == null) {
            return !this.enable.isEmpty() && enable.get();
        }
        try {
            boolean enabled = (boolean)getter.invoke(widget);
            this.enable.set(enabled);
            return !this.enable.isEmpty() && enable.get();
        } catch (Exception e){
            return !this.enable.isEmpty() && enable.get();
        }
    }

    private void onEnableChanged(Boolean oldVal, Boolean newVal) {
        if (widget == null || setter == null || enable.isEmpty()) {
            return;
        }
        Boolean enable = this.enable.get();
        try {
            setter.invoke(widget,enable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void manage(Widget widget) {
        try {
            setter = widget.getClass().getMethod("setEnabled", new Class[]{boolean.class});
        } catch (Exception e) {
        }

        try {
            getter = widget.getClass().getMethod("getEnabled");
        } catch (Exception e) {
        }

        if (!enable.isEmpty()) {
            this.onEnableChanged(null,null);
        }
        this.enable.addListener(this::onEnableChanged);

    }

    @Override
    public void unlink() {

    }
}
