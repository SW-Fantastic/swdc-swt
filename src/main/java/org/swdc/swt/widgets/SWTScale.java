package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.beans.RangeProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

public class SWTScale extends SWTControlWidget<Scale> implements Selectionable {

    private int flags;

    private Scale scale;

    private RangeProperty rangeProperty = new RangeProperty();

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTScale(int flags) {
        this.flags = flags;
    }

    public SWTScale max(int max) {
        rangeProperty.setMax(max);
        return this;
    }

    public SWTScale min(int min) {
        rangeProperty.setMin(min);
        return this;
    }

    public SWTScale increment(int inc) {
        rangeProperty.setIncrease(inc);
        return this;
    }

    @Override
    public void initWidget(Scale created) {
        if (scale != null) {
            super.initWidget(scale);
            selectionProperty.manage(this);
            rangeProperty.manage(scale);
            scale.addSelectionListener(selectionProperty.dispatcher());
            SWTWidgets.setupLayoutData(this,scale);
        }
    }

    @Override
    public Scale getWidget(Composite parent) {
        if (this.scale == null && parent != null) {
            scale = new Scale(parent,this.flags);
            initWidget(scale);
        }
        return scale;
    }

    @Override
    public Scale getWidget() {
        return scale;
    }

    public static SWTScale scale(int flags) {
        return new SWTScale(flags);
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
}
