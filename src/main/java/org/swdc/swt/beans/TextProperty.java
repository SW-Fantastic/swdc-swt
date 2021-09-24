package org.swdc.swt.beans;

import org.eclipse.swt.widgets.Widget;

import java.lang.reflect.Method;

public class TextProperty implements Property<String> {

    private Widget widget;
    private Method setter;

    private ObservableValue<String> value = new ObservableValue<>("");

    @Override
    public void set(String s) {
        this.value.set(s);
    }

    @Override
    public String get() {
        return value.isEmpty() ? "" : value.get();
    }

    @Override
    public void manage(Widget widget) {
        unlink();
        this.widget = widget;
        try {
            setter = widget.getClass().getMethod("setText",String.class);
            value.addListener(this::onTextChange);
            this.onTextChange(null,null);
        } catch (Exception e) {
        }
    }

    private void onTextChange(String oldVal, String newVal) {
        if (widget == null || value.isEmpty()) {
            return;
        }
        if (setter == null) {
            return;
        }
        try {
            setter.invoke(widget,value.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setDirectly(String value) {
        this.value.setWithoutListener(value);
    }

    @Override
    public void unlink() {
        if (this.widget != null) {
            this.value.removeListener(this::onTextChange);
            this.setter = null;
        }
    }

    public ObservableValue<String> valueText() {
        return value;
    }

}
