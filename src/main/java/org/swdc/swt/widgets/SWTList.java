package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;

import java.util.ArrayList;

public class SWTList extends SWTControlWidget<List> {

    public  interface ListFactory<T> {
        String getValue(T obj);
    }

    private List list;

    private int flags;

    private java.util.List<Object> data;

    private ListFactory factory;

    public SWTList(int flag) {
        this.flags = flag;
        data = new ArrayList<>();
    }

    public SWTList data(java.util.List<Object> data)  {
        this.data = data;
        if (this.list != null) {
            this.list.removeAll();
            if (this.factory != null) {
                for (Object item : data) {
                    if (item == null) {
                        continue;
                    }
                    String val = factory.getValue(item);
                    list.add(val);
                }
            }
        }
        return this;
    }

    public SWTList factory(ListFactory factory) {
        this.factory = factory;
        return this;
    }

    public <T> T getSelected() {
        int selected = this.list.getSelectionIndex();
        if (selected > 0) {
            return (T)data.get(selected);
        }
        return null;
    }

    @Override
    public void ready() {
        super.ready();
        if (list != null) {
            if (data != null && factory != null) {
                for (Object item : data) {
                    if (item == null) {
                        continue;
                    }
                    String val = factory.getValue(item);
                    list.add(val);
                }
            }
            SWTWidgets.setupLayoutData(this,list);
        }

    }


    @Override
    protected List getWidget(Composite parent) {
        if (this.list == null && parent != null) {
            list = new List(parent,flags);
        }
        return list;
    }

    public static SWTList list(int flag) {
        return new SWTList(flag);
    }

}
