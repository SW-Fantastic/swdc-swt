package org.swdc.swt.widgets.base;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;

public abstract class SWTLabelWidget <T extends Widget> extends SWTWidget<T> {

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

    public <R extends SWTLabelWidget> R text(String text) {
        property.set(text);
        return (R) this;
    }

    public String text() {
        return property.get();
    }

}
