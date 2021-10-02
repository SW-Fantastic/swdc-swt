package org.swdc.swt.beans;

import groovy.lang.Closure;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;

import java.lang.reflect.Method;

public class ControlProperty implements SWTProperty<String> {

    private ObservableValue<String> moveMethodName = new ObservableValue<>();
    private ObservableValue<String> resizedMethodName = new ObservableValue<>();

    private SWTWidget widget;
    private Method moveMethod;
    private Method resizeMethod;

    private Closure moveClosure;
    private Closure resizeClosure;

    private ControlAdapter dispatcher = new ControlAdapter() {
        @Override
        public void controlMoved(ControlEvent controlEvent) {
           ControlProperty self = ControlProperty.this;
           if (moveMethod != null) {
               self.call(controlEvent,moveMethod);
           } else if (moveClosure != null) {
               self.moveClosure.call(controlEvent);
           }
        }

        @Override
        public void controlResized(ControlEvent controlEvent) {
            ControlProperty self = ControlProperty.this;
            if (resizeMethod != null) {
                self.call(controlEvent,resizeMethod);
            } else if (resizeClosure != null) {
                resizeClosure.call(controlEvent);
            }
        }
    };

    private void call(ControlEvent event, Method finalMethod) {
        if (widget == null || widget.getStage() == null) {
            return;
        }
        Stage stage = widget.getStage();
        Object controller = stage.getController();
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

    @Override
    public void set(String s) {
        throw new RuntimeException("方法无效");
    }

    @Override
    public String get() {
        throw new RuntimeException("方法无效。");
    }

    public void setMoveMethod(String name) {
        this.moveMethodName.set(name);
    }

    public String getMoveMethod() {
        return moveMethodName.isEmpty() ? "" : moveMethodName.get();
    }

    public void setResizedMethod(String name) {
        this.resizedMethodName.set(name);
    }

    public String getResizeMethod() {
        return resizedMethodName.isEmpty() ? "" : resizedMethodName.get();
    }

    private void onResizeMethodChange(String oldName, String newName) {
        if (resizedMethodName.isEmpty() || widget == null || widget.getStage() == null) {
            return;
        }
        Stage stage = widget.getStage();
        if (stage.getController() == null) {
            return;
        }

        Object controller = stage.getController();
        String name = resizedMethodName.get();
        if (controller != null && resizedMethodName != null) {
            Class controllerClazz = controller.getClass();
            if (resizeMethod == null) {
                try {
                    resizeMethod = controllerClazz.getMethod(name);
                } catch (Exception e) {
                    try {
                        resizeMethod = controllerClazz.getMethod(name, SelectionEvent.class);
                    } catch (Exception ex) {
                        throw new RuntimeException("找不到可用的方法：" + name);
                    }
                }
            }
        }
    }

    private void onMoveMethodChange(String oldName, String newName) {
        if (moveMethodName.isEmpty() || widget == null || widget.getStage() == null) {
            return;
        }
        Stage stage = widget.getStage();
        if (stage.getController() == null) {
            return;
        }

        Object controller = stage.getController();
        String name = moveMethodName.get();
        if (controller != null && moveMethodName != null) {
            Class controllerClazz = controller.getClass();
            if (moveMethod == null) {
                try {
                    moveMethod = controllerClazz.getMethod(name);
                } catch (Exception e) {
                    try {
                        moveMethod = controllerClazz.getMethod(name, SelectionEvent.class);
                    } catch (Exception ex) {
                        throw new RuntimeException("找不到可用的方法：" + name);
                    }
                }
            }
        }
    }

    @Override
    public void manage(SWTWidget widget) {
        this.widget = widget;
        Stage stage = widget.getStage();
        if (!resizedMethodName.isEmpty() && stage != null && stage.getController() != null) {
            this.onResizeMethodChange(null,null);
        }

        if (!moveMethodName.isEmpty() && stage != null && stage.getController() != null) {
            this.onMoveMethodChange(null,null);
        }

        resizedMethodName.addListener(this::onResizeMethodChange);
        moveMethodName.addListener(this::onMoveMethodChange);
    }

    @Override
    public void unlink() {
        this.moveMethodName.removeListener(this::onMoveMethodChange);
        this.resizedMethodName.removeListener(this::onResizeMethodChange);
        this.moveMethod = null;
        this.resizeMethod = null;
    }

    public ControlAdapter dispatcher(){
        return dispatcher;
    }

    public void closure(Closure resize, Closure move) {
        this.moveClosure = move;
        this.resizeClosure= resize;
    }

}
