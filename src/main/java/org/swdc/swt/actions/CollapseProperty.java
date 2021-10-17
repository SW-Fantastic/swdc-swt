package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.events.MouseEvent;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;

import java.lang.reflect.Method;

public class CollapseProperty implements SWTProperty<String,ExpandEvent> {

    private SWTWidget widget;

    private ObservableValue<String> collapseName = new ObservableValue<>();
    private Method collapseMethod;
    private Closure collapseClosure;

    private ObservableValue<String> expandName = new ObservableValue<>();
    private Method expandMethod;
    private Closure expandClosure;

    private ExpandListener dispatcher = new ExpandListener() {
        @Override
        public void itemCollapsed(ExpandEvent expandEvent) {
            if (collapseMethod != null) {
                call(widget,expandEvent,collapseMethod);
            } else if (collapseClosure != null) {
                collapseClosure.call(expandEvent);
            }
        }

        @Override
        public void itemExpanded(ExpandEvent expandEvent) {
            if (expandMethod != null) {
                call(widget,expandEvent,expandMethod);
            } else if (expandClosure != null) {
                expandClosure.call(expandEvent);
            }
        }
    };

    private void onCollapseChanged(String oldMethod, String newMethod) {
        if (widget == null || collapseName.isEmpty() || widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                collapseName,
                widget,
                prop-> prop.collapseMethod,
                method -> collapseMethod = method,
                ExpandEvent.class
        );
    }

    private void onExpandChange(String oldMethod, String newMethod) {
        if (widget == null || collapseName.isEmpty() || widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                expandName,
                widget,
                prop-> prop.expandMethod,
                method -> expandMethod = method,
                ExpandEvent.class
        );
    }

    public String getExpandMethod() {
        return expandName.isEmpty() ? "" : expandName.get();
    }

    public void setExpandMethod(String method) {
        this.expandName.set(method);
    }

    public String getCollapseMethod() {
        return collapseName.isEmpty() ? "" : collapseName.get();
    }

    public void setCollapseMethod(String method) {
        collapseName.set(method);
    }

    public ExpandListener dispatcher() {
        return dispatcher;
    }

    public void closure(Closure collapse, Closure expand) {
        this.collapseClosure = collapse;
        this.expandClosure = expand;
    }

    @Override
    public void manage(SWTWidget widget) {
        unlink();
        this.widget = widget;
        if (!collapseName.isEmpty() && widget.getController() != null) {
            this.onCollapseChanged(null,null);
            collapseName.addListener(this::onCollapseChanged);
        }
        if (!expandName.isEmpty() && widget.getController() != null) {
            this.onExpandChange(null,null);
            expandName.addListener(this::onExpandChange);
        }
    }

    @Override
    public void unlink() {
        collapseName.removeListener(this::onCollapseChanged);
        collapseMethod = null;
        expandName.removeListener(this::onExpandChange);
        expandMethod = null;
    }
}
