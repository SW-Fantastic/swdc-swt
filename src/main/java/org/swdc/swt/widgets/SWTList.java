package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.swdc.swt.beans.SizeProperty;

import java.util.ArrayList;

public class SWTList extends SWTWidget<List> {

    public  interface ListFactory<T> {
        String getValue(T obj);
    }

    private List list;

    private int flags;

    private java.util.List<Object> data;

    private ListFactory factory;

    private SizeProperty sizeProperty = new SizeProperty();

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

    public SWTList size(int width, int height) {
        sizeProperty.set(width,height);
        return this;
    }

    @Override
    public void ready(Stage stage) {
        if (data != null && factory != null) {
            for (Object item : data) {
                if (item == null) {
                    continue;
                }
                String val = factory.getValue(item);
                list.add(val);
            }
        }
    }

    @Override
    public List getWidget(Composite parent) {
        if (this.list == null && parent != null) {
            list = new List(parent,flags);
            if (this.getLayoutData() != null) {
                list.setLayoutData(getLayoutData().get());
            }
            sizeProperty.manage(list);
        }
        return list;
    }

    public static SWTList list(int flag) {
        return new SWTList(flag);
    }

}
