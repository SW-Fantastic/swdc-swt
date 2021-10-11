package org.swdc.swt.actions;

import groovy.lang.Closure;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.lang.reflect.Method;

public class MouseTrackProperty implements SWTProperty<String> {

    private SWTWidget widget;

    private ObservableValue<String> mouseEnterMethodName = new ObservableValue<>();
    private Method mouseEnterMethod;
    private Closure closureEnter;

    private ObservableValue<String> mouseExitedMethodName = new ObservableValue<>();
    private Method mouseExitedMethod;
    private Closure closureExit;

    private ObservableValue<String> mouseHoverMethodName = new ObservableValue<>();
    private Method mouseHoverMethod;
    private Closure closureHover;


    private MouseTrackAdapter dispatcher = new MouseTrackAdapter() {

        @Override
        public void mouseEnter(MouseEvent mouseEvent) {
            MouseTrackProperty self = MouseTrackProperty.this;
            if (self.mouseEnterMethod != null) {
                self.call(mouseEvent,self.mouseEnterMethod);
            } else if (self.closureEnter != null) {
                self.closureEnter.call(mouseEvent);
            }
        }

        @Override
        public void mouseExit(MouseEvent mouseEvent) {
            MouseTrackProperty self = MouseTrackProperty.this;
            if (self.mouseExitedMethod != null) {
                self.call(mouseEvent,self.mouseExitedMethod);
            } else if (self.closureExit != null) {
                self.closureExit.call(mouseEvent);
            }
        }

        @Override
        public void mouseHover(MouseEvent mouseEvent) {
            MouseTrackProperty self = MouseTrackProperty.this;
            if (self.mouseHoverMethod != null) {
                self.call(mouseEvent,self.mouseHoverMethod);
            } else if (self.closureHover != null) {
                self.closureHover.call(mouseEvent);
            }
        }
    };



    private void call(MouseEvent event, Method finalMethod) {
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

    public MouseTrackAdapter dispatcher() {
        return dispatcher;
    }

    public void setMouseEnterMethod(String mouseEnterMethod) {
        this.mouseEnterMethodName.set(mouseEnterMethod);
    }

    public String getMouseEnterMethod() {
        return this.mouseEnterMethodName.isEmpty() ? "" : mouseEnterMethodName.get();
    }

    public void setMouseExitedMethod(String method) {
        this.mouseExitedMethodName.set(method);
    }

    public String getMouseExitedMethod() {
        return this.mouseExitedMethodName.isEmpty() ? "" : mouseExitedMethodName.get();
    }

    public void setMouseHoverMethod(String method) {
        this.mouseHoverMethodName.set(method);
    }

    public String getMouseHoverMethod() {
        return this.mouseHoverMethodName.isEmpty() ? "" : mouseHoverMethodName.get();
    }

    public void onMouseExitChanged(String oldVal, String newVal){
        if (mouseExitedMethodName.isEmpty() || widget == null ) {
            return;
        }
        Stage stage = widget.getStage();
        if (stage.getController() == null) {
            return;
        }

        SWTWidgets.setupMethod(
                this,
                mouseExitedMethodName,
                widget,
                prop -> prop.mouseExitedMethod,
                method -> mouseExitedMethod = method,
                MouseEvent.class
        );
    }

    public void onMouseEnterChanged(String oldVal, String newVal) {
        if (mouseEnterMethodName.isEmpty() || widget == null) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }

        SWTWidgets.setupMethod(
                this,
                mouseEnterMethodName,
                widget,
                prop -> prop.mouseEnterMethod,
                method -> mouseEnterMethod = method,
                MouseEvent.class
        );

    }

    public void onMouseHoverChanged(String valOld,String valNew) {
        if (mouseHoverMethodName.isEmpty() || widget == null ) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }

        SWTWidgets.setupMethod(
                this,
                mouseHoverMethodName,
                widget,
                prop -> prop.mouseHoverMethod,
                method -> mouseHoverMethod = method,
                MouseEvent.class
        );

    }

    @Override
    public void manage(SWTWidget widget) {
        unlink();
        this.widget = widget;
        Stage stage = widget.getStage();
        if (!mouseHoverMethodName.isEmpty() && widget.getController() != null) {
            this.onMouseHoverChanged(null,null);
        }

        if (!mouseEnterMethodName.isEmpty() && widget.getController() != null) {
            this.onMouseEnterChanged(null,null);
        }

        if (!mouseExitedMethodName.isEmpty() && widget.getController() != null) {
            this.onMouseExitChanged(null,null);
        }

        mouseExitedMethodName.addListener(this::onMouseExitChanged);
        mouseEnterMethodName.addListener(this::onMouseEnterChanged);
        mouseHoverMethodName.addListener(this::onMouseHoverChanged);
    }

    public void closure(Closure enter, Closure exit, Closure hover) {
        this.closureEnter = enter;
        this.closureExit = exit;
        this.closureHover = hover;
    }

    @Override
    public void unlink() {
        mouseExitedMethodName.removeListener(this::onMouseExitChanged);
        mouseEnterMethodName.removeListener(this::onMouseEnterChanged);
        mouseHoverMethodName.removeListener(this::onMouseHoverChanged);
        this.widget = null;

        this.mouseHoverMethod = null;
        this.mouseExitedMethod = null;
        this.mouseEnterMethod = null;
    }
}
