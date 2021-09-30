package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.swdc.swt.beans.ColorProperty;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SizeProperty;

public class SWTToolBar extends SWTWidget<ToolBar> implements SWTContainer {

    private int flag;
    private ToolBar toolBar;

    private SWTToolItem widget;

    private ColorProperty colorProperty = new ColorProperty();

    public SWTToolBar(int flag) {
        this.flag = flag;

    }

    public SWTToolBar backgroundColor(String color) {
        this.colorProperty.setBackground(color);
        return this;
    }


    @Override
    protected ToolBar getWidget(Composite parent) {
        if (toolBar == null && parent != null) {
            toolBar = new ToolBar(parent,flag);
            this.colorProperty.manage(toolBar);
        }
        return toolBar;
    }

    @Override
    public void ready(Stage stage) {
        if (toolBar == null) {
            return;
        }
        if (this.widget != null) {
            SWTToolItem item = this.widget;
            while (item != null) {
                item.create(toolBar,this);
                item.initStage(stage);
                item.ready(stage);
                item = (SWTToolItem) item.getNext();
            }
        }
        SWTWidgets.setupLayoutData(this,toolBar);
    }

    @Override
    public void children(SWTWidget widget) {
        if (!(widget instanceof SWTToolItem)) {
            throw new RuntimeException("ToolBar内应该使用ToolItem");
        }
        this.widget =(SWTToolItem) widget;
    }
}
