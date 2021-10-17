package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Slider;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.beans.RangeProperty;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

public class SWTSlider extends SWTControlWidget<Slider> implements Selectionable {

    private int flags;

    private Slider slider;

    private RangeProperty rangeProperty = new RangeProperty();

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTSlider(int flags) {
        this.flags = flags;
    }

    public SWTSlider min(int min) {
        rangeProperty.setMin(min);
        return this;
    }

    public SWTSlider max(int max) {
        this.rangeProperty.setMax(max);
        return this;
    }

    public SWTSlider increment(int inc) {
        this.rangeProperty.setIncrease(inc);
        return this;
    }

    @Override
    public void ready() {
        super.ready();
        if (this.slider != null) {
            selectionProperty.manage(this);
            slider.addSelectionListener(selectionProperty.dispatcher());
            SWTWidgets.setupLayoutData(this,this.slider);
        }
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

    @Override
    protected Slider getWidget(Composite parent) {
        if (this.slider == null && parent != null) {
            this.slider = new Slider(parent,this.flags);

            this.rangeProperty.manage(this.slider);
        }
        return this.slider;
    }

    public static SWTSlider slider(int flags) {
        return new SWTSlider(flags);
    }

}
