package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;
import org.swdc.swt.beans.RangeProperty;
import org.swdc.swt.beans.SizeProperty;

public class SWTScale extends SWTWidget<Scale> {

    private int flags;

    private Scale scale;

    private RangeProperty rangeProperty = new RangeProperty();

    private SizeProperty sizeProperty = new SizeProperty();

    public SWTScale(int flags) {
        this.flags = flags;
    }

    public SWTScale max(int max) {
        rangeProperty.setMax(max);
        return this;
    }

    public SWTScale min(int min) {
        rangeProperty.setMin(min);
        return this;
    }

    public SWTScale increment(int inc) {
        rangeProperty.setIncrease(inc);
        return this;
    }

    public SWTScale size(int width, int height) {
        sizeProperty.set(width, height);
        return this;
    }

    @Override
    public Scale getWidget(Composite parent) {
        if (this.scale == null && parent != null) {
            scale = new Scale(parent,this.flags);
            if (this.getLayoutData() != null) {
                scale.setLayoutData(getLayoutData().get());
            }
            sizeProperty.manage(scale);
            rangeProperty.manage(scale);
        }
        return scale;
    }

    public static SWTScale scale(int flags) {
        return new SWTScale(flags);
    }

}
