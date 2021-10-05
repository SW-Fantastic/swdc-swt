package org.swdc.swt.widgets.form;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

public class SWTFormText extends SWTLabelControlWidget<FormText> {

    private int flag;

    private ObservableValue<Boolean> parseTags = new ObservableValue<>(true);

    private FormText formText;

    public SWTFormText(int flag) {
        this.flag = flag;
    }

    public SWTFormText parseTag(Boolean tag) {
        this.parseTags.set(tag);
        return this;
    }

    @Override
    public void ready() {
        super.ready();
        if (this.formText == null) {
            return;
        }
        SWTWidgets.setupLayoutData(this,this.formText);
    }

    @Override
    protected FormText getWidget(Composite parent) {
        if (this.formText == null && parent != null) {
            FormToolkit toolkit = SWTWidgets.factory();
            this.formText = toolkit.createFormText(parent,false);
        }
        return formText;
    }

}
