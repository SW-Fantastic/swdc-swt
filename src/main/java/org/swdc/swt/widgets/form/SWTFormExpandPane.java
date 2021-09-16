package org.swdc.swt.widgets.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ObservableSizeValue;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

public class SWTFormExpandPane extends SWTWidget<ExpandableComposite> implements SWTContainer {

    private int flag;
    private ObservableValue<String> text = new ObservableValue<>("");
    private ObservableValue<Boolean> expand = new ObservableValue<>(false);
    private ObservableValue<Point> size = new ObservableSizeValue(new Point(SWT.DEFAULT,SWT.DEFAULT));

    private SWTWidget widget;

    private ExpandableComposite composite;

    public SWTFormExpandPane(int flag) {
        this.flag = flag;

        this.text.addListener(((oldVal, newVal) ->  {
            if (this.composite != null && !this.text.isEmpty()) {
                this.composite.setText(text.get());
            }
        }));
        this.expand.addListener((oldVal, newVal) -> {
            if (this.composite != null && !this.expand.isEmpty()) {
                this.composite.setExpanded(this.expand.get());
            }
        });
        this.size.addListener((oldVal, newVal) -> {
            if (this.composite != null && !this.size.isEmpty()) {
                this.composite.setSize(this.size.get());
            }
        });
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
        this.expand.set(expand);
        return this;
    }

    public SWTFormExpandPane size(int width, int height) {
        this.size.set(new Point(width,height));
        return this;
    }

    @Override
    public ExpandableComposite getWidget(Composite parent) {
        if (parent != null && this.composite == null) {
            FormToolkit toolkit = SWTWidgets.factory();
            composite = toolkit.createExpandableComposite(parent,this.flag);
            toolkit.paintBordersFor(parent);
            if (!text.isEmpty()) {
                composite.setText(text.get());
            }
            if (!this.size.isEmpty()) {
                composite.setSize(this.size.get());
            }
            if (!this.expand.isEmpty()) {
                composite.setExpanded(this.expand.get());
            }
            if (this.getLayoutData() != null) {
                composite.setLayoutData(getLayoutData().get());
            }
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
