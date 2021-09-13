package org.swdc.swt.layouts;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Layout;


public class SWTGridLayout implements SWTLayout{

    private GridLayout layout;

    public SWTGridLayout() {
        layout = new GridLayout();
    }

    public SWTGridLayout columns(int columnNumber) {
        layout.numColumns = columnNumber;
        return this;
    }

    public SWTGridLayout margin(int marginLeftRight, int marginTopBottom) {
        layout.marginWidth = marginLeftRight;
        layout.marginHeight = marginTopBottom;
        return this;
    }

    public SWTGridLayout marginLeft(int margin) {
        layout.marginLeft = margin;
        return this;
    }

    public SWTGridLayout marginRight(int margin) {
        layout.marginRight = margin;
        return this;
    }

    public SWTGridLayout marginTop(int margin) {
        layout.marginTop = margin;
        return this;
    }

    public SWTGridLayout marginBottom(int margin) {
        layout.marginBottom = margin;
        return this;
    }

    public SWTGridLayout columnSpacing(int spec) {
        layout.verticalSpacing = spec;
        return this;
    }

    public SWTGridLayout rowSpacing(int spec) {
        layout.horizontalSpacing = spec;
        return this;
    }

    public SWTGridLayout spacing(int specW, int specH) {
        layout.verticalSpacing = specH;
        layout.horizontalSpacing = specW;
        return this;
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    public static SWTGridLayout gridLayout() {
        return new SWTGridLayout();
    }

    public static SWTGridLayoutData cell() {
        return new SWTGridLayoutData();
    }

}
