package org.swdc.swt.widgets.base;

import groovy.lang.Closure;

public interface MouseMovable {

    void onMouseMove(String method);

    void onMouseMove(Closure closure);

}
