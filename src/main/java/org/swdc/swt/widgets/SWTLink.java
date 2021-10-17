package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

public class SWTLink extends SWTLabelControlWidget<Link> implements Selectionable {

    private int flags;

    private Link link;

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTLink(int flag) {
        this.flags = flag;
    }

    public static SWTLink link(int flags) {
        return new SWTLink(flags);
    }

    @Override
    public void ready() {
        super.ready();
        if (this.link != null) {
            selectionProperty.manage(this);
            link.addSelectionListener(selectionProperty.dispatcher());
            SWTWidgets.setupLayoutData(this,this.link);
        }
    }

    @Override
    protected Link getWidget(Composite parent) {
        if (link == null && parent != null) {
            link = new Link(parent,this.flags);
        }
        return link;
    }

    @Override
    public void onAction(String methodName) {
        selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        selectionProperty.closure(closure);
    }
}
