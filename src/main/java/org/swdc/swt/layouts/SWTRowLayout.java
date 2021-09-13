package org.swdc.swt.layouts;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Layout;

public class SWTRowLayout implements SWTLayout {

    private RowLayout layout;

    public SWTRowLayout(int flags) {
        layout = new RowLayout(flags);
    }

    public SWTRowLayout justify(boolean justify) {
        layout.justify = justify;
        return this;
    }

    public SWTRowLayout center(boolean center) {
        layout.center = center;
        return this;
    }

    public SWTRowLayout margin(int marginLeftRight, int marginTopBottom) {
        layout.marginWidth = marginLeftRight;
        layout.marginHeight = marginTopBottom;
        return this;
    }

    public SWTRowLayout marginLeft(int margin) {
        layout.marginLeft = margin;
        return this;
    }

    public SWTRowLayout marginRight(int margin) {
        layout.marginRight = margin;
        return this;
    }

    public SWTRowLayout marginTop(int margin) {
        layout.marginTop = margin;
        return this;
    }

    public SWTRowLayout marginBottom(int margin) {
        layout.marginBottom = margin;
        return this;
    }

    public SWTRowLayout spacing(int spec) {
        layout.spacing = spec;
        return this;
    }

    public SWTRowLayout wrap(boolean wrap) {
        layout.wrap = wrap;
        return this;
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    public static SWTRowLayout rowLayout(int flags) {
        return new SWTRowLayout(flags);
    }

}
