package org.swdc.swt.widgets.pane;

import groovy.lang.Closure;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.util.Arrays;
import java.util.List;

public class SWTCBanner extends SWTWidget<CBanner> implements SWTContainer {

    private int flag;
    private CBanner banner;

    private SWTWidget left;
    private SWTWidget right;
    private SWTWidget bottom;

    public SWTCBanner(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready(Stage stage) {
        if (this.banner == null) {
            return;
        }
        if (this.right != null) {
            Control widget = (Control) right.create(this.banner,this);
            right.initStage(stage);
            right.ready(stage);
            banner.setRight(widget);
            banner.setRightWidth(widget.getSize().x);
        }
        if (this.left != null) {
            Widget widget = left.create(this.banner,this);
            left.initStage(stage);
            left.ready(stage);
            banner.setLeft((Control) widget);
        }
        if (this.bottom != null) {
            Widget widget = bottom.create(this.banner,this);
            bottom.initStage(stage);
            bottom.ready(stage);
            banner.setBottom((Control) widget);
        }

        SWTWidgets.setupLayoutData(this,banner);
    }


    public SWTCBanner left(SWTWidget left) {
        if (left.getFirst() != left.getLast()) {
            throw new RuntimeException("SWTViewForm的Left只能使用一个组件");
        }
        this.left = left;
        return this;
    }


    public SWTCBanner bottom(SWTWidget bottom) {
        if (bottom.getLast() != bottom.getFirst()) {
            throw new RuntimeException("SWTViewForm的Bottom只能使用一个组件");
        }
        this.bottom = bottom;
        return this;
    }

    public SWTCBanner right(SWTWidget right) {
        if (right.getFirst() != right.getLast()) {
            throw new RuntimeException("SWTViewForm的Right只能使用一个组件");
        }
        this.right = right;
        return this;
    }

    public SWTCBanner left(Closure<SWTWidget> left) {
        left.setDelegate(this);
        left.setResolveStrategy(Closure.DELEGATE_ONLY);
        SWTWidget widget = left.call();
        return this.left(widget);
    }


    public SWTCBanner right(Closure<SWTWidget> right) {
        right.setDelegate(this);
        right.setResolveStrategy(Closure.DELEGATE_ONLY);
        SWTWidget widget = right.call();
        return this.right(widget);
    }

    public SWTCBanner bottom(Closure<SWTWidget> bottom) {
        bottom.setDelegate(this);
        bottom.setResolveStrategy(Closure.DELEGATE_ONLY);
        SWTWidget widget = bottom.call();
        return this.bottom(widget);
    }


    @Override
    public CBanner getWidget(Composite parent) {
        if (banner == null && parent != null) {
            banner = new CBanner(parent,this.flag);
            if (this.getLayoutData() != null) {
                banner.setLayoutData(getLayoutData().get());
            }
        }
        return banner;
    }

    @Override
    public List<SWTWidget> children() {
        return Arrays.asList(this.left,this.right,this.bottom);
    }
}
