package org.swdc.swt.widgets;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class SWTLabel extends SWTWidget<Label> {

    private Label label;

    private String text;

    private String color;

    private String backgroundColor;

    private int flag;

    public SWTLabel(int flag, String text) {
        this.text = text;
        this.flag = flag;
    }

    public Label getWidget(Composite parent) {
        if (label == null && parent != null) {
            label =  new Label(parent,flag);
            if (this.text != null) {
                label.setText(this.text);
            }
            if (color != null) {
                this.color(color);
            }
            if (backgroundColor != null) {
                this.backgroundColor(backgroundColor);
            }
            if (this.getLayoutData() != null) {
                label.setLayoutData(this.getLayoutData().get());
            }
        }
        return label;
    }

    public SWTLabel text(String text) {
        this.text = text;
        if (this.label != null) {
            this.label.setText(text);
        }
        return this;
    }

    public SWTLabel color(String color) {
        this.color = color;
        if (label == null) {
            return this;
        }
        Color swtColor = WidgetUtils.color(color).getColor();
        if (swtColor != null) {
            label.setForeground(swtColor);
        }
        return this;
    }

    public SWTLabel backgroundColor(String color) {
        this.backgroundColor = color;
        if (label == null) {
            return this;
        }
        SWTColor swtColor = WidgetUtils.color(color);
        if (swtColor != null) {
            label.setBackground(swtColor.getColor());
        }
        return this;
    }

    public SWTLabel size(int width, int height) {
        if (this.label == null) {
            return this;
        }
        this.label.setSize(width,height);
        return this;
    }

    public String text() {
        return text;
    }

    public static SWTLabel label(int flag, String text) {
        return new SWTLabel(flag,text);
    }

}
