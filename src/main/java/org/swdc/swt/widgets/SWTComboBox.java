package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.widgets.base.Controlable;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

public class SWTComboBox extends SWTLabelControlWidget<Combo> implements Controlable {

    private int flag;

    private Combo combo;

    public SWTComboBox(int flag) {
        this.flag = flag;
    }

    public SWTComboBox text(String text) {
        return this;
    }

    @Override
    public void ready(Stage stage) {
        if (combo != null) {
            SWTWidgets.setupLayoutData(this,this.combo);
        }
    }

    @Override
    protected Combo getWidget(Composite parent) {
        if (this.combo == null && parent != null) {
            this.combo = new Combo(parent,this.flag);
        }
        return combo;
    }


    public static SWTComboBox comboBox(int flag) {
        return new SWTComboBox(flag);
    }

}
