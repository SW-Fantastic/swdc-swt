package org.swdc.swt.widgets.form;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;

public class SWTFormText extends SWTWidget<FormText> {

    private int flag;

    private ObservableValue<String> text = new ObservableValue<>("");
    private ObservableValue<Boolean> parseTags = new ObservableValue<>(true);
    private ObservableValue<Boolean> expandURLs = new ObservableValue<>(true);

    private FormText formText;

    public SWTFormText(int flag) {
        this.flag = flag;
        this.text.addListener(this::onTextChange);
    }

    public SWTFormText parseTag(Boolean tag) {
        this.parseTags.set(tag);
        return this;
    }

    private void onTextChange(String oldVal,String newVal) {
        if (this.formText != null && !text.isEmpty() && !parseTags.isEmpty() && !expandURLs.isEmpty()) {
            this.formText.setText(text.get(),parseTags.get(),expandURLs.get());
        }
    }

    public SWTFormText text(String text) {
        this.text.set(text);
        return this;
    }

    @Override
    public FormText getWidget(Composite parent) {
        if (this.formText == null && parent != null) {
            FormToolkit toolkit = SWTWidgets.factory();
            this.formText = toolkit.createFormText(parent,false);
            if (!this.text.isEmpty()) {
                this.onTextChange(null,null);
            }
        }
        return formText;
    }

}
