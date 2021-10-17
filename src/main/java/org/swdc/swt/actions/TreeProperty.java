package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.TreeAdapter;
import org.eclipse.swt.events.TreeEvent;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;

import java.lang.reflect.Method;

public class TreeProperty implements SWTProperty<String,TreeEvent> {


    private ObservableValue<String> treeCollapsedName = new ObservableValue<>();
    private Method collapseMethod = null;
    private Closure closureCollapse = null;

    private ObservableValue<String> treeExpandName = new ObservableValue<>();
    private Method expandMethod = null;
    private Closure closureExpand = null;

    private SWTWidget widget;


    private TreeAdapter dispatcher = new TreeAdapter() {
        @Override
        public void treeCollapsed(TreeEvent treeEvent) {
            if (collapseMethod != null) {
                call(widget,treeEvent,collapseMethod);
            } else if (closureCollapse != null) {
                closureCollapse.call(treeEvent);
            }
        }

        @Override
        public void treeExpanded(TreeEvent treeEvent){
            if (expandMethod != null) {
                call(widget,treeEvent,expandMethod);
            } else if (closureExpand != null){
                closureExpand.call(treeEvent);
            }
        }
    };

    private void onCollapseChange(String oldVal, String newVal) {
        if (this.widget == null || this.widget.getController() == null || treeCollapsedName.isEmpty()) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                treeCollapsedName,
                widget,
                prop -> prop.collapseMethod,
                method -> collapseMethod = method,
                TreeEvent.class
        );
    }

    private void onExpandChanged(String oldVal, String newVal) {
        if (this.widget == null || this.widget.getController() == null || treeCollapsedName.isEmpty()) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                treeExpandName,
                widget,
                prop -> prop.expandMethod,
                method -> expandMethod = method,
                TreeEvent.class
        );
    }

    public TreeAdapter dispatcher() {
        return dispatcher;
    }


    public String getCollapseMethod() {
        return treeCollapsedName.isEmpty() ? "" : treeCollapsedName.get();
    }

    public void setCollapseMethod(String name) {
        treeCollapsedName.set(name);
    }

    public String getExpandMethod() {
        return treeExpandName.isEmpty() ? "" : treeExpandName.get();
    }

    public void setExpandMethod(String method) {
        treeExpandName.set(method);
    }

    public void closure(Closure collapse, Closure expand) {
        this.closureCollapse = collapse;
        this.closureExpand = expand;
    }

    @Override
    public void manage(SWTWidget widget) {
        unlink();
        this.widget = widget;

        if (!treeCollapsedName.isEmpty() && widget.getController() != null) {
            onCollapseChange(null,null);
            treeCollapsedName.addListener(this::onCollapseChange);
        }

        if (!treeExpandName.isEmpty() && widget.getController() != null) {
            onExpandChanged(null,null);
            treeExpandName.addListener(this::onExpandChanged);
        }

    }

    @Override
    public void unlink() {
        treeExpandName.removeListener(this::onExpandChanged);
        treeCollapsedName.removeListener(this::onCollapseChange);
        expandMethod = null;
        collapseMethod = null;
    }
}
