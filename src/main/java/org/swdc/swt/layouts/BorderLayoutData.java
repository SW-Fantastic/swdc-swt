package org.swdc.swt.layouts;

import org.eclipse.swt.graphics.Point;


/**
 * Indicates the region that a control belongs to.
 *
 */
public class BorderLayoutData {

    public int region; // default.

    private Point point;

    public BorderLayoutData(int region) {
        this.region = region;
    }

    public BorderLayoutData(int region,int width, int height) {
        this.point = new Point(width,height);
        this.region = region;
    }

    public Point getDefaultSize() {
        return point;
    }

}
