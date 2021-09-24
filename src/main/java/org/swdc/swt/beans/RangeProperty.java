package org.swdc.swt.beans;

import org.eclipse.swt.widgets.Widget;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class RangeProperty implements Property<Integer> {

    private ObservableValue<Integer> max = new ObservableValue<>();
    private ObservableValue<Integer> min = new ObservableValue<>();
    private ObservableValue<Integer> increase = new ObservableValue<>();

    private Method maxSetter = null;
    private Method minSetter = null;
    private Method increaseSetter = null;

    private Widget widget;

    @Override
    public void set(Integer integer) {
        throw new RuntimeException("方法无效");
    }

    @Override
    public Integer get() {
        throw new RuntimeException("方法无效");
    }

    public void setMax(Integer max) {
        this.max.set(max);
    }

    public Integer getMax(){
        return max.isEmpty() ? 0 : max.get();
    }

    public void setMin(Integer min) {
        this.min.set(min);
    }

    public Integer getMin() {
        return this.min.isEmpty() ? 0 : this.min.get();
    }

    public void setIncrease(Integer inc) {
        this.increase.set(inc);
    }

    public Integer getIncrease() {
        return this.increase.isEmpty() ? 0 : this.increase.get();
    }

    @Override
    public void manage(Widget widget) {
        this.widget = widget;
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            increaseSetter = widget.getClass().getMethod("setIncrement", new Class[]{int.class});
            increase.addListener(this::onIncreaseChange);
            this.onIncreaseChange(null,null);
        } catch (Exception  e) {
        }

        try {
            maxSetter = widget.getClass().getMethod("setMaximum", new Class[]{int.class});
            max.addListener(this::onMaxChange);
            this.onMinChange(null,null);
        } catch (Exception  e) {
        }

        try {
            minSetter = widget.getClass().getMethod("setMinimum", new Class[]{int.class});
            min.addListener(this::onMinChange);
            this.onMinChange(null,null);
        } catch (Exception  e) {
        }

    }

    private void onMaxChange(Integer oldVal, Integer newVal){
        if (widget == null) {
            return;
        }
        if (max.isEmpty()) {
            return;
        }
        try {
            maxSetter.invoke(widget,max.get());
        } catch (Throwable e) {
        }
    }

    private void onMinChange(Integer oldVal, Integer newVal){
        if (widget == null) {
            return;
        }
        if (min.isEmpty()) {
            return;
        }
        try {
            minSetter.invoke(widget,min.get());
        } catch (Throwable e) {
        }
    }

    private void onIncreaseChange(Integer oldVal, Integer newVal){
        if (widget == null) {
            return;
        }
        if (increase.isEmpty()) {
            return;
        }
        try {
            increaseSetter.invoke(widget,increase.get());
        } catch (Throwable e) {
        }
    }

    @Override
    public void unlink() {
        this.minSetter = null;
        this.maxSetter = null;
        this.increaseSetter = null;
        this.max.removeListener(this::onMaxChange);
        this.min.removeListener(this::onMinChange);
        this.increase.removeListener(this::onIncreaseChange);
    }

    public ObservableValue<Integer> valueMax() {
        return max;
    }

    public ObservableValue<Integer> valueMin() {
        return min;
    }

    public ObservableValue<Integer> valueIncrease() {
        return increase;
    }

}
