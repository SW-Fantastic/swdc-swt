package org.swdc.swt.beans;

import org.eclipse.swt.events.MouseEvent;
import org.swdc.swt.widgets.SWTWidget;

import java.lang.reflect.Method;

/**
 * 用于SWTWidget的Property，应该在
 * ready的方法中使用。
 *
 * property for SWTWidget，should be
 * used in override “ready” method
 *
 * @param <T>
 */
public interface SWTProperty<T,E> {

    /**
     * 管理此Widget组件的相应属性。
     * @param widget
     */
    void manage(SWTWidget widget);

    /**
     * 脱管当前组件。
     */
    void unlink();

    default void call(SWTWidget widget,E event, Method finalMethod) {
        if (widget == null ) {
            return;
        }
        Object controller = widget.getController();
        if (controller == null) {
            return;
        }

        if (finalMethod == null) {
            return;
        }

        try {
            if (finalMethod.getParameterCount() > 0) {
                finalMethod.invoke(controller,event);
            } else {
                finalMethod.invoke(controller);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
