package org.swdc.swt.widgets;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.widgets.base.Controlable;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

public class SWTCLabel extends SWTLabelControlWidget<CLabel> implements Controlable {

    private int flag;
    private CLabel label;

    public SWTCLabel(int flag) {
        this.flag = flag;
    }

    @Override
    public void initWidget(CLabel created) {
        if (this.label == null) {
            return;
        }
        super.initWidget(label);
    }

    @Override
    public CLabel getWidget() {
        return label;
    }

    @Override
    public CLabel getWidget(Composite parent) {
        if (label == null && parent != null) {
            label = new CLabel(parent,flag);
            initWidget(label);
        }
        return label;
    }

}
