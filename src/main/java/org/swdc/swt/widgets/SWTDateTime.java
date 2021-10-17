package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.widgets.base.Controlable;
import org.swdc.swt.widgets.base.SWTControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

public class SWTDateTime extends SWTControlWidget<DateTime> implements Selectionable {

    private int flag;

    private DateTime dateTime;

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTDateTime(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready() {
        super.ready();
        if (this.dateTime != null) {
            SWTWidgets.setupLayoutData(this,this.dateTime);
            selectionProperty.manage(this);
            dateTime.addSelectionListener(selectionProperty.dispatcher());
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

    @Override
    public void onAction(String methodName) {
        selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        selectionProperty.closure(closure);
    }
}
