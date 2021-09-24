package org.swdc.swt.widgets.pane;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;

import java.util.Arrays;

public class SWTGroup extends SWTWidget<Group> implements SWTContainer {

    private int flags;
    private Group group;

    private SWTWidget widget;

    private SizeProperty sizeProperty = new SizeProperty();
    private TextProperty textProperty = new TextProperty();



    private SWTLayout layout;

    public SWTGroup(int flag) {
        this.flags = flag;
    }

    public SWTGroup layout(SWTLayout layout) {
        this.layout = layout;
        return this;
    }

    @Override
    public void ready(Stage stage) {
        if (this.group != null) {
            SWTWidget swtWidget = widget;
            while (swtWidget != null) {
                swtWidget.getWidget(group);
                swtWidget.setStage(stage);
                swtWidget.ready(stage);
                swtWidget = swtWidget.getNext();
            }
        }
    }

    public SWTGroup text(String text){
        this.textProperty.set(text);
        return this;
    }

    public SWTGroup size(int width, int height) {
        this.sizeProperty.set(new Point(width,height));
        return this;
    }

    @Override
    public Group getWidget(Composite parent) {
        if (parent != null && group == null) {
            group = new Group(parent,this.flags);
            if (this.getLayoutData() != null) {
                this.group.setLayoutData(this.getLayoutData().get());
            }
            if (this.layout != null){
                group.setLayout(layout.getLayout());
            } else {
                group.setLayout(new FillLayout());
            }
            sizeProperty.manage(group);
            textProperty.manage(group);
        }
        return group;
    }

    @Override
    public void children(SWTWidget widget) {
        this.widget =widget;
    }
}
