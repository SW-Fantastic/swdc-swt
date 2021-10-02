package org.swdc.swt.widgets.pane;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
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

public class SWTCTab extends SWTLabelWidget<CTabItem> implements SWTContainer {

    private CTabItem item;

    private int flag;

    private SWTWidget widget;

    public SWTCTab(int flag) {
        this.flag = flag;
    }

    @Override
    protected CTabItem getWidget(Composite parent) {
        if (parent == null) {
            return null;
        }
        if (!(parent instanceof CTabFolder)) {
            throw new RuntimeException("Tab必须位于TabPane内部");
        }

        CTabFolder tabFolder = (CTabFolder) parent;

        if (item == null) {
            item = new CTabItem(tabFolder,this.flag);
        }
        return item;
    }

    @Override
    public void ready(Stage stage) {
        if (widget == null || item == null) {
            return;
        }
        Widget view = widget.create(this.item.getParent(),this);
        Control target = (Control) view;
        widget.initStage(stage);
        widget.ready(stage);
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
}
