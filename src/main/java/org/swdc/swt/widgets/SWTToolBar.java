package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.swdc.swt.beans.ColorProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;

public class SWTToolBar extends SWTControlWidget<ToolBar> implements SWTContainer {

    private int flag;
    private ToolBar toolBar;

    private SWTToolItem widget;

    public SWTToolBar(int flag) {
        this.flag = flag;
    }

    @Override
    protected ToolBar getWidget(Composite parent) {
        if (toolBar == null && parent != null) {
            toolBar = new ToolBar(parent,flag);
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
