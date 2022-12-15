package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.swdc.swt.widgets.base.SWTControlWidget;

public class SWTCoolBar extends SWTControlWidget<CoolBar> implements SWTContainer{

    private int flag;

    private CoolBar coolBar;

    private SWTCoolItem items;

    public SWTCoolBar(int flag) {
        this.flag = flag;
    }

    @Override
    public CoolBar getWidget(Composite parent) {
        if (coolBar == null && parent != null) {
            this.coolBar = new CoolBar(parent,this.flag);
            initWidget(coolBar);
        }
        return coolBar;
    }

    @Override
    public CoolBar getWidget() {
        return coolBar;
    }

    @Override
    public void initWidget(CoolBar created) {
        if (coolBar == null) {
            return;
        }
        super.initWidget(coolBar);
        if (this.items != null) {
            SWTCoolItem item = this.items;
            while (item != null) {
                item.getWidget(coolBar);
                item = (SWTCoolItem) item.getNext();
            }
        }
        SWTWidgets.setupLayoutData(this,coolBar);
    }

    @Override
    public void children(SWTWidget widget) {
        if (!(widget instanceof SWTCoolItem)) {
            throw new RuntimeException("ToolBar内应该使用ToolItem");
        }
        this.items =(SWTCoolItem) widget;
    }


}
