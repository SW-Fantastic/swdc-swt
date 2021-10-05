package org.swdc.swt.widgets;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.swdc.swt.Window;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.base.Initialize;

import java.util.*;

public class Stage extends SWTWidget<Shell> implements SWTContainer, Window {

    private Shell shell = new Shell();

    private SWTLayout layout;

    private SWTWidget widget;

    private boolean initialized;

    public Object getController() {
        return loader.getController(this);
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
    public void ready() {
        super.ready();
        SWTWidget swtWidget = widget;
        while (swtWidget != null) {
            swtWidget.create(shell,this);
            swtWidget = swtWidget.getNext();
        }

        SWTWidgets.setupLayoutData(this,shell);
    }


    public void show() {
        if (!initialized) {
            Object controller = this.getController();
            if (controller instanceof Initialize) {
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

    @Override
    public Shell getWidget() {
        return shell;
    }
}
