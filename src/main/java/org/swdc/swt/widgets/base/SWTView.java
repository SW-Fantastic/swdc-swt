package org.swdc.swt.widgets.base;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scrollable;
import org.swdc.swt.SWTViewLoader;
import org.swdc.swt.layouts.SWTFillLayout;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.pane.SWTPane;

import java.util.Collections;
import java.util.List;

public abstract class SWTView<C extends Scrollable> extends SWTWidget<Composite> implements SWTContainer {

    private SWTPane widget;
    private SWTLayout layout;

    private Composite pane;

    public SWTWidget getView(SWTViewLoader loader) {
        this.loader = loader;
        return viewPage(this);
    }

    protected abstract SWTWidget viewPage(SWTWidget self);

    protected abstract SWTLayout layout();

    public SWTLayout getLayout() {
        if (layout == null) {
            layout = layout();
        }
        return layout;
    }

    @Override
    public void setParent(SWTContainer parent) {
        if (this.getParent() == null) {
            super.setParent(parent);
        }
    }

    @Override
    public void initWidget(Composite created) {
        super.initWidget(created);
        SWTWidgets.setupLayoutData(this,widget.getWidget());
    }

    @Override
    public Composite getWidget(Composite parent) {
        if (pane == null) {
            SWTLayout layout = layout();
            widget = SWTPane.pane(SWT.NORMAL).layout(layout == null ?
                    SWTFillLayout.fillLayout(SWT.NORMAL) : layout);

            widget.setParent(this);
            widget.children(this.viewPage(this));
            pane = widget.getWidget(parent);
            initWidget(pane);
        }

        return pane;
    }

    @Override
    public List<SWTWidget> children() {
        return Collections.singletonList(widget);
    }
}
