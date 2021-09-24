package org.swdc.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ObservableSizeValue;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;

public class SWTText extends SWTWidget<Text> {

    private int flags;

    private TextProperty textProperty = new TextProperty();

    private SizeProperty sizeProperty = new SizeProperty();

    private Text textField;

    public SWTText(int flag, String text) {
        this.flags = flag;
        this.textProperty.set(text);
    }


    public SWTText text(String text) {
        this.textProperty.set(text);
        return this;
    }

    public SWTText size(int width, int height) {
        this.sizeProperty.set(width,height);
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
            if (this.getLayoutData() != null) {
                textField.setLayoutData(this.getLayoutData().get());
            }

            textField.addModifyListener(m -> {
                textProperty.setDirectly(textField.getText());
            });


            textProperty.manage(textField);
            sizeProperty.manage(textField);
        }
        return textField;
    }
    public static SWTText textView(int flags, String text)  {
        return new SWTText(flags,text);
    }

}
