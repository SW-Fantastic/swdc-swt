package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.widgets.base.SWTControlWidget;

public class SWTCanvas extends SWTControlWidget<Canvas> {

    private int flag;

    private Canvas canvas;

    public SWTCanvas(int flag) {
        this.flag = flag;
    }

    @Override
    public void initWidget(Canvas created) {
        if (created == null) {
            return;
        }
        super.initWidget(created);
        SWTWidgets.setupLayoutData(this,created);
    }

    @Override
    public Canvas getWidget() {
        return canvas;
    }

    @Override
    public Canvas getWidget(Composite parent) {
        if (this.canvas == null && parent != null) {
            this.canvas = new Canvas(parent,this.flag);
            initWidget(canvas);
        }
        return canvas;
    }
}
