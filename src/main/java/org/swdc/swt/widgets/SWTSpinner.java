package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.swdc.swt.beans.RangeProperty;
import org.swdc.swt.beans.SizeProperty;

public class SWTSpinner extends SWTWidget<Spinner> {

    private Spinner spinner;

    private int flag;

    private RangeProperty rangeProperty = new RangeProperty();

    private SizeProperty sizeProperty = new SizeProperty();

    public SWTSpinner(int flag) {
        this.flag = flag;
    }

    public SWTSpinner min(int min) {
        this.rangeProperty.setMin(min);
        return this;
    }

    public SWTSpinner max(int max) {
        this.rangeProperty.setMax(max);
        return this;
    }

    public SWTSpinner increment(int inc) {
        this.rangeProperty.setIncrease(inc);
        return this;
    }

    public SWTSpinner size(int width, int height) {
        this.sizeProperty.set(width,height);
        return this;
    }

    @Override
    public Spinner getWidget(Composite parent) {
        if (this.spinner == null && parent != null) {
            spinner = new Spinner(parent,this.flag);
            if (this.getLayoutData() != null) {
                spinner.setLayoutData(this.getLayoutData().get());
            }
            rangeProperty.manage(spinner);
            sizeProperty.manage(spinner);
        }
        return spinner;
    }

    public static SWTSpinner spinner(int flag) {
        return new SWTSpinner(flag);
    }

}
