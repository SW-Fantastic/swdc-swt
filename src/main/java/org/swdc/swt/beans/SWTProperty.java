package org.swdc.swt.beans;

import org.swdc.swt.widgets.SWTWidget;

/**
 * 用于SWTWidget的Property，应该在
 * ready的方法中使用。
 *
 * property for SWTWidget，should be
 * used in override “ready” method
 *
 * @param <T>
 */
public interface SWTProperty<T> {

    /**
     * 管理此Widget组件的相应属性。
     * @param widget
     */
    void manage(SWTWidget widget);

    /**
     * 脱管当前组件。
     */
    void unlink();

}
