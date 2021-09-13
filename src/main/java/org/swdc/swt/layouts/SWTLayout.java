package org.swdc.swt.layouts;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Layout;
import org.swdc.swt.Modifiable;

public interface SWTLayout extends Modifiable<SWTLayout> {

    Layout getLayout();


}
