package org.swdc.swt.widgets.base;

import groovy.lang.Closure;

public interface MouseWheelable {

    void onMouseWheel(String method);

    void onMouseWheel(Closure closure);

}
