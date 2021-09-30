package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.*;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;

public class SWTCoolItem extends SWTWidget<CoolItem> {

    private CoolItem item;
    private int flag;

    private TextProperty text = new TextProperty();

    private SWTWidget widget;

    public SWTCoolItem(int flag) {
        this.flag = flag;
    }


    public SWTCoolItem text(String text){
        this.text.set(text);
        return this;
    }

    @Override
    public void ready(Stage stage) {
        if (this.widget != null && item != null) {
            Widget widget = this.widget.getWidget(item.getParent());
            this.widget.initStage(stage);
            this.widget.ready(stage);
            item.setControl((Control) widget);
            item.setPreferredSize(this.widget.getSize());
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
    protected CoolItem getWidget(Composite parent) {
        if (!(parent instanceof CoolBar)) {
            throw new RuntimeException("ToolItem 必须放置在ToolBar里面");
        }
        CoolBar toolBar = (CoolBar) parent;
        if (item == null && parent != null) {
            item = new CoolItem(toolBar,flag);
            this.text.manage(item);
        }

        return item;
    }

}
