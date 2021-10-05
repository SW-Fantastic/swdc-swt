package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.swdc.swt.widgets.base.Controlable;
import org.swdc.swt.widgets.base.SWTControlWidget;

public class SWTDateTime extends SWTControlWidget<DateTime> implements Controlable {

    private int flag;

    private DateTime dateTime;

    public SWTDateTime(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready() {
        super.ready();
        if (this.dateTime != null) {
            SWTWidgets.setupLayoutData(this,this.dateTime);
        }
    }

    @Override
    protected DateTime getWidget(Composite parent) {
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
