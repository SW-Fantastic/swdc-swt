package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;

public class SWTScale extends SWTWidget<Scale> {

    private int flags;

    private Scale scale;

    private int max;

    private int min;

    private int increment;

    public SWTScale(int flags) {
        this.flags = flags;
    }

    public SWTScale max(int max) {
        if (scale != null) {
            scale.setMaximum(max);
        }
        this.max = max;
        return this;
    }

    public SWTScale min(int min) {
        if (scale != null) {
            scale.setMinimum(min);
        }
        this.min = min;
        return this;
    }

    public SWTScale increment(int inc) {
        if (scale != null) {
            scale.setIncrement(inc);
        }
        increment = inc;
        return this;
    }

    @Override
    public Scale getWidget(Composite parent) {
        if (this.scale == null && parent != null) {
            scale = new Scale(parent,this.flags);
            if (this.getLayoutData() != null) {
                scale.setLayoutData(getLayoutData().get());
            }
            scale.setMinimum(min);
            scale.setMaximum(max);
            scale.setIncrement(increment);
        }
        return scale;
    }

    public static SWTScale scale(int flags) {
        return new SWTScale(flags);
    }

}
