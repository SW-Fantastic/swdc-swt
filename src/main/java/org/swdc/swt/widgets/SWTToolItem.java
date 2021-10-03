package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.*;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.widgets.base.SWTLabelWidget;
import org.swdc.swt.widgets.base.Selectionable;

import java.util.Collections;
import java.util.List;

public class SWTToolItem extends SWTLabelWidget<ToolItem> implements Selectionable,SWTContainer {

    private ToolItem item;
    private int flag;

    private SWTWidget widget;

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTToolItem(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready(Stage stage) {
        if (this.widget != null && item != null) {
            Widget widget = this.widget.create(item.getParent(),this);
            this.widget.initStage(stage);
            this.widget.ready(stage);
            item.setControl((Control) widget);


            // 接管本组件的SelectionEvent
            selectionProperty.manage(this);
            // 添加本Section的Listener到button。
            item.addSelectionListener(selectionProperty.dispatcher());

        }
    }

    public SWTToolItem control(SWTWidget widget) {
        if (widget.getFirst() != widget.getLast()) {
            throw new RuntimeException("ToolItem内部只能有一个组件。");
        }
        this.widget = widget;
        return this;
    }

    public SWTToolItem control(Closure<SWTWidget> widget) {
        SWTWidget target =  widget.call();
        if (target.getFirst() != target.getLast()) {
            throw new RuntimeException("ToolItem内部只能有一个组件。");
        }
        this.widget = target;
        return this;
    }

    @Override
    protected ToolItem getWidget(Composite parent) {
        if (!(parent instanceof ToolBar)) {
            throw new RuntimeException("ToolItem 必须放置在ToolBar里面");
        }
        ToolBar toolBar = (ToolBar) parent;
        if (item == null) {
            item = new ToolItem(toolBar,flag);
        }

        return item;
    }

    @Override
    public void onAction(String methodName) {
        this.selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
       selectionProperty.closure(closure);
    }

    @Override
    public List<SWTWidget> children() {
        return Collections.singletonList(this.widget);
    }
}
