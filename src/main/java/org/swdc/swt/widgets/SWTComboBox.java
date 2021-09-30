package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.layouts.LayoutData;
import org.swdc.swt.layouts.SWTFormData;

public class SWTComboBox extends SWTWidget<Combo> {

    private int flag;

    private TextProperty text = new TextProperty();

    private Combo combo;

    public SWTComboBox(int flag) {
        this.flag = flag;
    }

    public SWTComboBox text(String text) {
        this.text.set(text);
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

            this.text.manage(combo);
        }
        return combo;
    }

    public static SWTComboBox comboBox(int flag) {
        return new SWTComboBox(flag);
    }

}
