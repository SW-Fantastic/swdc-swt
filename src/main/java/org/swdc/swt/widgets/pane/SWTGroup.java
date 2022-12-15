package org.swdc.swt.widgets.pane;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.base.SWTControlWidget;

import java.util.Arrays;
import java.util.List;

public class SWTGroup extends SWTControlWidget<Group> implements SWTContainer {

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
    public void initWidget(Group created) {
        if (group == null) {
            return;
        }
        super.initWidget(group);
        SWTWidget swtWidget = widget;
        while (swtWidget != null) {
            swtWidget.setParent(this);
            swtWidget.getWidget(group);
            swtWidget = swtWidget.getNext();
        }
        SWTWidgets.setupLayoutData(this,group);
    }

    public SWTGroup text(String text){
        this.textProperty.set(text);
        return this;
    }


    @Override
    public Group getWidget(Composite parent) {
        if (parent != null && group == null) {
            group = new Group(parent,this.flags);

            if (this.layout != null){
                group.setLayout(layout.getLayout());
            } else {
                group.setLayout(new FillLayout());
            }
            textProperty.manage(group);
            initWidget(group);
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
