package org.swdc.swt.widgets.base;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.swdc.swt.SWTViewLoader;
import org.swdc.swt.beans.ExpandProperty;
import org.swdc.swt.widgets.SWTContainer;

public abstract class SWTCollapseControlWidget<T extends Control> extends SWTExpandableControlWidget<T> {

    private ExpandProperty expandProperty = new ExpandProperty();

    @Override
    public T create(Composite parent, SWTContainer parentWidget, SWTViewLoader loader) {
        T widget = super.create(parent, parentWidget, loader);
        if (widget != null) {
            expandProperty.manage(widget);
        }
        return widget;
    }

    public void expand(boolean expand) {
        this.expandProperty.set(expand);
    }

    public Boolean expand() {
        return expandProperty.get();
    }


}
