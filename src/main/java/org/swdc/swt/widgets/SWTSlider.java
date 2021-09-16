package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Slider;

public class SWTSlider extends SWTWidget<Slider> {

    private int flags;

    private Slider slider;

    private int max;

    private int min;

    private int increment;

    public SWTSlider(int flags) {
        this.flags = flags;
    }

    public SWTSlider min(int min) {
        this.min = min;
        if (this.slider != null) {
            this.slider.setMinimum(min);
        }
        return this;
    }

    public SWTSlider max(int max) {
        this.max = max;
        if (slider != null) {
            slider.setMaximum(max);
        }
        return this;
    }

    public SWTSlider increment(int inc) {
        this.increment = inc;
        if (this.slider != null) {
            this.slider.setIncrement(inc);
        }
        return this;
    }

    @Override
    public Slider getWidget(Composite parent) {
        if (this.slider == null && parent != null) {
            this.slider = new Slider(parent,this.flags);
            if (this.getLayoutData() != null) {
                this.slider.setLayoutData(getLayoutData().get());
            }
            this.slider.setMaximum(max);
            this.slider.setMinimum(min);
            this.slider.setIncrement(increment);
        }
        return this.slider;
    }

    public static SWTSlider slider(int flags) {
        return new SWTSlider(flags);
    }

}
