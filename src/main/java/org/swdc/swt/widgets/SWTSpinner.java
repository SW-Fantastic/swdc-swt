package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.beans.RangeProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

public class SWTSpinner extends SWTControlWidget<Spinner> implements Selectionable {

    private Spinner spinner;

    private int flag;

    private RangeProperty rangeProperty = new RangeProperty();

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTSpinner(int flag) {
        this.flag = flag;
    }

    public SWTSpinner min(int min) {
        this.rangeProperty.setMin(min);
        return this;
    }

    public SWTSpinner max(int max) {
        this.rangeProperty.setMax(max);
        return this;
    }

    public SWTSpinner increment(int inc) {
        this.rangeProperty.setIncrease(inc);
        return this;
    }

    @Override
    public Spinner getWidget() {
        return spinner;
    }

    @Override
    public void initWidget(Spinner created) {
        if (this.spinner != null) {
            selectionProperty.manage(this);
            rangeProperty.manage(spinner);
            spinner.addSelectionListener(selectionProperty.dispatcher());
            SWTWidgets.setupLayoutData(this,spinner);
            super.initWidget(spinner);
        }
    }

    @Override
    public Spinner getWidget(Composite parent) {
        if (this.spinner == null && parent != null) {
            spinner = new Spinner(parent,this.flag);
            initWidget(spinner);
        }
        return spinner;
    }

    @Override
    public void onAction(String methodName) {
        this.selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        selectionProperty.closure(closure);
    }

    public static SWTSpinner spinner(int flag) {
        return new SWTSpinner(flag);
    }

}
