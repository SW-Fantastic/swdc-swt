package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.swdc.swt.Modifiable;
import org.swdc.swt.layouts.LayoutData;

public abstract class SWTWidget<T extends Widget> implements Modifiable<SWTWidget<T>> {

    private SWTWidget next;

    private SWTWidget prev;

    private String id;

    private Stage stage;

    private LayoutData layoutData;

    public SWTWidget rightShift(SWTWidget item) {
        if (this.next == null) {
            this.next = item;
            item.prev = this;
        } else {
            this.next.rightShift(item);
        }
        return this;
    }

    public SWTWidget leftShift(SWTWidget item) {
        if (this.prev == null) {
            this.prev = item;
            item.next = this;
        } else {
            this.prev.leftShift(item);
        }
        return this;
    }

    public Stage getStage() {
        return stage;
    }



    /**
     * 用来传递stage窗口和在它里面的controller
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public SWTWidget getFirst(){
        if (this.prev == null) {
            return this;
        }
        return this.prev.getFirst();
    }

    public SWTWidget getNext() {
        return next;
    }

    public SWTWidget getPrev() {
        return prev;
    }

    public SWTWidget getLast() {
        if (this.next == null) {
            return this;
        } else {
            return this.next.getLast();
        }
    }

    public <R extends SWTWidget<T>> R layout(LayoutData layoutData) {
        this.layoutData = layoutData;
        return (R) this;
    }

    public LayoutData getLayoutData() {
        return layoutData;
    }

    /**
     * 创建SWT组件
     * @param parent
     * @return
     */
    public abstract T getWidget(Composite parent);

    /**
     * 初始化此组件（SWT组件此时应当已经创建娲完毕）
     * @param stage
     */
    public void ready(Stage stage) {

    }

    public SWTWidget<T> id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }
}
