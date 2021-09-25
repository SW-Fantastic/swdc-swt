package org.swdc.swt.layouts;

import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.swdc.swt.widgets.SWTWidget;


public class SWTStackLayout implements SWTLayout {

    private StackLayout layout = new StackLayout();

    public SWTStackLayout top(SWTWidget ctrl) {
        Control control = (Control) ctrl.getWidget();
        this.layout.topControl = control;
        control.requestLayout();
        return this;
    }

    public SWTStackLayout margin(int marginLeftRight, int marginTopBottom) {
        this.layout.marginWidth = marginLeftRight;
        this.layout.marginHeight = marginTopBottom;
        return this;
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

}
