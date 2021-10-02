package org.swdc.swt.widgets.pane;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;
import org.swdc.swt.widgets.base.SWTControlWidget;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

import java.util.Collections;
import java.util.List;

public class SWTExpandBar extends SWTControlWidget<ExpandBar> implements SWTContainer {

    private int flag;
    private ExpandBar expandBar;

    private SWTWidget widget;

    public SWTExpandBar(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready(Stage stage) {
        if (expandBar == null) {
            return;
        }
        if (widget != null && widget instanceof SWTExpandItem) {
            SWTExpandItem item = (SWTExpandItem) widget;
            while (item != null){
                item.create(expandBar,this);
                item.initStage(stage);
                item.ready(stage);
                item = (SWTExpandItem) item.getNext();
            }
         }
        SWTWidgets.setupLayoutData(this,expandBar);
    }

    @Override
    protected ExpandBar getWidget(Composite parent) {
        if (expandBar == null && parent != null) {
            this.expandBar = new ExpandBar(parent,this.flag);
        }
        return expandBar;
    }

    @Override
    public void children(SWTWidget widget) {
        if (!(widget instanceof SWTExpandItem)) {
            throw new RuntimeException("请使用SWTExpandItem");
        }
        this.widget = widget;
    }

    @Override
    public List<SWTWidget> children() {
        return Collections.singletonList(widget);
    }
}
