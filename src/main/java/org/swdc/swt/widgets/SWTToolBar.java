package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.swdc.swt.widgets.base.SWTControlWidget;

public class SWTToolBar extends SWTControlWidget<ToolBar> implements SWTContainer {

    private int flag;
    private ToolBar toolBar;

    private SWTToolItem widget;

    public SWTToolBar(int flag) {
        this.flag = flag;
    }

    @Override
    public ToolBar getWidget(Composite parent) {
        if (toolBar == null && parent != null) {
            toolBar = new ToolBar(parent,flag);
            initWidget(toolBar);
        }
        return toolBar;
    }

    public ToolBar getWidget() {
        return toolBar;
    }

    @Override
    public void initWidget(ToolBar created) {
        if (toolBar == null) {
            return;
        }
        super.initWidget(toolBar);
        if (this.widget != null) {
            SWTToolItem item = this.widget;
            while (item != null) {
                item.getWidget(toolBar);
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
