package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.swdc.swt.beans.RangeProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;

public class SWTSpinner extends SWTControlWidget<Spinner> {

    private Spinner spinner;

    private int flag;

    private RangeProperty rangeProperty = new RangeProperty();

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

    @Override
    public void ready(Stage stage) {
        if (this.spinner != null) {
            SWTWidgets.setupLayoutData(this,spinner);
        }
    }

    @Override
    protected Spinner getWidget(Composite parent) {
        if (this.spinner == null && parent != null) {
            spinner = new Spinner(parent,this.flag);

            rangeProperty.manage(spinner);
        }
        return spinner;
    }

    public static SWTSpinner spinner(int flag) {
        return new SWTSpinner(flag);
    }

}
