package org.swdc.swt.widgets.form;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

public class SWTForm extends SWTLabelControlWidget<Form> implements SWTContainer {

    private int flag;
    private Form form;

    private SWTWidget widget;

    private SWTLayout layout;

    public SWTForm(int flag) {
        this.flag = flag;
    }

    public SWTForm layout(SWTLayout layout) {
        this.layout = layout;
        return this;
    }


    @Override
    public void initWidget(Form created) {
        if (this.form != null) {
            super.initWidget(form);
            Composite parent = form.getBody();
            SWTWidget swtWidget = widget;
            while (swtWidget != null) {
                swtWidget.setParent(this);
                swtWidget.getWidget(parent);
                swtWidget = swtWidget.getNext();
            }
            SWTWidgets.setupLayoutData(this,form);
        }
    }

    @Override
    public Form getWidget(Composite parent) {
        if (form == null && parent != null) {
            FormToolkit toolkit = SWTWidgets.factory();
            form = toolkit.createForm(parent);
            toolkit.paintBordersFor(parent);

            if (this.layout != null) {
                form.getBody().setLayout(layout.getLayout());
            } else {
                form.getBody().setLayout(new FillLayout());
            }
            initWidget(form);
        }
        return form;
    }

    @Override
    public void children(SWTWidget widget) {
        this.widget = widget;
    }
}
