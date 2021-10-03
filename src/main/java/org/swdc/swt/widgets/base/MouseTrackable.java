package org.swdc.swt.widgets.base;

import groovy.lang.Closure;

public interface MouseTrackable {

    void onMouseEnter(String method);

    void onMouseEnter(Closure closure);

    void onMouseExit(String method);

    void onMouseExit(Closure closure);

    void onMouseHover(String method);

    void onMouseHover(Closure closure);

}
