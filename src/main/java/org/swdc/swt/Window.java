package org.swdc.swt;

import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;

public interface Window {

    void show();

    default <R extends SWTWidget> R create() {
        SWTWidgets.loadViewsByAnnotation(this);
        return (R) this;
    }

}
