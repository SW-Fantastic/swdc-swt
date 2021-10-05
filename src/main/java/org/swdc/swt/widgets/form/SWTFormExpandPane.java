package org.swdc.swt.widgets.form;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ExpandProperty;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

public class SWTFormExpandPane extends SWTLabelControlWidget<ExpandableComposite> implements SWTContainer {

    private int flag;
    private ExpandProperty expandProperty = new ExpandProperty();

    private SWTWidget widget;

    private ExpandableComposite composite;

    public SWTFormExpandPane(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready() {
        super.ready();
        if (this.composite != null && this.widget != null) {
            Control tartget = (Control) widget.create(composite,this);
            composite.setClient(tartget);
            SWTWidgets.setupLayoutData(this,composite);
        }
    }

    public SWTFormExpandPane expand(boolean expand) {
        this.expandProperty.set(expand);
        return this;
    }

    @Override
    protected ExpandableComposite getWidget(Composite parent) {
        if (parent != null && this.composite == null) {
            FormToolkit toolkit = SWTWidgets.factory();
            composite = toolkit.createExpandableComposite(parent,this.flag);
            toolkit.paintBordersFor(parent);

            composite.addExpansionListener(new ExpansionAdapter(){
                @Override
                public void expansionStateChanged(ExpansionEvent e) {
                    expandProperty.setDirectly(composite.isExpanded());
                }
            });
            expandProperty.manage(composite);
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
