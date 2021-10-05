package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;
import org.swdc.swt.beans.RangeProperty;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;

public class SWTScale extends SWTControlWidget<Scale> {

    private int flags;

    private Scale scale;

    private RangeProperty rangeProperty = new RangeProperty();

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

    @Override
    public void ready() {
        super.ready();
        if (scale != null) {
            SWTWidgets.setupLayoutData(this,scale);
        }
    }

    @Override
    protected Scale getWidget(Composite parent) {
        if (this.scale == null && parent != null) {
            scale = new Scale(parent,this.flags);
            rangeProperty.manage(scale);
        }
        return scale;
    }

    public static SWTScale scale(int flags) {
        return new SWTScale(flags);
    }

}
