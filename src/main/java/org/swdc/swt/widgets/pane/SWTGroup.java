package org.swdc.swt.widgets.pane;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.util.Arrays;
import java.util.List;

public class SWTGroup extends SWTWidget<Group> implements SWTContainer {

    private int flags;
    private Group group;

    private SWTWidget widget;

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
        if (group == null) {
            return;
        }
        SWTWidget swtWidget = widget;
        while (swtWidget != null) {
            swtWidget.create(group,this);
            swtWidget.initStage(stage);
            swtWidget.ready(stage);
            swtWidget = swtWidget.getNext();
        }
        SWTWidgets.setupLayoutData(this,group);
    }

    public SWTGroup text(String text){
        this.textProperty.set(text);
        return this;
    }


    @Override
    protected Group getWidget(Composite parent) {
        if (parent != null && group == null) {
            group = new Group(parent,this.flags);

            if (this.layout != null){
                group.setLayout(layout.getLayout());
            } else {
                group.setLayout(new FillLayout());
            }
            textProperty.manage(group);
        }
        return group;
    }

    @Override
    public List<SWTWidget> children() {
        return Arrays.asList(this.widget);
    }

    @Override
    public void children(SWTWidget widget) {
        this.widget =widget;
    }
}
