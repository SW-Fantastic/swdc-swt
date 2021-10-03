package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.widgets.base.Controlable;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

public class SWTText extends SWTLabelControlWidget<Text> implements Controlable {

    private int flags;

    private Text textField;


    public SWTText(int flag, String text) {
        this.flags = flag;
        this.text(text);
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
        }
        return textField;
    }
    public static SWTText textView(int flags, String text)  {
        return new SWTText(flags,text);
    }

}
