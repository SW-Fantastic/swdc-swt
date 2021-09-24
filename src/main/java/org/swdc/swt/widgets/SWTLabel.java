package org.swdc.swt.widgets;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ColorProperty;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;

public class SWTLabel extends SWTWidget<Label> {

    private Label label;

    private TextProperty text = new TextProperty();
    private SizeProperty sizeProperty = new SizeProperty();

    private ColorProperty colorProperty = new ColorProperty();

    private int flag;

    public SWTLabel(int flag, String text) {
        this.text.set(text);
        this.flag = flag;
    }


    public Label getWidget(Composite parent) {
        if (label == null && parent != null) {
            if (SWTWidgets.isFormAPI(parent)) {
                FormToolkit toolkit = SWTWidgets.factory();
                label = toolkit.createLabel(parent,"",this.flag);
                toolkit.paintBordersFor(parent);
            }  else {
                label =  new Label(parent,flag);
            }
            if (this.getLayoutData() != null) {
                label.setLayoutData(this.getLayoutData().get());
            }
            this.text.manage(label);
            this.sizeProperty.manage(label);
            this.colorProperty.manage(label);
        }
        return label;
    }

    public SWTLabel text(String text) {
        this.text.set(text);
        return this;
    }

    public SWTLabel color(String color) {
        this.colorProperty.setForeground(color);
        return this;
    }

    public SWTLabel backgroundColor(String color) {
        colorProperty.setBackground(color);
        return this;
    }

    public SWTLabel size(int width, int height) {
        this.sizeProperty.set(width,height);
        return this;
    }

    public String text() {
        return text.get();
    }

    public static SWTLabel label(int flag, String text) {
        return new SWTLabel(flag,text);
    }

}
