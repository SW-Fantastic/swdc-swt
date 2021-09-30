package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;

public class SWTText extends SWTWidget<Text> {

    private int flags;

    private TextProperty textProperty = new TextProperty();

    private Text textField;

    public SWTText(int flag, String text) {
        this.flags = flag;
        this.textProperty.set(text);
    }


    public SWTText text(String text) {
        this.textProperty.set(text);
        return this;
    }


    @Override
    public void ready(Stage stage) {
        if (textField == null) {
            return;
        }
        SWTWidgets.setupLayoutData(this,this.textField);
    }

    @Override
    protected Text getWidget(Composite parent) {
        if (this.textField == null && parent != null) {
            if (SWTWidgets.isFormAPI(parent)) {
                FormToolkit toolkit = SWTWidgets.factory();
                textField = toolkit.createText(parent,"",this.flags);
                toolkit.paintBordersFor(parent);
            } else {
                textField = new Text(parent,this.flags);
            }

            textProperty.manage(textField);
        }
        return textField;
    }
    public static SWTText textView(int flags, String text)  {
        return new SWTText(flags,text);
    }

}
