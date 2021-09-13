package org.swdc.swt.widgets.pane;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.*;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;

public class SWTTab extends SWTWidget<TabItem> implements SWTContainer {

    private TabItem item;

    private int flag;

    private String text;

    private SWTWidget widget;

    private int width;
    private int height;


    public SWTTab(int flag) {
        this.flag = flag;
    }

    public SWTTab size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }


    public SWTTab text(String  text) {
        this.text = text;
        if (this.item != null){
            this.item.setText(this.text);
        }
        return this;
    }

    @Override
    public TabItem getWidget(Composite parent) {
        if (parent == null) {
            return null;
        }
        if (!(parent instanceof TabFolder)) {
            throw new RuntimeException("Tab必须位于TabPane内部");
        }

        TabFolder tabFolder = (TabFolder) parent;

        if (item == null) {
            item = new TabItem(tabFolder,this.flag);
            if (this.text != null) {
                item.setText(this.text);
            }
        }
        return item;
    }

    @Override
    public void ready(Stage stage) {
        if (widget == null) {
            return;
        }
        Widget view = widget.getWidget(this.item.getParent());
        Control target = (Control) view;
        widget.setStage(stage);
        widget.ready(stage);
        if (target instanceof ScrolledComposite) {
            target = target.getParent();
        }
        item.setControl(target);

        target.setSize(width,height);
    }

    @Override
    public void children(SWTWidget widget) {
        if (widget.getFirst() != widget.getLast()) {
            throw new RuntimeException("Tab的内容仅允许一个组件。");
        }
        this.widget = widget;
    }

    public static SWTTab tab(int flag, String name) {
        return new SWTTab(flag).text(name);
    }

}
