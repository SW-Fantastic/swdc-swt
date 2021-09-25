package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Layout;
import org.swdc.swt.layouts.SWTLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 组件包含其他组件的时候应该实现此接口。
 */
public interface SWTContainer {


    /**
     * 组件创建的起点，groovy里面，此方法后面直接跟widget方法。
     * 直接set到字段即可。
     * @param widget
     */
    default void children(SWTWidget widget) {
        throw new UnsupportedOperationException();
    }

    /**
     * 窗口务必重写此方法。
     * 在这个方法内依次创建各个SWT的widget。
     *
     * @param closure
     * @return
     */
    default SWTWidget widget(Closure<SWTWidget> closure) {
        SWTWidget widget = closure.call();
        return widget;
    }

    /**
     * 返回children链。
     * 正常情况只有一个，那就是children，但是
     * 有些view包含left，right等多个区域，这样就存在
     * 两个或更多个children链。
     *
     * @return
     */
    default List<SWTWidget> children() {
        return new ArrayList<>();
    }

    default SWTLayout getLayout(){
        return null;
    }

}
