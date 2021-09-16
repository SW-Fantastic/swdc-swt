package org.swdc.dsl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.swdc.swt.Widget;
import org.swdc.swt.widgets.SWTLabel;

public class TestController {

    @Widget("lblTest")
    private SWTLabel label;

    private boolean flag = false;

    public void hello(SelectionEvent event) {
        System.err.println("Hello it was clicked");
        if (flag) {
            label.text("Test Flag A");
            label.size(180, SWT.DEFAULT);
        } else {
            label.text("Test Flag B");
            label.size(120,SWT.DEFAULT);
        }
        flag = !flag;
    }

}
