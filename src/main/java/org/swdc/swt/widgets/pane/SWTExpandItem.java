package org.swdc.swt.widgets.pane;

import org.eclipse.swt.widgets.*;
import org.swdc.swt.beans.ExpandProperty;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.base.SWTExpandableWidget;

import java.util.Collections;
import java.util.List;

public class SWTExpandItem extends SWTExpandableWidget<ExpandItem> implements SWTContainer {

    private int flag;
    private ExpandItem item;

    private ExpandProperty expandProperty = new ExpandProperty();

    private SWTWidget widget;

    public SWTExpandItem(int flag) {
        this.flag = flag;
    }

    @Override
    public void initWidget(ExpandItem created) {
        if (widget != null){
            super.initWidget(created);
            widget.setParent(getParent());
            Widget widget = this.widget.getWidget(created.getParent());
            item.setControl((Control) widget);
            this.expandProperty.manage(item);
        }
    }

    @Override
    public ExpandItem getWidget(Composite parent) {
        if (!(parent instanceof ExpandBar ) ){
            throw new RuntimeException("parent必须是ExpandBar");
        }
        if (item == null && parent != null) {
            ExpandBar bar = (ExpandBar) parent;
            if (this.item == null && parent != null) {
                this.item = new ExpandItem(bar,this.flag);
            }
            initWidget(item);
        }
        return item;
    }

    @Override
    public void children(SWTWidget widget) {
        if (widget.getFirst() != widget.getLast()) {
            throw new RuntimeException("Expand Item内只能有一个组件。");
        }
        this.widget = widget;
    }


    @Override
    public List<SWTWidget> children() {
        return Collections.singletonList(this.widget);
    }
}
