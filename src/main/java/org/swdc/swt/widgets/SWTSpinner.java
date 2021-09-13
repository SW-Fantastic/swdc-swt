package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

public class SWTSpinner extends SWTWidget<Spinner> {

    private Spinner spinner;

    private int flag;

    public SWTSpinner(int flag) {
        this.flag = flag;
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
