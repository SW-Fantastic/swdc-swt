package org.swdc.swt.widgets.base;


import groovy.lang.Closure;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.swdc.swt.SWTViewLoader;
import org.swdc.swt.actions.ExpansionProperty;
import org.swdc.swt.beans.ExpandProperty;
import org.swdc.swt.widgets.SWTContainer;

import java.lang.reflect.Method;

public abstract class SWTExpansationControlWidget<T extends Control> extends SWTLabelControlWidget<T> {

    private ExpandProperty expandProperty = new ExpandProperty();

    private ExpansionProperty expansionProperty = new ExpansionProperty();


    @Override
    public void initWidget(T created) {
        super.initWidget(created);
        try {
            Method adder = created.getClass().getMethod("addExpansionListener", new Class[]{IExpansionListener.class});
            adder.invoke(created,expansionProperty.dispatcher());
            expansionProperty.manage(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onExpand(String method) {
        this.expansionProperty.setExpand(method);
    }

    public void onExpand(Closure closure) {
        closure.setDelegate(getWidget());
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        expansionProperty.closure(closure);
    }

    public void expand(boolean expand) {
        this.expandProperty.set(expand);
    }

    public Boolean expand() {
        return expandProperty.get();
    }

}
