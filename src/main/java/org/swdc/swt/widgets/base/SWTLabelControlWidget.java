package org.swdc.swt.widgets.base;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.SWTContainer;

public abstract class SWTLabelControlWidget<T extends Control> extends SWTControlWidget<T> {

    private TextProperty property = new TextProperty();

    @Override
    public T create(Composite parent, SWTContainer parentWidget) {
        T widget = super.create(parent, parentWidget);
        if (widget == null) {
            return widget;
        }
        property.manage(widget);
        return widget;
    }

    public <R extends SWTLabelControlWidget> R text(String text) {
        property.set(text);
        return (R) this;
    }

    public String text() {
        return property.get();
    }

}
