package org.swdc.swt.beans;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.widgets.Widget;
import org.swdc.swt.widgets.SWTColor;
import org.swdc.swt.widgets.SWTWidgets;

import java.lang.reflect.Method;

public class ColorProperty implements Property<String> {

    private Widget widget;
    private Method setter;
    private Method bgSetter;

    private ObservableValue<String> color = new ObservableValue<>("");
    private ObservableValue<String> background = new ObservableValue<>("");

    @Override
    public void set(String s) {
        throw new RuntimeException("方法无效");
    }

    @Override
    public String get() {
        throw new RuntimeException("方法无效");
    }

    public void setForeground(String color) {
        this.color.set(color);
    }

    public void setBackground(String background) {
        this.background.set(background);
    }

    public String getForeground() {
        return color.isEmpty()? "" : color.get();
    }

    public String getBackground() {
        return background.isEmpty()?"":background.get();
    }

    @Override
    public void manage(Widget widget) {
        unlink();
        this.widget = widget;
        try {
            setter = widget.getClass().getMethod("setForeground", Color.class);
            color.addListener(this::onForegroundChange);
            this.onForegroundChange(null,null);
        } catch (Exception e) {

        }

        try {
            bgSetter = widget.getClass().getMethod("setBackground",Color.class);
            color.addListener(this::onBackgroundChange);
            this.onBackgroundChange(null,null);
        } catch (Exception ex) {
        }
    }

    private void onBackgroundChange(String oldVal, String newVal) {
        if (widget == null || background.isEmpty()) {
            return;
        }
        if (bgSetter == null) {
            return;
        }
        try {
            SWTColor color = SWTWidgets.color(this.background.get());
            Color swtcolor = color.getColor();
            if (swtcolor == null) {
                return;
            }
            bgSetter.invoke(widget,swtcolor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void onForegroundChange(String oldVal, String newVal) {
        if (widget == null || color.isEmpty()) {
            return;
        }
        if (setter == null) {
            return;
        }
        try {
            SWTColor color = SWTWidgets.color(this.color.get());
            Color swtcolor = color.getColor();
            if (swtcolor == null) {
                return;
            }
            setter.invoke(widget,swtcolor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlink() {
        if (this.widget != null) {
            this.color.removeListener(this::onForegroundChange);
            this.background.removeListener(this::onBackgroundChange);
            this.setter = null;
            this.bgSetter = null;
        }
    }

    public void addBackgroundListener(ChangeListener<String> background) {
        this.background.addListener(background);
    }

    public void removeBackgroundListener(ChangeListener<String> background) {
        this.background.removeListener(background);
    }

    public void addForegroundListener(ChangeListener<String> foreground) {
        this.color.addListener(foreground);
    }

    public void removeForegroundListener(ChangeListener<String> foreground) {
        this.color.removeListener(foreground);
    }

    public ObservableValue<String> valueColor() {
        return this.color;
    }

    public ObservableValue<String> valueBackground(){
        return this.background;
    }

}
