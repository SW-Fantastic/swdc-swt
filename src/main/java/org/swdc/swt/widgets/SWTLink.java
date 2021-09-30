package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;

public class SWTLink extends SWTWidget<Link> {

    private int flags;

    private Link link;

    private TextProperty text = new TextProperty();

    public SWTLink(int flag) {
        this.flags = flag;
    }

    public SWTLink text(String text) {
        this.text.set(text);
        return this;
    }

    public static SWTLink link(int flags) {
        return new SWTLink(flags);
    }

    @Override
    public void ready(Stage stage) {
        if (this.link != null) {
            SWTWidgets.setupLayoutData(this,this.link);
        }
    }

    @Override
    protected Link getWidget(Composite parent) {
        if (link == null && parent != null) {
            link = new Link(parent,this.flags);
            text.manage(link);
        }
        return link;
    }
}
