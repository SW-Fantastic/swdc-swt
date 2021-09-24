package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;

public class SWTComboBox extends SWTWidget<Combo> {

    private int flag;

    private TextProperty text = new TextProperty();

    private SizeProperty sizeProperty = new SizeProperty();

    private Combo combo;

    public SWTComboBox(int flag) {
        this.flag = flag;
    }

    public SWTComboBox size(int width, int height) {
        sizeProperty.set(width,height);
        return this;
    }

    public SWTComboBox text(String text) {
        this.text.set(text);
        return this;
    }


    @Override
    public Combo getWidget(Composite parent) {
        if (this.combo == null && parent != null) {
            this.combo = new Combo(parent,this.flag);

            if (this.getLayoutData() != null) {
                this.combo.setLayoutData(this.getLayoutData().get());
            }

            this.sizeProperty.manage(combo);
            this.text.manage(combo);
        }
        return combo;
    }

    public static SWTComboBox comboBox(int flag) {
        return new SWTComboBox(flag);
    }

}
