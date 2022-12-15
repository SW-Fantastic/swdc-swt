package org.swdc.swt.widgets.base;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.swdc.swt.SWTViewLoader;
import org.swdc.swt.beans.ExpandProperty;
import org.swdc.swt.widgets.SWTContainer;

public abstract class SWTCollapseControlWidget<T extends Control> extends SWTExpandableControlWidget<T> {

    private ExpandProperty expandProperty = new ExpandProperty();

    @Override
    public void initWidget(T created) {
        if (created == null) {
            return;
        }
        super.initWidget(created);
        expandProperty.manage(created);
    }

    public void expand(boolean expand) {
        this.expandProperty.set(expand);
    }

    public Boolean expand() {
        return expandProperty.get();
    }


}
