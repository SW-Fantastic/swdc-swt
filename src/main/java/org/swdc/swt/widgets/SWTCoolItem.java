package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.swdc.swt.widgets.base.SWTLabelWidget;

import java.util.Collections;
import java.util.List;

public class SWTCoolItem extends SWTLabelWidget<CoolItem> implements SWTContainer {

    private CoolItem item;
    private int flag;

    private SWTWidget widget;

    public SWTCoolItem(int flag) {
        this.flag = flag;
    }


    @Override
    public void initWidget(CoolItem created) {
        if (this.widget != null && item != null) {
            Widget widget = this.widget.getWidget(item.getParent());
            Point size = this.widget.size();

            item.setSize(size.x,size.y);
            item.setControl((Control) widget);
            item.setPreferredSize(size);

            this.sizeProperty().valueSize().addListener((oldVal, newVal) -> {
                Point sizeChange = this.getSize();
                item.setSize(sizeChange.x,sizeChange.y);
                item.setPreferredSize(sizeChange.x,sizeChange.y);
            });
            super.initWidget(item);

        }
    }

    public SWTCoolItem control(SWTWidget widget) {
        if (widget.getFirst() != widget.getLast()) {
            throw new RuntimeException("ToolItem内部只能有一个组件。");
        }
        this.widget = widget;
        return this;
    }

    public SWTCoolItem control(Closure<SWTWidget> widget) {
        SWTWidget target =  widget.call();
        if (target.getFirst() != target.getLast()) {
            throw new RuntimeException("ToolItem内部只能有一个组件。");
        }
        this.widget = target;
        return this;
    }

    @Override
    public CoolItem getWidget(Composite parent) {
        if (!(parent instanceof CoolBar)) {
            throw new RuntimeException("ToolItem 必须放置在ToolBar里面");
        }
        CoolBar toolBar = (CoolBar) parent;
        if (item == null && parent != null) {
            item = new CoolItem(toolBar,flag);
            initWidget(item);
        }

        return item;
    }

    @Override
    public List<SWTWidget> children() {
        return Collections.singletonList(widget);
    }
}
