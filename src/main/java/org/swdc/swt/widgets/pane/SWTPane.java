package org.swdc.swt.widgets.pane;

import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.base.SWTControlWidget;

import java.util.Arrays;
import java.util.List;

public class SWTPane extends SWTControlWidget<Composite> implements SWTContainer {

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
    public void initWidget(Composite created) {
        if (composite == null) {
            return;
        }
        super.initWidget(composite);
        SWTWidget swtWidget = widget;
        while (swtWidget != null) {
            swtWidget.setParent(this);
            swtWidget.getWidget(composite);
            swtWidget = swtWidget.getNext();
        }

        SWTWidgets.setupLayoutData(this,composite);

    }

    @Override
    public Composite getWidget(Composite parent) {
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
            initWidget(composite);
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

    @Override
    public <T> T getController() {
        return loader.getController(this);
    }
}
