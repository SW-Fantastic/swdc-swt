package org.swdc.swt.widgets.base;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.layouts.SWTFillLayout;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;
import org.swdc.swt.widgets.pane.SWTPane;

import java.util.Collections;
import java.util.List;

public abstract class SWTView extends SWTWidget<Composite> implements SWTContainer {

    private SWTPane widget;

    private SWTWidget root;

    private SWTLayout layout;

    public abstract SWTWidget viewPage();

    public final void layout(SWTLayout layout) {
        this.layout = layout;
    }

    @Override
    public void ready(Stage stage) {
        SWTWidget item = viewPage();
        this.root = item;
        while (item != null) {
            item.create(widget.getWidget(),this);
            item.initStage(stage);
            item.ready(stage);
            item = item.getNext();
        }
        SWTWidgets.setupLayoutData(this,widget.getWidget());
    }

    @Override
    protected Composite getWidget(Composite parent) {
        widget = new SWTPane(SWT.FLAT);
        if (this.layout != null) {
            widget.layout(this.layout);
        } else {
            widget.layout(SWTFillLayout.fillLayout(SWT.NORMAL));
        }
        return widget.create(parent,this);
    }

    @Override
    public List<SWTWidget> children() {
        return Collections.singletonList(root);
    }
}
