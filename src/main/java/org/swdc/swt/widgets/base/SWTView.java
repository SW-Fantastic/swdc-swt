package org.swdc.swt.widgets.base;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.swdc.swt.SWTViewLoader;
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

    private SWTLayout layout;

    public SWTWidget getView(SWTViewLoader loader) {
        this.loader = loader;
        return viewPage();
    }

    protected abstract SWTWidget viewPage();

    public void layout(SWTLayout layout) {
        this.layout = layout;
    }

    @Override
    public void ready() {
        super.ready();
        SWTWidgets.setupLayoutData(this,widget.getWidget());
    }

    @Override
    protected Composite getWidget(Composite parent) {
        widget = SWTPane.pane(SWT.NORMAL)
                .layout(SWTFillLayout.fillLayout(SWT.NORMAL));

        Composite pane = widget.create(parent,this);
        viewPage().create(pane,this);

       return pane;
    }

    @Override
    public List<SWTWidget> children() {
        return Collections.singletonList(widget);
    }
}
