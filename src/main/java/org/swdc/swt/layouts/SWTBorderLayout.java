package org.swdc.swt.layouts;

import org.eclipse.swt.widgets.Layout;

public class SWTBorderLayout implements SWTLayout {

    private BorderLayout layout;

    public SWTBorderLayout() {
        this.layout = new BorderLayout();
    }

    public static SWTBorderLayoutData left(int width) {
        BorderLayoutData data = new BorderLayoutData(BorderLayout.WEST,width,0);
        return new SWTBorderLayoutData(data);
    }

    public static SWTBorderLayoutData right(int width) {
        BorderLayoutData data = new BorderLayoutData(BorderLayout.EAST,width,0);
        return new SWTBorderLayoutData(data);
    }

    public static SWTBorderLayoutData top(int height) {
        BorderLayoutData data = new BorderLayoutData(BorderLayout.NORTH,0,height);
        return new SWTBorderLayoutData(data);
    }

    public static SWTBorderLayoutData bottom(int height) {
        BorderLayoutData data = new BorderLayoutData(BorderLayout.SOUTH,0,height);
        return new SWTBorderLayoutData(data);
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    public static SWTBorderLayout borderLayout() {
        return new SWTBorderLayout();
    }

}
