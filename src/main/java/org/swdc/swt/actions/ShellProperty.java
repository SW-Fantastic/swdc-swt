package org.swdc.swt.actions;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.SWTProperty;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;

import java.lang.reflect.Method;

public class ShellProperty implements SWTProperty<String,ShellEvent> {

    private ObservableValue<String> activeName = new ObservableValue<>();
    private Method activeMethod;

    private ObservableValue<String> closeName = new ObservableValue<>();
    private Method closeMethod;

    private ObservableValue<String> openName = new ObservableValue<>();
    private Method openMethod;

    private ObservableValue<String> deActiveName = new ObservableValue<>();
    private Method deActiveMethod;

    private ObservableValue<String> iconifiedName = new ObservableValue<>();
    private Method iconifiedMethod;

    private ObservableValue<String> deIconifiedName = new ObservableValue<>();
    private Method deIconifiedMethod;

    private SWTWidget widget;

    private ShellAdapter dispatcher = new ShellAdapter() {

        @Override
        public void shellActivated(ShellEvent shellEvent) {
            if (activeMethod != null){
                call(widget,shellEvent, activeMethod);
            }
        }

        @Override
        public void shellClosed(ShellEvent shellEvent) {
            if (closeMethod != null) {
                call(widget,shellEvent,closeMethod);
            }
        }

        @Override
        public void shellDeactivated(ShellEvent shellEvent) {
            if (deActiveMethod != null) {
                call(widget,shellEvent,deActiveMethod);
            }
        }

        @Override
        public void shellDeiconified(ShellEvent shellEvent) {
            if (deIconifiedMethod != null) {
                call(widget,shellEvent,deIconifiedMethod);
            }
        }

        @Override
        public void shellIconified(ShellEvent shellEvent) {
            if (iconifiedMethod != null) {
                call(widget,shellEvent,iconifiedMethod);
            }
        }

    };

    public String getActiveMethod() {
        return activeName.isEmpty() ? "" : activeName.get();
    }

    public void setActiveMethod(String name) {
        this.activeName.set(name);
    }

    public String getDeActiveMethod() {
        return deActiveName.isEmpty() ? "" : deActiveName.get();
    }

    public void setDeActiveMethod(String deActiveMethod) {
        this.deActiveName.set(deActiveMethod);
    }

    public String getOpenMethod() {
        return openName.isEmpty() ? "" : openName.get();
    }

    public void setOpenMethod(String openMethod) {
        openName.set(openMethod);
    }

    public void setCloseMethod(String closeMethod) {
        closeName.set(closeMethod);
    }

    public String getCloseMethod() {
        return closeName.isEmpty() ? "" : closeName.get();
    }

    public String getIconifiedMethod() {
        return iconifiedName.isEmpty() ? "" : iconifiedName.get();
    }

    public void setIconifiedMethod(String iconifiedMethod) {
        iconifiedName.set(iconifiedMethod);
    }

    public String getDeIconifiedMethod() {
        return deIconifiedName.isEmpty() ? "" : deIconifiedName.get();
    }

    public void setDeIconifiedMethod(String method) {
        deIconifiedName.set(method);
    }

    private void onActiveMethodChange(String oldMethod, String newMethod) {
        if (widget == null || activeName.isEmpty()) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                activeName,
                widget,
                prop -> prop.activeMethod,
                (Method method) -> this.activeMethod = method,
                MouseEvent.class
        );
    }

    private void onDeActiveMethodChange(String oldMethod, String newMethod) {
        if (widget == null || deActiveName.isEmpty()) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                deActiveName,
                widget,
                prop -> prop.deActiveMethod,
                (Method method) -> this.deActiveMethod = method,
                MouseEvent.class
        );
    }

    private void onOpenMethodChange(String oldMethod, String newMethod) {
        if (widget == null || openName.isEmpty()) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                openName,
                widget,
                prop -> prop.openMethod,
                (Method method) -> this.openMethod = method,
                MouseEvent.class
        );
    }

    private void onCloseMethodChange(String oldMethod, String newMethod) {
        if (widget == null || closeName.isEmpty()) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                closeName,
                widget,
                prop -> prop.closeMethod,
                (Method method) -> this.closeMethod = method,
                MouseEvent.class
        );
    }

    private void onIconifyMethodChange(String oldMethod, String newMethod) {
        if (widget == null || iconifiedName.isEmpty()) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                iconifiedName,
                widget,
                prop -> prop.iconifiedMethod,
                (Method method) -> this.iconifiedMethod = method,
                MouseEvent.class
        );
    }

    private void onDeIconifyMethodChange(String oldMethod, String newMethod) {
        if (widget == null || deIconifiedName.isEmpty()) {
            return;
        }
        if (widget.getController() == null) {
            return;
        }
        SWTWidgets.setupMethod(
                this,
                deIconifiedName,
                widget,
                prop -> prop.deIconifiedMethod,
                (Method method) -> this.deIconifiedMethod = method,
                MouseEvent.class
        );
    }

    @Override
    public void manage(SWTWidget widget) {
        this.unlink();
        this.widget = widget;

        if (!activeName.isEmpty() && widget.getStage() != null && widget.getController() != null) {
            this.onActiveMethodChange(null,null);
            activeName.addListener(this::onActiveMethodChange);
        }

        if (!deActiveName.isEmpty() && widget.getController() != null) {
            onDeActiveMethodChange(null,null);
            deActiveName.addListener(this::onDeActiveMethodChange);
        }

        if (!openName.isEmpty() && widget.getController() != null) {
            onOpenMethodChange(null,null);
            openName.addListener(this::onOpenMethodChange);
        }

        if (!closeName.isEmpty() && widget.getController() != null) {
            onCloseMethodChange(null,null);
            closeName.addListener(this::onCloseMethodChange);
        }

        if (!iconifiedName.isEmpty()) {
            onIconifyMethodChange(null,null);
            iconifiedName.addListener(this::onIconifyMethodChange);
        }

        if (!deIconifiedName.isEmpty() && widget.getController() != null) {
            onDeIconifyMethodChange(null,null);
            deIconifiedName.addListener(this::onDeIconifyMethodChange);
        }

        if (!iconifiedName.isEmpty() && widget.getController() != null) {
            onDeIconifyMethodChange(null,null);
            deIconifiedName.addListener(this::onDeIconifyMethodChange);
        }

    }

    @Override
    public void unlink() {
        this.activeName.removeListener(this::onActiveMethodChange);
        this.deActiveName.removeListener(this::onDeActiveMethodChange);
        this.iconifiedName.removeListener(this::onIconifyMethodChange);
        this.deIconifiedName.removeListener(this::onDeIconifyMethodChange);
        this.openName.removeListener(this::onOpenMethodChange);
        this.closeName.removeListener(this::onCloseMethodChange);

        activeMethod = null;
        deActiveMethod = null;
        iconifiedMethod = null;
        deIconifiedMethod = null;
        openMethod = null;
        closeMethod = null;

    }
}
