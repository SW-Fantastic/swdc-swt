package org.swdc.swt.widgets.pane;

import groovy.lang.Closure;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;

public class SWTViewForm extends SWTWidget<ViewForm> {

    private int flag;
    private ViewForm viewForm;

    private SWTWidget left;
    private SWTWidget right;
    private SWTWidget center;
    private SWTWidget bottom;

    private SizeProperty sizeProperty = new SizeProperty();


    private SWTViewForm(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready(Stage stage) {
        if (this.viewForm != null) {
            if (this.right != null) {
                Widget widget = right.getWidget(this.viewForm);
                right.setStage(stage);
                right.ready(stage);
                viewForm.setTopRight((Control) widget);
            }
            if (this.left != null) {
                Widget widget = left.getWidget(this.viewForm);
                left.setStage(stage);
                left.ready(stage);
                viewForm.setTopLeft((Control) widget);
            }
            if (this.center != null) {
                Widget widget = center.getWidget(this.viewForm);
                center.setStage(stage);
                center.ready(stage);
                viewForm.setTopCenter((Control) widget);
            }
            if (this.bottom != null) {
                Widget widget = bottom.getWidget(this.viewForm);
                bottom.setStage(stage);
                bottom.ready(stage);
                viewForm.setContent((Control) widget);
            }
        }
    }

    public SWTViewForm size(int width, int height) {
        this.sizeProperty.set(width,height);
        return this;
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
            if (this.getLayoutData() != null) {
                viewForm.setLayoutData(getLayoutData());
            }
            sizeProperty.manage(viewForm);
        }
        return viewForm;
    }

}
