package org.swdc.swt.widgets.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.swdc.swt.Modifiable;
import org.swdc.swt.beans.TextProperty;

public class SWTMenuItem implements Modifiable<SWTMenuItem> {

    private MenuItem item;

    private SWTMenu subMenu;

    private TextProperty textProperty = new TextProperty();

    private int flag;

    public SWTMenuItem(int flag) {
        this.flag = flag;
    }

    public SWTMenuItem subMenu(SWTMenu subMenu) {
        this.subMenu = subMenu;
        return this;
    }

    public MenuItem getMenuItem(SWTMenu menu) {
        if (menu == null) {
            return null;
        }
        Menu swtMenu = menu.getMenu();
        if (swtMenu == null) {
            return null;
        }
        this.item = new MenuItem(swtMenu,this.flag);
        textProperty.manage(item);
        if (this.subMenu != null) {
            Menu swtSubMenu = subMenu.getMenu(item);
            item.setMenu(swtSubMenu);
        }
        return this.item;
    }

    public String text() {
        return textProperty.get();
    }

    public void text(String text) {
        textProperty.set(text);
    }

}
