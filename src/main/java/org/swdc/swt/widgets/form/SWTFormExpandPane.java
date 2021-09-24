package org.swdc.swt.widgets.form;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ExpandProperty;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

public class SWTFormExpandPane extends SWTWidget<ExpandableComposite> implements SWTContainer {

    private int flag;
    private TextProperty text = new TextProperty();

    private ExpandProperty expandProperty = new ExpandProperty();
    private SizeProperty sizeProperty = new SizeProperty();

    private SWTWidget widget;

    private ExpandableComposite composite;

    public SWTFormExpandPane(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready(Stage stage) {
        if (this.composite != null) {
            Control tartget = (Control) widget.getWidget(composite);
            widget.setStage(stage);
            widget.ready(stage);
            composite.setClient(tartget);
        }
    }

    public SWTFormExpandPane text(String text) {
        this.text.set(text);
        return this;
    }

    public SWTFormExpandPane expand(boolean expand) {
        this.expandProperty.set(expand);
        return this;
    }

    public SWTFormExpandPane size(int width, int height) {
        this.sizeProperty.set(new Point(width,height));
        return this;
    }

    @Override
    public ExpandableComposite getWidget(Composite parent) {
        if (parent != null && this.composite == null) {
            FormToolkit toolkit = SWTWidgets.factory();
            composite = toolkit.createExpandableComposite(parent,this.flag);
            toolkit.paintBordersFor(parent);
            if (this.getLayoutData() != null) {
                composite.setLayoutData(getLayoutData().get());
            }
            composite.addExpansionListener(new ExpansionAdapter(){
                @Override
                public void expansionStateChanged(ExpansionEvent e) {
                    expandProperty.setDirectly(composite.isExpanded());
                }
            });
            text.manage(composite);
            expandProperty.manage(composite);
            sizeProperty.manage(composite);
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
