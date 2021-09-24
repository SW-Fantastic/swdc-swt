package org.swdc.swt.beans;


import org.eclipse.swt.widgets.Widget;

/**
 * Widget的属性，监控属性的变化
 * 自动调整widget。
 *
 * @param <T>
 */
public interface Property <T> {

    void set(T t);

    T get();

    /**
     * 管理此Widget组件的相应属性。
     * @param widget
     */
    void manage(Widget widget);

    /**
     * 脱管当前组件。
     */
    void unlink();

}
