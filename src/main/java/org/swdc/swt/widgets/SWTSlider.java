package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Slider;
import org.swdc.swt.beans.RangeProperty;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;

public class SWTSlider extends SWTControlWidget<Slider> {

    private int flags;

    private Slider slider;

    private RangeProperty rangeProperty = new RangeProperty();

    public SWTSlider(int flags) {
        this.flags = flags;
    }

    public SWTSlider min(int min) {
        rangeProperty.setMin(min);
        return this;
    }

    public SWTSlider max(int max) {
        this.rangeProperty.setMax(max);
        return this;
    }

    public SWTSlider increment(int inc) {
        this.rangeProperty.setIncrease(inc);
        return this;
    }

    @Override
    public void ready() {
        super.ready();
        if (this.slider != null) {
            SWTWidgets.setupLayoutData(this,this.slider);
        }
    }

    @Override
    protected Slider getWidget(Composite parent) {
        if (this.slider == null && parent != null) {
            this.slider = new Slider(parent,this.flags);

            this.rangeProperty.manage(this.slider);
        }
        return this.slider;
    }

    public static SWTSlider slider(int flags) {
        return new SWTSlider(flags);
    }

}
