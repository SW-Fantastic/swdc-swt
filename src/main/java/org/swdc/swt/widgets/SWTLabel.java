package org.swdc.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ObservableSizeValue;
import org.swdc.swt.beans.ObservableValue;

public class SWTLabel extends SWTWidget<Label> {

    private Label label;

    private ObservableValue<String> text = new ObservableValue<>("");
    private ObservableValue<Point> size = new ObservableSizeValue(new Point(SWT.DEFAULT,SWT.DEFAULT));

    private ObservableValue<String> color = new ObservableValue<>();
    private String backgroundColor;

    private int flag;

    public SWTLabel(int flag, String text) {
        this.text.set(text);

        this.text.addListener(((oldVal, newVal) -> {
            if (label != null) {
                label.setText(this.text.isEmpty()  ? "" : this.text.get());
            }
        }));

        this.color.addListener(((oldVal, newVal) -> {
            if (label != null && !color.isEmpty()) {
                Color swtColor = SWTWidgets.color(color.get()).getColor();
                if (swtColor != null) {
                    label.setForeground(swtColor);
                }
                label.setForeground(swtColor);
            }
        }));

        this.size.addListener(this::sizeChanged);
        this.flag = flag;
    }

    public void sizeChanged(Point oldVal, Point newVal) {
        if (this.label != null && !size.isEmpty()) {
            Point size = this.size.get();
            label.setSize(size);
            label.requestLayout();
        }
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
            if (!text.isEmpty()) {
                label.setText(text.get());
            }
            if (!color.isEmpty()) {
                this.color(color.get());
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
        this.text.set(text);
        return this;
    }

    public SWTLabel color(String color) {
        this.color.set(color);
        return this;
    }

    public SWTLabel backgroundColor(String color) {
        this.backgroundColor = color;
        if (label == null) {
            return this;
        }
        SWTColor swtColor = SWTWidgets.color(color);
        if (swtColor != null) {
            label.setBackground(swtColor.getColor());
        }
        return this;
    }

    public SWTLabel size(int width, int height) {
        this.size.set(new Point(width,height));
        return this;
    }

    public String text() {
        return text.get();
    }

    public static SWTLabel label(int flag, String text) {
        return new SWTLabel(flag,text);
    }

}
