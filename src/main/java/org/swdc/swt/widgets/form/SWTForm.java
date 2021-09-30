package org.swdc.swt.widgets.form;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

public class SWTForm extends SWTWidget<Form> implements SWTContainer {

    private int flag;
    private Form form;

    private SWTWidget widget;

    private SWTLayout layout;

    private TextProperty textProperty = new TextProperty();

    public SWTForm(int flag) {
        this.flag = flag;
    }

    public SWTForm layout(SWTLayout layout) {
        this.layout = layout;
        return this;
    }

    public SWTForm text(String text) {
        this.textProperty.set(text);
        return this;
    }

    @Override
    public void ready(Stage stage) {
        if (this.form != null) {
            Composite parent = form.getBody();
            SWTWidget swtWidget = widget;
            while (swtWidget != null) {
                swtWidget.create(parent,this);
                swtWidget.initStage(stage);
                swtWidget.ready(stage);
                swtWidget = swtWidget.getNext();
            }
            SWTWidgets.setupLayoutData(this,form);
        }
    }

    @Override
    protected Form getWidget(Composite parent) {
        if (form == null && parent != null) {
            FormToolkit toolkit = SWTWidgets.factory();
            form = toolkit.createForm(parent);
            toolkit.paintBordersFor(parent);

            if (this.layout != null) {
                form.getBody().setLayout(layout.getLayout());
            } else {
                form.getBody().setLayout(new FillLayout());
            }
            textProperty.manage(form);
        }
        return form;
    }

    @Override
    public void children(SWTWidget widget) {
        this.widget = widget;
    }
}
