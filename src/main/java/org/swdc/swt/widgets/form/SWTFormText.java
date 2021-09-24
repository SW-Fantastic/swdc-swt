package org.swdc.swt.widgets.form;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;

public class SWTFormText extends SWTWidget<FormText> {

    private int flag;

    private SizeProperty sizeProperty = new SizeProperty();
    private TextProperty text = new TextProperty();
    private ObservableValue<Boolean> parseTags = new ObservableValue<>(true);
    private ObservableValue<Boolean> expandURLs = new ObservableValue<>(true);

    private FormText formText;

    public SWTFormText(int flag) {
        this.flag = flag;
    }

    public SWTFormText parseTag(Boolean tag) {
        this.parseTags.set(tag);
        return this;
    }


    public SWTFormText text(String text) {
        this.text.set(text);
        return this;
    }

    public SWTFormText size(int width, int height) {
        this.sizeProperty.set(width, height);
        return this;
    }

    @Override
    public FormText getWidget(Composite parent) {
        if (this.formText == null && parent != null) {
            FormToolkit toolkit = SWTWidgets.factory();
            this.formText = toolkit.createFormText(parent,false);
            this.text.manage(formText);
            this.sizeProperty.manage(formText);
        }
        return formText;
    }

}
