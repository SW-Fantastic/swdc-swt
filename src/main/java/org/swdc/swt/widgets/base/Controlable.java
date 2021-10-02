package org.swdc.swt.widgets.base;

import groovy.lang.Closure;

public interface Controlable {

    void onResize(Closure closure);

    void onResize(String method);

    void onMove(Closure closure);

    void onMove(String method);

}
