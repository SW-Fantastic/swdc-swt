package org.swdc.dsl;

import org.swdc.swt.SWTResource;
import org.swdc.swt.views.AbstractSplash;

import javax.swing.*;

public class SWTTestSplash extends AbstractSplash {

    private JWindow window;

    public SWTTestSplash(SWTResource resource) {
        super(resource);
    }

    @Override
    public JWindow getSplash() {
        if (this.window == null) {
            this.window = new JWindow();
        }
        return this.window;
    }
}
