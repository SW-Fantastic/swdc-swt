package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.beans.ObservableArrayList;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

import java.util.ArrayList;

public class SWTList extends SWTControlWidget<List> implements Selectionable {

    public  interface ListFactory<T> {
        String getValue(T obj);
    }

    private SelectionProperty selectionProperty = new SelectionProperty();

    private List list;

    private int flags;

    private ObservableArrayList<Object> data = new ObservableArrayList<>();

    private ListFactory factory;

    public SWTList(int flag) {
        this.flags = flag;
    }

    public SWTList data(java.util.List<Object> data)  {
        this.data.clear();
        this.data.addAll(data);
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
            selectionProperty.manage(this);
            list.addSelectionListener(selectionProperty.dispatcher());
            SWTWidgets.setupLayoutData(this,list);
        }

    }


    @Override
    public void onAction(String methodName) {
        selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        selectionProperty.closure(closure);
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
