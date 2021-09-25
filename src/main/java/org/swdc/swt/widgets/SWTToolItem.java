package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.*;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SizeProperty;

public class SWTToolItem extends SWTWidget<ToolItem> {

    private ToolItem item;
    private int flag;

    private SizeProperty sizeProperty = new SizeProperty();

    private SWTWidget widget;

    private ObservableValue<String> text = new ObservableValue<>();

    public SWTToolItem(int flag) {
        this.flag = flag;
        text.addListener((oldVal, newVal) -> {
            if (this.widget == null && !text.isEmpty() && item != null) {
                item.setText(text.get());
            }
        });
    }

    public SWTToolItem size(int width, int height){
        this.sizeProperty.set(width,height);
        return this;
    }

    public SWTToolItem text(String text){
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
        } else if (!text.isEmpty() && item != null){
            item.setText(text.get());
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
        if (item == null && parent != null) {
            item = new ToolItem(toolBar,flag);
            this.sizeProperty.manage(item);
        }

        return item;
    }
}
