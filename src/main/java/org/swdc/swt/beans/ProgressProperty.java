package org.swdc.swt.beans;

import org.eclipse.swt.widgets.Widget;

import java.lang.reflect.Method;

public class ProgressProperty implements Property<Integer> {

    private ObservableValue<Integer>  value = new ObservableValue<>();
    private ObservableValue<Integer> max = new ObservableValue<>(100);
    private ObservableValue<Integer> min = new ObservableValue<>(0);

    private Widget widget;

    private Method setter;
    private Method getter;

    private Method maxSetter;
    private Method maxGetter;

    private Method minSetter;
    private Method minGetter;

    @Override
    public void set(Integer integer) {
        value.set(integer);
    }

    @Override
    public Integer get() {
        try {
            if (getter == null) {
                return value.isEmpty() ? 0 : value.get();
            }
            Integer val = (Integer) getter.invoke(widget);
            if (val != null) {
                this.value.set(val);
            }
            return value.isEmpty() ? 0 : value.get();
        } catch (Exception e) {
            return value.isEmpty() ? 0: value.get();
        }
    }

    public void setMax(int max) {
        this.max.set(max);
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public int getMax() {
        try {
            if (maxGetter == null) {
                return max.isEmpty() ? 0 : max.get();
            }
            Integer val = (Integer) maxGetter.invoke(widget);
            if (val != null) {
                this.max.set(val);
            }
            return max.isEmpty() ? 0 : max.get();
        } catch (Exception e) {
            return max.isEmpty() ? 0: max.get();
        }
    }


    public int getMin() {
        try {
            if (minGetter == null) {
                return min.isEmpty() ? 0 : min.get();
            }
            Integer val = (Integer) minGetter.invoke(widget);
            if (val != null) {
                this.max.set(val);
            }
            return min.isEmpty() ? 0 : min.get();
        } catch (Exception e) {
            return min.isEmpty() ? 0: min.get();
        }
    }

    @Override
    public void manage(Widget widget) {
        unlink();
        this.widget = widget;
        try {
            setter = widget.getClass().getMethod("setSelection", new Class[]{int.class});
            this.onValueChange(null,null);
            this.value.addListener(this::onValueChange);
        } catch (Exception e) {
        }

        try {
            getter = widget.getClass().getMethod("getSelection");
        } catch (Exception e) {
        }

        try {
            maxSetter = widget.getClass().getMethod("setMaximum", new Class[]{int.class});
            max.addListener(this::onMaxChange);
            this.onMinChange(null,null);
        } catch (Exception  e) {
        }

        try {
            maxGetter = widget.getClass().getMethod("getMaximum");
        } catch (Exception e){
        }

        try {
            minSetter = widget.getClass().getMethod("setMinimum", new Class[]{int.class});
            min.addListener(this::onMinChange);
            this.onMinChange(null,null);
        } catch (Exception  e) {
        }

        try {
            minGetter = widget.getClass().getMethod("getMinimum");
        } catch (Exception  e) {
        }
    }

    private void onMinChange(Integer oldVal, Integer newVal) {

        try {
            if (this.widget == null || this.setter == null) {
                return;
            }
            if (this.min.isEmpty()) {
                return;
            }
            int val = this.min.get();
            minSetter.invoke(this.widget,val);
        } catch (Exception e) {
        }
    }

    private void onMaxChange(Integer oldVal, Integer newVal) {

        try {
            if (this.widget == null || this.setter == null) {
                return;
            }
            if (this.max.isEmpty()) {
                return;
            }
            int val = this.max.get();
            maxSetter.invoke(this.widget,val);
        } catch (Exception e) {
        }
    }

    private void onValueChange(Integer oldVal, Integer newVal) {
        try {
            if (this.widget == null || this.setter == null) {
                return;
            }
            if (this.value.isEmpty()) {
                return;
            }
            int val = this.value.get();
            setter.invoke(this.widget,val);
        } catch (Exception e) {
        }
    }

    @Override
    public void unlink() {
        this.value.removeListener(this::onValueChange);
        this.setter = null;
        this.widget = null;
        this.getter = null;

        this.max.removeListener(this::onMaxChange);
        this.maxSetter = null;
        this.maxGetter = null;

        this.min.removeListener(this::onMinChange);
        this.minSetter = null;
        this.minGetter = null;

    }
}
