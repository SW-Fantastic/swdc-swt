package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

public class SWTLabel extends SWTLabelControlWidget<Label> {

    private Label label;

    private int flag;

    public SWTLabel(int flag, String text) {
        this.flag = flag;
        this.text(text);
    }

    @Override
    public void ready() {
        super.ready();
        if (this.label != null) {
            SWTWidgets.setupLayoutData(this,label);
        }
    }

    protected Label getWidget(Composite parent) {
        if (label == null && parent != null) {
            if (SWTWidgets.isFormAPI(parent)) {
                FormToolkit toolkit = SWTWidgets.factory();
                label = toolkit.createLabel(parent,"",this.flag);
                toolkit.paintBordersFor(parent);
            }  else {
                label =  new Label(parent,flag);
            }
        }
        return label;
    }


    public static SWTLabel label(int flag, String text) {
        return new SWTLabel(flag,text);
    }

}
