package org.swdc.swt.beans;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Widget;

import java.lang.reflect.Method;

public class TextSelectionProperty implements Property<Point> {

    private ObservableSizeValue selection = new ObservableSizeValue(new Point(0,0));

    private Method setter;
    private Method getter;

    private Widget widget;

    @Override
    public void set(Point point) {
        selection.set(point);
    }

    @Override
    public Point get() {
        try {
            if (getter == null) {
                return selection.isEmpty() ? new Point(0,0) : selection.get();
            }
            Point selection = (Point) getter.invoke(this.widget);
            if (selection != null) {
                this.selection.set(selection);
            }
            return this.selection.isEmpty() ? new Point(0,0) : this.selection.get();
        }catch (Exception e) {
            return null;
        }
    }

    private void onSectionChange(Point valOld, Point valNew) {
        try {
            if (this.widget == null || this.selection.isEmpty() || this.setter == null) {
                return;
            }

            Point point = this.selection.get();
            setter.invoke(widget, point);
        }catch (Exception e) {
        }
    }

    @Override
    public void manage(Widget widget) {
        unlink();
        this.widget = widget;

        try {
            this.getter = widget.getClass().getMethod("getSelection");
        } catch (Exception e) {
        }

        try {
            this.setter = widget.getClass().getMethod("setSelection",new Class[] { Point.class});
        } catch (Exception e) {
        }

        this.onSectionChange(null,null);
        this.selection.addListener(this::onSectionChange);

    }

    @Override
    public void unlink() {
        this.selection.removeListener(this::onSectionChange);
        this.getter = null;
        this.setter = null;
    }
}
