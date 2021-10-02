package org.swdc.swt.widgets;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.widgets.base.Controlable;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

public class SWTCComboBox extends SWTLabelControlWidget<CCombo> implements Controlable {

    private int flag;
    private CCombo cCombo;

    public SWTCComboBox(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready(Stage stage) {
        if (cCombo == null){
            return;
        }
    }

    @Override
    protected CCombo getWidget(Composite parent) {
        if (cCombo == null && parent != null) {
            cCombo = new CCombo(parent,this.flag);
        }
        return cCombo;
    }
}
