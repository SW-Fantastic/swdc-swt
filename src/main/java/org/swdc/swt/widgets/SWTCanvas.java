package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class SWTCanvas extends SWTWidget<Canvas> {

    private int flag;

    private Canvas canvas;

    public SWTCanvas(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready(Stage stage) {
        if (canvas == null) {
            return;
        }
        SWTWidgets.setupLayoutData(this,this.canvas);
    }

    @Override
    protected Canvas getWidget(Composite parent) {
        if (this.canvas == null && parent != null) {
            this.canvas = new Canvas(parent,this.flag);
        }
        return canvas;
    }
}
