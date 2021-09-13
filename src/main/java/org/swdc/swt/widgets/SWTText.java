package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class SWTText extends SWTWidget<Text> {

    private int flags;

    private String text;

    private int width;
    private int height;

    private Text textField;

    public SWTText(int flag, String text) {
        this.flags = flag;
        this.text = text;
    }


    public SWTText text(String text) {
        this.text = text;
        if (this.textField != null) {
            this.textField.setText(text);
        }
        return this;
    }

    public SWTText size(int width, int height) {
        this.width = width;
        this.height = height;
        if (this.textField != null) {
            this.textField.setSize(width,height);
        }
        return this;
    }


    @Override
    public Text getWidget(Composite parent) {
        if (this.textField == null && parent != null) {
            textField = new Text(parent,this.flags);
            textField.setSize(this.width,this.height);
            if (this.getLayoutData() != null) {
                textField.setLayoutData(this.getLayoutData().get());
            }
            if (this.text != null) {
                textField.setText(this.text);
            }
        }
        return textField;
    }
    public static SWTText textView(int flags, String text)  {
        return new SWTText(flags,text);
    }

}
