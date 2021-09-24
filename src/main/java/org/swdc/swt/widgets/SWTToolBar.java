package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SizeProperty;

public class SWTToolBar extends SWTWidget<ToolBar> implements SWTContainer {

    private int flag;
    private ToolBar toolBar;

    private SWTToolItem widget;

    private SizeProperty sizeProperty = new SizeProperty();

    private ObservableValue<String> background = new ObservableValue<>();

    public SWTToolBar(int flag) {
        this.flag = flag;
        background.addListener((oldVal, newVal) -> {
            if (toolBar != null && !background.isEmpty()) {
                SWTColor swtColor = SWTWidgets.color(background.get());
                if (swtColor != null && swtColor.getColor() != null) {
                    toolBar.setBackground(swtColor.getColor());
                }
            }
        });
    }

    public SWTToolBar backgroundColor(String color) {
        this.background.set(color);
        return this;
    }

    public SWTToolBar size(int width , int height) {
        this.sizeProperty.set(width,height);
        return this;
    }

    @Override
    public ToolBar getWidget(Composite parent) {
        if (toolBar == null && parent != null) {
            toolBar = new ToolBar(parent,flag);
            if (this.getLayoutData() != null) {
                toolBar.setLayoutData(getLayoutData().get());
            }
            if (!this.background.isEmpty()) {
                SWTColor swtColor = SWTWidgets.color(background.get());
                if (swtColor != null && swtColor.getColor() != null) {
                    toolBar.setBackground(swtColor.getColor());
                }
            }
            this.sizeProperty.manage(toolBar);
        }
        return toolBar;
    }

    @Override
    public void ready(Stage stage) {
        if (this.widget != null) {
            SWTToolItem item = this.widget;
            while (item != null) {
                item.getWidget(this.toolBar);
                item.setStage(stage);
                item.ready(stage);
                item = (SWTToolItem) item.getNext();
            }
        }
    }

    @Override
    public void children(SWTWidget widget) {
        if (!(widget instanceof SWTToolItem)) {
            throw new RuntimeException("ToolBar内应该使用ToolItem");
        }
        this.widget =(SWTToolItem) widget;
    }
}
