package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

public class SWTText extends SWTLabelControlWidget<Text> implements Selectionable {

    private int flags;

    private Text textField;

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTText(int flag, String text) {
        this.flags = flag;
        this.text(text);
    }

    @Override
    public void initWidget(Text created) {
        if (textField == null) {
            return;
        }
        super.initWidget(textField);
        selectionProperty.manage(this);
        textField.addSelectionListener(selectionProperty.dispatcher());

        SWTWidgets.setupLayoutData(this,this.textField);
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
            initWidget(textField);
        }
        return textField;
    }

    @Override
    public Text getWidget() {
        return textField;
    }

    @Override
    public void onAction(String methodName) {
        this.selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        selectionProperty.closure(closure);
    }

    public static SWTText textView(int flags, String text)  {
        return new SWTText(flags,text);
    }

}
