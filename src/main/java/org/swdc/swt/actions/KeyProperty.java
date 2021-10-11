package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.lang.reflect.Method;

public class KeyProperty implements SWTProperty<String> {

    private SWTWidget widget;

    private ObservableValue<String> keyPressedMethodName = new ObservableValue<>();
    private Method keyPressMethod;
    private Closure keyPressClosure;

    private ObservableValue<String> keyReleaseMethodName = new ObservableValue<>();
    private Method keyReleaseMethod;
    private Closure keyReleaseClosure;

    private KeyAdapter dispatcher = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            KeyProperty self = KeyProperty.this;
            if (self.keyPressMethod != null) {
                self.call(keyEvent,keyPressMethod);
            } else if (keyPressClosure != null) {
                keyPressClosure.call(keyEvent);
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            KeyProperty self = KeyProperty.this;
            if (self.keyReleaseMethod != null) {
                self.call(keyEvent,keyReleaseMethod);
            } else if (keyReleaseClosure != null) {
                keyReleaseClosure.call(keyEvent);
            }
        }
    };


    private void call(KeyEvent event, Method finalMethod) {
        if (widget == null) {
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

    private void onKeyPressMethodChange(String valOld, String valNew) {
        if (keyPressedMethodName.isEmpty() || widget == null) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                keyPressedMethodName,
                widget,
                prop -> prop.keyPressMethod,
                method -> keyPressMethod = method,
                KeyEvent.class
        );
    }

    private void onKeyReleaseMethodChange(String valOld, String valNew) {
        if (keyReleaseMethodName.isEmpty() || widget == null ) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                keyReleaseMethodName,
                widget,
                prop -> prop.keyReleaseMethod,
                method -> keyReleaseMethod = method,
                KeyEvent.class
        );
    }

    @Override
    public void manage(SWTWidget widget) {
        unlink();
        this.widget = widget;
        Stage stage = widget.getStage();
        if (!keyPressedMethodName.isEmpty() && widget.getController() != null) {
            this.onKeyPressMethodChange(null,null);
        }
        if (!keyReleaseMethodName.isEmpty() && widget.getController() != null) {
            this.onKeyReleaseMethodChange(null,null);
        }
        keyPressedMethodName.addListener(this::onKeyPressMethodChange);
        keyReleaseMethodName.addListener(this::onKeyReleaseMethodChange);
    }

    public void closure(Closure pressed, Closure released) {
        this.keyPressClosure = pressed;
        this.keyReleaseClosure = released;
    }

    public KeyAdapter dispatcher() {
        return dispatcher;
    }

    public String getKeyPressMethod() {
        return keyPressedMethodName.isEmpty() ? "" : keyPressedMethodName.get();
    }

    public void setKeyPressMethod(String method) {
        keyReleaseMethodName.set(method);
    }

    public String getKeyReleaseMethod() {
        return keyReleaseMethodName.isEmpty() ? "" : keyReleaseMethodName.get();
    }

    public void setKeyReleaseMethod(String name) {
        keyReleaseMethodName.set(name);
    }

    @Override
    public void unlink() {
        this.keyPressedMethodName.removeListener(this::onKeyPressMethodChange);
        this.keyReleaseMethodName.removeListener(this::onKeyReleaseMethodChange);

        this.keyPressMethod = null;
        this.keyReleaseMethod = null;

    }
}
