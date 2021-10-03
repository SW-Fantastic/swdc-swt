package org.swdc.swt.widgets.base;

import groovy.lang.Closure;

public interface KeyListeneable {

    void onKeyPressed(String method);

    void onKeyPressed(Closure closure);

    void onKeyRelease(String method);

    void onKeyRelease(Closure closure);

}
