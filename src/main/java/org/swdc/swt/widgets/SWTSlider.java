package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Slider;
import org.swdc.swt.beans.RangeProperty;
import org.swdc.swt.beans.SizeProperty;

public class SWTSlider extends SWTWidget<Slider> {

    private int flags;

    private Slider slider;

    private RangeProperty rangeProperty = new RangeProperty();

    private SizeProperty sizeProperty = new SizeProperty();

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

    public SWTSlider size(int width, int height) {
        sizeProperty.set(width,height);
        return this;
    }

    @Override
    public void ready(Stage stage) {
        if (this.slider != null) {
            SWTWidgets.setupLayoutData(this,this.slider);
        }
    }

    @Override
    protected Slider getWidget(Composite parent) {
        if (this.slider == null && parent != null) {
            this.slider = new Slider(parent,this.flags);

            this.rangeProperty.manage(this.slider);
            this.sizeProperty.manage(this.slider);
        }
        return this.slider;
    }

    public static SWTSlider slider(int flags) {
        return new SWTSlider(flags);
    }

}
