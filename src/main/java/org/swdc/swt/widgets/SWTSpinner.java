package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

public class SWTSpinner extends SWTWidget<Spinner> {

    private Spinner spinner;

    private int flag;

    private int max;
    private int min;
    private int increment;

    public SWTSpinner(int flag) {
        this.flag = flag;
    }

    public SWTSpinner min(int min) {
        this.min = min;
        if (this.spinner != null) {
            spinner.setMinimum(min);
        }
        return this;
    }

    public SWTSpinner max(int max) {
        this.max = max;
        if (this.spinner != null) {
            spinner.setMaximum(max);
        }
        return this;
    }

    public SWTSpinner increment(int inc) {
        this.increment = inc;
        if (this.spinner != null) {
            spinner.setIncrement(inc);
        }
        return this;
    }

    @Override
    public Spinner getWidget(Composite parent) {
        if (this.spinner == null && parent != null) {
            spinner = new Spinner(parent,this.flag);
            if (this.getLayoutData() != null) {
                spinner.setLayoutData(this.getLayoutData().get());
            }
        }
        return spinner;
    }

    public static SWTSpinner spinner(int flag) {
        return new SWTSpinner(flag);
    }

}
