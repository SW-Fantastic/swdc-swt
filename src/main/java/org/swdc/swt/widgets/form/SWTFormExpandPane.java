package org.swdc.swt.widgets.form;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.base.SWTExpansationControlWidget;

public class SWTFormExpandPane extends SWTExpansationControlWidget<ExpandableComposite> implements SWTContainer {

    private int flag;

    private SWTWidget widget;

    private ExpandableComposite composite;

    public SWTFormExpandPane(int flag) {
        this.flag = flag;
    }

    @Override
    public void initWidget(ExpandableComposite created) {
        if (this.composite != null && this.widget != null) {
            super.initWidget(composite);
            widget.setParent(this);
            Control tartget = (Control) widget.getWidget(composite);
            composite.setClient(tartget);
            SWTWidgets.setupLayoutData(this,composite);
        }
    }


    @Override
    public ExpandableComposite getWidget(Composite parent) {
        if (parent != null && this.composite == null) {
            FormToolkit toolkit = SWTWidgets.factory();
            composite = toolkit.createExpandableComposite(parent,this.flag);
            toolkit.paintBordersFor(parent);
            composite.addExpansionListener(new ExpansionAdapter(){
                @Override
                public void expansionStateChanged(ExpansionEvent e) {
                    expand(composite.isExpanded());
                }
            });
            initWidget(composite);
        }
        return composite;
    }

    @Override
    public void children(SWTWidget widget) {
        if (widget.getFirst() != widget.getLast()) {
            throw new RuntimeException("只能放一个");
        }
        this.widget = widget;
    }

    public static SWTFormExpandPane expandPane(int flag) {
        return new SWTFormExpandPane(flag);
    }

}
