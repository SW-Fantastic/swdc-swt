package org.swdc.swt.beans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Widget;

import java.lang.reflect.Method;

public class SizeProperty implements Property<Point> {

    private ObservableValue<Point> size = new ObservableValue<>(new Point(SWT.DEFAULT,SWT.DEFAULT));
    private Widget widget;

    private Method setter;
    private Method getter;

    private Method widthSetter;
    private Method widthGetter;

    private Method heightSetter;
    private Method heightGetter;

    private Method layoutMethod;

    public SizeProperty() {
    }

    public void unlink() {
        if (this.widget != null) {
            setter = null;
            widthSetter = null;
            heightSetter = null;
            layoutMethod = null;
            this.size.removeListener(this::onSizeChange);
        }
    }

    public void manage(Widget composite) {
        this.unlink();
        this.widget = composite;
        try {
            setter = widget.getClass().getMethod("setSize", Point.class);
        } catch (Exception e) {
        }

        try {
            getter = widget.getClass().getMethod("getSize");
        } catch (Exception e) {
        }

        try {
            widthSetter = widget.getClass().getMethod("setWidth", int.class);
        } catch (Exception e) {
        }
        try {
            heightSetter = widget.getClass().getMethod("setHeight", int.class);
        } catch (Exception e) {
        }
        try {
            layoutMethod = widget.getClass().getMethod("requestLayout");
        } catch (Exception e){
            try {
                layoutMethod = widget.getClass().getMethod("park");
            } catch (Exception ex) {
            }
        }
        this.onSizeChange(null,null);
        this.size.addListener(this::onSizeChange);
    }

    private void onSizeChange(Point oldVal, Point newVal) {
        if (this.widget == null || size.isEmpty()) {
            return;
        }
        try {
            Point size = this.size.get();
            if (this.setter != null){
                this.setter.invoke(widget,size);
            } else  {
                if (widthSetter != null) {
                    widthSetter.invoke(widget,size.x);
                }
                if (heightSetter !=  null) {
                    heightSetter.invoke(widget,size.y);
                }
            }

            if (layoutMethod != null) {
                layoutMethod.invoke(widget);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void set(int width, int height) {
        this.set(new Point(width,height));
    }

    public void set(Point val) {
        this.size.set(val);
    }

    @Override
    public Point get() {
        if (this.getter == null) {
            return size.isEmpty() ? new Point(SWT.DEFAULT,SWT.DEFAULT) : size.get();
        }
        try {
            Point val = (Point) this.getter.invoke(widget);
            if (val == null) {
                return size.isEmpty() ? new Point(SWT.DEFAULT,SWT.DEFAULT) : size.get();
            }
            this.size.set(val);
            if (this.widthSetter != null) {
                this.widthSetter.invoke(widget,val.x);
            }
            if (this.heightSetter != null) {
                this.heightSetter.invoke(widget,val.y);
            }
            return size.isEmpty() ? new Point(SWT.DEFAULT,SWT.DEFAULT) : size.get();
        } catch (Exception e) {
            return size.isEmpty() ? new Point(SWT.DEFAULT,SWT.DEFAULT) : size.get();
        }
    }

    public void width(int width) {
        Point size;
        if (!this.size.isEmpty()) {
            size = this.size.get();
        } else {
            size = new Point(SWT.DEFAULT,SWT.DEFAULT);
        }
        size.x = width;
        this.size.set(size);
    }

    public void height(int height) {
        Point size;
        if (!this.size.isEmpty()) {
            size = this.size.get();
        } else {
            size = new Point(SWT.DEFAULT,SWT.DEFAULT);
        }
        size.y = height;
        this.size.set(size);
    }

    public void setDirectly(Point point){
        this.size.setWithoutListener(point);
    }

    public ObservableSizeValue valueSize() {
        return (ObservableSizeValue) this.size;
    }

}
