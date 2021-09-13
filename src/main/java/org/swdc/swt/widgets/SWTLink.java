package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;

public class SWTLink extends SWTWidget<Link> {

    private int flags;

    private Link link;

    private String text;

    public SWTLink(int flag) {
        this.flags = flag;
    }

    public SWTLink text(String text) {
        this.text = text;
        if (this.link != null) {
            this.link.setText(text);
        }
        return this;
    }

    @Override
    public Link getWidget(Composite parent) {
        if (link == null && parent != null) {
            link = new Link(parent,this.flags);
            if (this.text != null) {
                link.setText(text);
            }
            if (this.getLayoutData() != null) {
                this.link.setLayoutData(getLayoutData().get());
            }
        }
        return link;
    }
}
