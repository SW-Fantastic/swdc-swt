package org.swdc.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ObservableSizeValue;
import org.swdc.swt.beans.ObservableValue;

public class SWTText extends SWTWidget<Text> {

    private int flags;

    private ObservableValue<String> text = new ObservableValue<>();

    private ObservableValue<Point> size = new ObservableSizeValue(new Point(SWT.DEFAULT,SWT.DEFAULT));

    private Text textField;

    public SWTText(int flag, String text) {
        this.flags = flag;
        this.text.set(text);
        this.text.addListener(((oldVal, newVal) -> {
            if (textField != null && !this.text.isEmpty()) {
                textField.setText(this.text.get());
            }
        }));

        this.size.addListener(((oldVal, newVal) -> {
            if (textField != null && !this.size.isEmpty()) {
                textField.setSize(this.size.get());
            }
        }));
    }


    public SWTText text(String text) {
        this.text.set(text);
        if (this.textField != null) {
            this.textField.setText(text);
        }
        return this;
    }

    public SWTText size(int width, int height) {
        this.size.set(new Point(width,height));
        if (this.textField != null) {
            this.textField.setSize(width,height);
        }
        return this;
    }


    @Override
    public Text getWidget(Composite parent) {
        if (this.textField == null && parent != null) {
            if (SWTWidgets.isFormAPI(parent)) {
                FormToolkit toolkit = SWTWidgets.factory();
                textField = toolkit.createText(parent,"",this.flags);
                toolkit.paintBordersFor(parent);
            } else {
                textField = new Text(parent,this.flags);
            }
            textField.setSize(this.size.get());
            if (this.getLayoutData() != null) {
                textField.setLayoutData(this.getLayoutData().get());
            }
            if (!this.text.isEmpty()) {
                textField.setText(this.text.get());
            }
        }
        return textField;
    }
    public static SWTText textView(int flags, String text)  {
        return new SWTText(flags,text);
    }

}
