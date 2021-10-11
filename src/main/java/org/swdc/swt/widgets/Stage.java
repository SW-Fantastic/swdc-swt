package org.swdc.swt.widgets;


import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.swdc.swt.Window;
import org.swdc.swt.actions.ShellProperty;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.base.Initialize;
import org.swdc.swt.widgets.base.SWTControlWidget;
import org.swdc.swt.widgets.menu.SWTMenu;

import java.util.*;

public class Stage extends SWTControlWidget<Shell> implements SWTContainer, Window {

    private Shell shell;

    private SWTLayout layout;

    private SWTWidget widget;

    private boolean initialized;

    private Image[] icons;

    ShellAdapter defaultActions = new ShellAdapter() {
        @Override
        public void shellClosed(ShellEvent shellEvent) {
            shellEvent.doit = false;
            shell.setVisible(false);
        }
    };

    private ShellProperty shellProperty = new ShellProperty();

    public Stage() {
        shell = new Shell();
        shellProperty.manage(this);
    }

    public Stage(int flag) {
        shell = new Shell(flag);
        shellProperty.manage(this);
    }

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

    public void onActive(String method) {
        shellProperty.setActiveMethod(method);
    }

    public void onDeActive(String method) {
        shellProperty.setDeActiveMethod(method);
    }

    public void onOpen(String method) {
        shellProperty.setOpenMethod(method);
    }

    public void onClose(String method) {
        shellProperty.setCloseMethod(method);
    }

    public void onIconified(String method) {
        shellProperty.setIconifiedMethod(method);
    }

    public void onDeIconified(String method) {
        shellProperty.setDeIconifiedMethod(method);
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

    public void icons(Image[] icons) {
        this.icons = icons;
        this.shell.setImages(icons);
    }

    @Override
    public void ready() {
        super.ready();

        SWTMenu menu = this.menu();
        if (menu != null) {
            Menu swtMenu = menu.getMenu(shell);
            shell.setMenuBar(swtMenu);
        }

        Image[] icons = this.icons;
        if (icons != null){
            this.shell.setImages(icons);
        }

        if (!initialized) {
            Object controller = this.getController();
            if (controller instanceof Initialize) {
                Initialize initialize = (Initialize) controller;
                initialize.initialize();
            }
        }
        initialized = true;

        shell.addShellListener(defaultActions);
    }

    public void show() {
        shell.setVisible(true);
        shell.moveAbove(null);
    }

    public void close() {
        shell.setVisible(false);
    }

    public void exit() {
        shell.removeShellListener(defaultActions);
        shell.close();
    }

    public void open() {
        shell.removeShellListener(defaultActions);
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
