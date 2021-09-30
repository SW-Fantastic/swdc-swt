package org.swdc.swt.beans;

import org.eclipse.swt.widgets.Widget;

import java.lang.reflect.Method;

public class SelectProperty implements Property<Integer> {

    private ObservableValue<Integer> val = new ObservableValue<>(0);
    private Method setter;
    private Method getter;

    private Widget widget;

    @Override
    public void set(Integer integer) {
        val.set(integer);
    }

    @Override
    public Integer get() {
        if (widget == null) {
            return 0;
        }
        try {
            Integer data = (Integer) this.getter.invoke(widget);
            if(data != null) {
                val.set(data);
            }
            return val.isEmpty() ? 0 : val.get();
        } catch (Exception e) {
            return val.isEmpty() ? 0 : val.get();
        }
    }

    @Override
    public void manage(Widget widget) {
        this.unlink();
        this.widget = widget;
        try {
            this.setter = widget.getClass().getMethod("setSelection",new Class[]{int.class});
            this.onSelectionChange(null,null);
        } catch (Exception e) {
        }

        try {
            this.getter = widget.getClass().getMethod("getSelection");
        } catch (Exception e) {
        }

    }

    private void onSelectionChange(Integer oldVal, Integer newVal) {
        if (this.setter == null || this.widget == null) {
            return;
        }
        if (this.val.isEmpty()) {
            return;
        }
        int val = this.val.get();
        try {
            this.setter.invoke(widget,val);
        } catch (Exception e) {
        }
    }

    @Override
    public void unlink() {
        this.val.removeListener(this::onSelectionChange);
        this.setter = null;
        this.widget = null;
        this.getter = null;
    }
}
