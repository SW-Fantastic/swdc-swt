package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;

public class SWTDateTime extends SWTWidget<DateTime> {

    private int flag;

    private DateTime dateTime;

    public SWTDateTime(int flag) {
        this.flag = flag;
    }

    @Override
    public DateTime getWidget(Composite parent) {
        if (dateTime == null && parent != null) {
            dateTime = new DateTime(parent,this.flag);
            if (this.getLayoutData() != null ) {
                dateTime.setLayoutData(getLayoutData().get());
            }
        }
        return dateTime;
    }

    public static SWTDateTime dateTime(int flag) {
        return new SWTDateTime(flag);
    }

}
