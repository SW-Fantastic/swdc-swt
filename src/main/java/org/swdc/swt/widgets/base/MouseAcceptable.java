package org.swdc.swt.widgets.base;

import groovy.lang.Closure;

public interface MouseAcceptable {

    void onMouseDown(String method);

    void onMouseDown(Closure closure);

    void onMouseUp(String method);

    void onMouseUp(Closure closure);

    void onMouseDBClick(String method);

    void onMouseDBClick(Closure closure);

}
