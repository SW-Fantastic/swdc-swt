package org.swdc.swt.widgets.pane;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.*;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;
import org.swdc.swt.widgets.base.SWTLabelWidget;

import java.util.Arrays;
import java.util.List;

public class SWTTab extends SWTLabelWidget<TabItem> implements SWTContainer {

    private TabItem item;

    private int flag;

    private SWTWidget widget;

    public SWTTab(int flag) {
        this.flag = flag;
    }

    @Override
    protected TabItem getWidget(Composite parent) {
        if (parent == null) {
            return null;
        }
        if (!(parent instanceof TabFolder)) {
            throw new RuntimeException("Tab必须位于TabPane内部");
        }

        TabFolder tabFolder = (TabFolder) parent;

        if (item == null) {
            item = new TabItem(tabFolder,this.flag);
        }
        return item;
    }

    @Override
    public void ready() {
        super.ready();
        if (widget == null) {
            return;
        }
        Widget view = widget.create(this.item.getParent(),this);
        Control target = (Control) view;
        if (target instanceof ScrolledComposite) {
            target = target.getParent();
        }
        item.setControl(target);
    }

    @Override
    public void children(SWTWidget widget) {
        if (widget.getFirst() != widget.getLast()) {
            throw new RuntimeException("Tab的内容仅允许一个组件。");
        }
        this.widget = widget;
    }

    @Override
    public List<SWTWidget> children() {
        return Arrays.asList(this.widget);
    }

    public static SWTTab tab(int flag, String name) {
        return new SWTTab(flag).text(name);
    }

}
