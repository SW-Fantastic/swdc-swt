package org.swdc.swt.beans;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;

import java.lang.reflect.Method;

public class SelectionProperty implements SWTProperty<String> {
    
    private ObservableValue<String> methodName = new ObservableValue<>();

    private SWTWidget widget;
    private Method actionMethod;

    private SelectionAdapter dispatcher = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent selectionEvent) {
            SelectionProperty self = SelectionProperty.this;
            self.call(selectionEvent);
        }
    };

    @Override
    public void set(String s) {
        methodName.set(s);
    }

    @Override
    public String get() {
        return methodName.get();
    }

    private void onNameChange(String oldName, String newName) {
        if (methodName.isEmpty() || widget == null || widget.getStage() == null) {
            return;
        }
        Stage stage = widget.getStage();
        if (stage.getController() == null) {
            return;
        }

        Object controller = stage.getController();
        String name = methodName.get();
        if (controller != null && methodName != null) {
            Class controllerClazz = controller.getClass();
            if (actionMethod == null) {
                try {
                    actionMethod = controllerClazz.getMethod(name);
                } catch (Exception e) {
                    try {
                        actionMethod = controllerClazz.getMethod(name, SelectionEvent.class);
                    } catch (Exception ex) {
                        throw new RuntimeException("找不到可用的方法：" + name);
                    }
                }
            }
        }
    }

    public void call(SelectionEvent selectionEvent) {
        if (widget == null || widget.getStage() == null) {
            return;
        }
        Stage stage = widget.getStage();
        Object controller = stage.getController();
        if (controller == null) {
            return;
        }

        Method finalMethod = actionMethod;
        if (actionMethod == null) {
            return;
        }

        try {
            if (finalMethod.getParameterCount() > 0) {
                finalMethod.invoke(controller,selectionEvent);
            } else {
                finalMethod.invoke(controller);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void manage(SWTWidget widget) {
        this.widget = widget;
        Stage stage = widget.getStage();
        if (!methodName.isEmpty() || stage != null && stage.getController() != null) {
            this.onNameChange(null,null);
        }
        methodName.addListener(this::onNameChange);
    }

    public SelectionAdapter dispatcher() {
        return dispatcher;
    }

    @Override
    public void unlink() {
        this.widget = null;
        this.actionMethod = null;

    }
}
