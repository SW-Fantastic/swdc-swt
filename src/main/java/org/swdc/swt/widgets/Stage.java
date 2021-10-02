package org.swdc.swt.widgets;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.swdc.swt.Window;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.base.Initialize;

import java.util.*;

public class Stage extends SWTWidget<Shell> implements SWTContainer, Window {

    private Shell shell = new Shell();

    private Object controller;

    private SWTLayout layout;

    private SWTWidget widget;

    private boolean initialized;


    public Stage controller(Object controller) {
        this.controller = controller;
        return this;
    }

    public Object getController() {
        return controller;
    }

    public Stage text(String text) {
        shell.setText(text);
        return this;
    }

    public Stage size(int width, int height) {
        shell.setSize(width, height);
        return this;
    }

    public Stage layout(SWTLayout layout) {
        this.layout = layout;
        shell.setLayout(layout.getLayout());
        return this;
    }

    public SWTLayout getLayout() {
        return layout;
    }

    @Override
    public void children(SWTWidget widget) {
        SWTWidgets.loadViewsByAnnotation(this);
        this.widget = widget;
    }

    @Override
    public void ready(Stage stage) {

        SWTWidget swtWidget = widget;
        while (swtWidget != null) {
            swtWidget.create(shell,this);
            swtWidget.initStage(stage);
            swtWidget.ready(stage);
            swtWidget = swtWidget.getNext();
        }

        SWTWidgets.setupLayoutData(this,shell);
    }


    public void show() {
        if (!initialized) {
            this.ready(this);
            if (this.controller != null && this.controller instanceof Initialize) {
                Initialize initialize = (Initialize) controller;
                initialize.initialize();
            }
            initialized = true;
        }
        shell.open();
    }

    public Boolean isDisposed() {
        return shell.isDisposed();
    }

    public Shell getShell() {
        return shell;
    }

    @Override
    public List<SWTWidget> children() {
        return Collections.singletonList(widget);
    }

    @Override
    protected Shell getWidget(Composite parent) {
        return shell;
    }
}
