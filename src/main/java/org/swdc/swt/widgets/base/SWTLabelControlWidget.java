package org.swdc.swt.widgets.base;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.swdc.swt.SWTViewLoader;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.SWTContainer;

public abstract class SWTLabelControlWidget<T extends Control> extends SWTControlWidget<T> {

    private TextProperty property = new TextProperty();

    @Override
    public void initWidget(T widget) {
        super.initWidget(widget);
        if (widget == null) {
            return;
        }
        property.manage(widget);
    }

    public <R extends SWTLabelControlWidget> R text(String text) {
        property.set(text);
        return (R) this;
    }

    public String text() {
        return property.get();
    }

}
