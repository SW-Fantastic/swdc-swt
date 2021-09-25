package org.swdc.swt.beans;


import org.eclipse.swt.widgets.Widget;

/**
 * Widget的属性
 * 在getWidget中使用。
 * 监控ObservableValue的变化，并修改Widget。
 *
 * Property for Widget，
 * should be use on override “getWidget” method。
 * listening the ObservableValue and adjust Widget。
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
