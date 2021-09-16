package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.ObservableSizeValue;
import org.swdc.swt.beans.ObservableValue;

import java.lang.reflect.Method;

public class SWTButton extends SWTWidget<Button> {

    private int flags;

    private Button button;

    private ObservableValue<String> text = new ObservableValue<>();

    private ObservableValue<Point> size = new ObservableSizeValue(new Point(SWT.DEFAULT,SWT.DEFAULT));

    private String methodName;

    private SelectionListener clickListener;

    private Method actionMethod;

    private String color;

    private String backgroundColor;

    private SelectionAdapter dispatcher = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent selectionEvent) {
            if (SWTButton.this.clickListener != null) {
                SWTButton.this.clickListener.widgetSelected(selectionEvent);
            }
        }
    };

    public SWTButton(int flags, String text) {
        this.text.set(text);
        this.flags = flags;
        this.text.addListener((oldVal, newVal) -> {
            if (this.button != null) {
                button.setText(this.text.isEmpty() ? "" : this.text.get());
            }
        });
        this.size.addListener((oldVal, newVal) -> {
            if (this.button != null && !this.size.isEmpty()) {
                this.button.setSize(this.size.get());
            }
        });
    }

    public SWTButton text(String text) {
        this.text.set(text);
        return this;
    }

    public SWTButton size(int width, int height) {
        this.size.set(new Point(width,height));
        return this;
    }

    @Override
    public Button getWidget(Composite parent) {
        if (this.button == null && parent != null) {
            if (SWTWidgets.isFormAPI(parent)) {
                FormToolkit toolkit = SWTWidgets.factory();
                button = toolkit.createButton(parent,"",flags);
                toolkit.paintBordersFor(parent);
            } else {
                button = new Button(parent,flags);
            }
            button.addSelectionListener(dispatcher);

            if (!this.text.isEmpty()) {
                button.setText(text.get());
            }

            if (this.color != null) {
                color(this.color);
            }

            if (this.backgroundColor != null) {
                backgroundColor(backgroundColor);
            }

            if (this.getLayoutData() != null) {
                button.setLayoutData(this.getLayoutData().get());
            }

        }
        return button;
    }


    @Override
    public void ready(Stage stage) {

        Object controller = stage.getController();
        if (controller == null || methodName == null) {
            return;
        }

        Class controllerClazz = controller.getClass();
        if (actionMethod == null) {
            try {
                actionMethod = controllerClazz.getMethod(methodName);
            } catch (Exception e) {
                try {
                    actionMethod = controllerClazz.getMethod(methodName,SelectionEvent.class);
                } catch (Exception ex) {
                    throw new RuntimeException("找不到可用的方法：" + methodName);
                }
            }
        }
    }

    public SWTButton action(Closure closure) {
        this.clickListener = new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                closure.call(selectionEvent);
            }
        };
        return this;
    }

    public SWTButton action(String methodName) {
        this.methodName = methodName;
        this.clickListener = new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Stage stage = SWTButton.this.getStage();
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
        };

        return this;
    }


    public SWTButton color(String color) {
        this.color = color;
        if (button == null) {
            return this;
        }
        Color swtColor = SWTWidgets.color(color).getColor();
        if (swtColor != null) {
            button.setForeground(swtColor);
        }
        return this;
    }

    public SWTButton backgroundColor(String color) {
        this.backgroundColor = color;
        if (button == null) {
            return this;
        }
        SWTColor swtColor = SWTWidgets.color(color);
        if (swtColor != null) {
            button.setBackground(swtColor.getColor());
        }
        return this;
    }

    public static SWTButton button(int flags, String text) {
        return new SWTButton(flags,text);
    }

}
