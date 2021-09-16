package org.swdc.swt.beans;

import org.eclipse.swt.graphics.Point;

public class ObservableSizeValue extends ObservableValue<Point> {

    public ObservableSizeValue(Point point) {
        super(point);
    }

    @Override
    protected boolean doEquals(Point self, Point another) {
        return self.y == another.y && self.x == another.x;
    }

}
