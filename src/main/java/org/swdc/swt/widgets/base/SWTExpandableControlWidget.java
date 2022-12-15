package org.swdc.swt.widgets.base;

import groovy.lang.Closure;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.Control;
import org.swdc.swt.actions.CollapseProperty;

import java.lang.reflect.Method;

public abstract class SWTExpandableControlWidget<T extends Control> extends SWTLabelControlWidget<T> {

    private CollapseProperty collapseProperty = new CollapseProperty();

    private Closure expandClosure;
    private Closure collapseClosure;

    @Override
    public void initWidget(T created) {
        try {
            super.initWidget(created);
            collapseProperty.manage(this);
            Method adder = created.getClass().getMethod("addExpandListener", new Class[]{ExpandListener.class});
            adder.invoke(created,collapseProperty.dispatcher());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void onExpand(String method) {
        this.collapseProperty.setExpandMethod(method);
    }

    public void onCollapse(String method) {
        this.collapseProperty.setCollapseMethod(method);
    }

    public void onExpand(Closure expand){
        expand.setDelegate(this);
        expand.setResolveStrategy(Closure.DELEGATE_ONLY);
        this.expandClosure = expand;

        collapseProperty.closure(this.collapseClosure,this.expandClosure);
    }

    public void onCollapse(Closure collapse) {
        collapse.setDelegate(this);
        collapse.setResolveStrategy(Closure.DELEGATE_ONLY);
        this.collapseClosure = collapse;

        collapseProperty.closure(this.collapseClosure,this.expandClosure);
    }

}
