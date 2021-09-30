package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.swdc.swt.beans.ColorProperty;
import org.swdc.swt.beans.SizeProperty;

public class SWTCoolBar extends SWTWidget<CoolBar> implements SWTContainer{

    private int flag;

    private CoolBar coolBar;

    private SWTCoolItem items;

    private ColorProperty colorProperty = new ColorProperty();

    public SWTCoolBar(int flag) {
        this.flag = flag;
    }

    public SWTCoolBar backgroundColor(String color) {
        this.colorProperty.setBackground(color);
        return this;
    }

    public SWTCoolBar color(String color) {
        this.colorProperty.setForeground(color);
        return this;
    }

    @Override
    protected CoolBar getWidget(Composite parent) {
        if (coolBar == null && parent != null) {
            this.coolBar = new CoolBar(parent,this.flag);
            colorProperty.manage(coolBar);
        }
        return null;
    }

    @Override
    public void ready(Stage stage) {
        if (coolBar == null) {
            return;
        }
        if (this.items != null) {
            SWTCoolItem item = this.items;
            while (item != null) {
                item.getWidget(this.coolBar);
                item.initStage(stage);
                item.ready(stage);
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
