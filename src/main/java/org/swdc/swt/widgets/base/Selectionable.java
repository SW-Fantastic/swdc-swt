package org.swdc.swt.widgets.base;

import groovy.lang.Closure;

public interface Selectionable {

    void onAction(String methodName);

    void onAction(Closure closure);

}
