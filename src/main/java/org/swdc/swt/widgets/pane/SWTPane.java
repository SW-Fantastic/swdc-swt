package org.swdc.swt.widgets.pane;

import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;
import org.swdc.swt.widgets.WidgetUtils;

public class SWTPane extends SWTWidget<Composite> implements SWTContainer {

    private int flag;

    private Composite composite;

    private SWTWidget widget;

    private SWTLayout layout;

    private int width;

    private int height;

    public SWTPane(int flag) {
        this.flag  = flag;
    }


    public SWTPane size(int width, int height) {
        this.width  = width;
        this.height = height;
        if (this.composite != null) {
            this.composite.setSize(width,height);
        }
        return this;
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
        if (this.composite != null) {
            SWTWidget swtWidget = widget;
            while (swtWidget != null) {
                swtWidget.getWidget(composite);
                swtWidget.setStage(stage);
                swtWidget.ready(stage);
                swtWidget = swtWidget.getNext();
            }

        }
    }

    @Override
    public Composite getWidget(Composite parent) {
        if (composite == null && parent != null) {
            composite = new Composite(parent,flag);
            composite.setSize(width,height);
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

    public static SWTPane pane(int flag) {
        return new SWTPane(flag);
    }

}
