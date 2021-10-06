package org.swdc.swt.widgets.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.swdc.swt.Modifiable;
import org.swdc.swt.beans.TextProperty;

import java.util.ArrayList;
import java.util.List;

public class SWTMenu implements Modifiable<SWTMenu> {

    private Menu menu;

    private int flag;

    private List<SWTMenuItem> itemList = new ArrayList<>();

    private TextProperty textProperty = new TextProperty();

    public SWTMenu(int flag) {
        this.flag = flag;
    }

    public Menu getMenu(Control parent) {
        if (menu == null && parent != null) {
            menu = new Menu(parent);
            this.createItems();
            textProperty.manage(menu);
        }
        return menu;
    }

    public Menu getMenu(Decorations parent) {
        if (menu == null && parent != null) {
            menu = new Menu(parent, flag);
            this.createItems();
            textProperty.manage(menu);
        }
        return menu;
    }

    public Menu getMenu(MenuItem parent) {
        if (menu == null && parent != null) {
            menu = new Menu(parent);
            this.createItems();
            textProperty.manage(menu);
        }
        return menu;
    }

    private void createItems() {
        if (this.menu == null) {
            return;
        }
        for (SWTMenuItem item : itemList) {
            item.getMenuItem(this);
        }
    }

    public SWTMenu item(SWTMenuItem item) {
        this.itemList.add(item);
        return this;
    }

    public String text() {
        return textProperty.get();
    }

    public void text(String text) {
        textProperty.set(text);
    }

    public Menu getMenu() {
        return menu;
    }
}
