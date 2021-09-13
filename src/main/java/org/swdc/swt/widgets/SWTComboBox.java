package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class SWTComboBox extends SWTWidget<Combo> {

    private int flag;

    private String text;

    private int width;

    private int height;

    private Combo combo;

    public SWTComboBox(int flag) {
        this.flag = flag;
    }

    public SWTComboBox size(int width, int height) {
        if (this.combo != null) {
            this.combo.setSize(width,height);
        }
        this.width = width;
        this.height = height;
        return this;
    }

    public SWTComboBox text(String text) {
        if (this.combo != null) {
            combo.setText(text);
        }
        this.text = text;
        return this;
    }


    @Override
    public Combo getWidget(Composite parent) {
        if (this.combo == null && parent != null) {
            this.combo = new Combo(parent,this.flag);
            this.combo.setSize(this.width,this.height);

            if (this.text != null) {
                this.combo.setText(text);
            }

            if (this.getLayoutData() != null) {
                this.combo.setLayoutData(this.getLayoutData().get());
            }

        }
        return combo;
    }

    public static SWTComboBox comboBox(int flag) {
        return new SWTComboBox(flag);
    }

}
