package org.swdc.swt.widgets.pane;

import groovy.lang.Closure;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;
import org.swdc.swt.widgets.base.SWTControlWidget;

import java.util.Arrays;
import java.util.List;

public class SWTViewForm extends SWTControlWidget<ViewForm> implements SWTContainer {

    private int flag;
    private ViewForm viewForm;

    private SWTWidget left;
    private SWTWidget right;
    private SWTWidget center;
    private SWTWidget bottom;

    private SWTViewForm(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready() {
        super.ready();
        if (viewForm == null) {
            return;
        }
        if (this.right != null) {
            Widget widget = right.create(this.viewForm,this);
            viewForm.setTopRight((Control) widget);
        }
        if (this.left != null) {
            Widget widget = left.create(this.viewForm,this);
            viewForm.setTopLeft((Control) widget);
        }
        if (this.center != null) {
            Widget widget = center.create(this.viewForm,this);
            viewForm.setTopCenter((Control) widget);
        }
        if (this.bottom != null) {
            Widget widget = bottom.create(this.viewForm,this);
            viewForm.setContent((Control) widget);
        }
        SWTWidgets.setupLayoutData(this,viewForm);
    }


    public SWTViewForm left(SWTWidget left) {
        if (left.getFirst() != left.getLast()) {
            throw new RuntimeException("SWTViewForm的Left只能使用一个组件");
        }
        this.left = left;
        return this;
    }

    public SWTViewForm center(SWTWidget center) {
        if (center.getFirst() != center.getLast()) {
            throw new RuntimeException("SWTViewForm的Center只能使用一个组件");
        }
        this.center = center;
        return this;
    }

    public SWTViewForm bottom(SWTWidget bottom) {
        if (bottom.getLast() != bottom.getFirst()) {
            throw new RuntimeException("SWTViewForm的Bottom只能使用一个组件");
        }
        this.bottom = bottom;
        return this;
    }

    public SWTViewForm right(SWTWidget right) {
        if (right.getFirst() != right.getLast()) {
            throw new RuntimeException("SWTViewForm的Right只能使用一个组件");
        }
        this.right = right;
        return this;
    }

    public SWTViewForm left(Closure<SWTWidget> left) {
        left.setDelegate(this);
        left.setResolveStrategy(Closure.DELEGATE_ONLY);
        SWTWidget widget = left.call();
        return this.left(widget);
    }

    public SWTViewForm center(Closure<SWTWidget> center) {
        center.setDelegate(this);
        center.setResolveStrategy(Closure.DELEGATE_ONLY);
        SWTWidget widget = center.call();
        return this.center(widget);
    }

    public SWTViewForm right(Closure<SWTWidget> right) {
        right.setDelegate(this);
        right.setResolveStrategy(Closure.DELEGATE_ONLY);
        SWTWidget widget = right.call();
        return this.right(widget);
    }

    public SWTViewForm bottom(Closure<SWTWidget> bottom) {
        bottom.setDelegate(this);
        bottom.setResolveStrategy(Closure.DELEGATE_ONLY);
        SWTWidget widget = bottom.call();
        return this.bottom(widget);
    }

    @Override
    public ViewForm getWidget(Composite parent) {
        if (viewForm == null && parent != null) {
            viewForm = new ViewForm(parent,flag);
        }
        return viewForm;
    }

    @Override
    public List<SWTWidget> children() {
        return Arrays.asList(this.left,this.center,this.right,this.bottom);
    }
}
