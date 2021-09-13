package org.swdc.swt.widgets;

import groovy.lang.Closure;

/**
 * 组件包含其他组件的时候应该实现此接口。
 */
public interface SWTContainer {


    /**
     * 组件创建的起点，groovy里面，此方法后面直接跟widget方法。
     * 直接set到字段即可。
     * @param widget
     */
    void children(SWTWidget widget);

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


}
