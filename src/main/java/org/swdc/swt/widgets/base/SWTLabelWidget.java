package org.swdc.swt.widgets.base;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.swdc.swt.SWTViewLoader;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;

public abstract class SWTLabelWidget <T extends Widget> extends SWTWidget<T> {

    private TextProperty property = new TextProperty();

    @Override
    protected void initWidget(T created) {
        if (created == null) {
            return;
        }
        property.manage(created);
    }

    public <R extends SWTLabelWidget> R text(String text) {
        property.set(text);
        return (R) this;
    }

    public String text() {
        return property.get();
    }

}
