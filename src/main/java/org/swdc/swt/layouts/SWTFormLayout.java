package org.swdc.swt.layouts;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Layout;

public class SWTFormLayout implements SWTLayout{

    private FormLayout layout = new FormLayout();


    public SWTFormLayout margin(int marginLeftRight, int marginTopBottom) {
        layout.marginWidth = marginLeftRight;
        layout.marginHeight = marginTopBottom;
        return this;
    }

    public SWTFormLayout marginLeft(int margin) {
        layout.marginLeft = margin;
        return this;
    }

    public SWTFormLayout marginRight(int margin) {
        layout.marginRight = margin;
        return this;
    }

    public SWTFormLayout marginTop(int margin) {
        layout.marginTop = margin;
        return this;
    }

    public SWTFormLayout marginBottom(int margin) {
        layout.marginBottom = margin;
        return this;
    }

    public SWTFormLayout spacing(int spec) {
        layout.spacing = spec;
        return this;
    }

    @Override
    public Layout getLayout() {
        return layout;
    }
}
