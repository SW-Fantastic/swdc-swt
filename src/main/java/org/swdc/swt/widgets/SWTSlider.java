package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Slider;

public class SWTSlider extends SWTWidget<Slider> {

    private int flags;

    private Slider slider;

    public SWTSlider(int flags) {
        this.flags = flags;
    }

    @Override
    public Slider getWidget(Composite parent) {
        if (this.slider == null && parent != null) {
            this.slider = new Slider(parent,this.flags);
            if (this.getLayoutData() != null) {
                this.slider.setLayoutData(getLayoutData().get());
            }
        }
        return this.slider;
    }

    public static SWTSlider slider(int flags) {
        return new SWTSlider(flags);
    }

}
