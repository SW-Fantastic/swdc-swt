package org.swdc.swt.widgets.pane;

import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.util.Arrays;
import java.util.List;

public class SWTPane extends SWTWidget<Composite> implements SWTContainer {

    private int flag;

    private Composite composite;

    private SWTWidget widget;

    private SWTLayout layout;

    public SWTPane(int flag) {
        this.flag  = flag;
    }


    public SWTPane layout(SWTLayout layout) {
        this.layout = layout;
        if (this.composite != null) {
            this.composite.setLayout(layout.getLayout());
        }
        return this;
    }

    @Override
    public void ready(Stage stage) {
        if (composite == null) {
            return;
        }
        SWTWidget swtWidget = widget;
        while (swtWidget != null) {
            swtWidget.create(composite,this);
            swtWidget.initStage(stage);
            swtWidget.ready(stage);
            swtWidget = swtWidget.getNext();
        }

        SWTWidgets.setupLayoutData(this,composite);

    }

    @Override
    protected Composite getWidget(Composite parent) {
        if (composite == null && parent != null) {
            boolean isFormWidget = parent.getClass().getPackage().getName().contains("org.eclipse.ui.forms");
            if (isFormWidget) {
                composite = SWTWidgets.factory().createComposite(parent,flag);
            } else {
                composite = new Composite(parent,flag);
            }
            if (this.layout != null) {
                composite.setLayout(layout.getLayout());
            }
        }
        return composite;
    }

    @Override
    public void children(SWTWidget widget) {
        this.widget = widget;
    }

    @Override
    public List<SWTWidget> children() {
        return Arrays.asList(widget);
    }

    public static SWTPane pane(int flag) {
        return new SWTPane(flag);
    }

    @Override
    public SWTLayout getLayout() {
        return layout;
    }
}
