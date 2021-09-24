package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.swdc.swt.beans.SizeProperty;

public class SWTDateTime extends SWTWidget<DateTime> {

    private int flag;

    private DateTime dateTime;

    private SizeProperty sizeProperty = new SizeProperty();

    public SWTDateTime(int flag) {
        this.flag = flag;
    }

    public SWTDateTime size(int width, int height) {
        this.sizeProperty.set(width,height);
        return this;
    }

    @Override
    public DateTime getWidget(Composite parent) {
        if (dateTime == null && parent != null) {
            dateTime = new DateTime(parent,this.flag);
            if (this.getLayoutData() != null ) {
                dateTime.setLayoutData(getLayoutData().get());
            }
            sizeProperty.manage(dateTime);
        }
        return dateTime;
    }

    public static SWTDateTime dateTime(int flag) {
        return new SWTDateTime(flag);
    }

}
