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
import org.swdc.swt.beans.*;

import java.lang.reflect.Method;

public class SWTButton extends SWTWidget<Button> {

    private int flags;

    private Button button;

    private TextProperty text = new TextProperty();

    private SizeProperty sizeProperty = new SizeProperty();

    private String methodName;

    private SelectionListener clickListener;

    private Method actionMethod;

    private ColorProperty colorProperty = new ColorProperty();

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
    }

    public SWTButton text(String text) {
        this.text.set(text);
        return this;
    }

    public SWTButton size(int width, int height) {
        this.sizeProperty.set(new Point(width,height));
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

            if (this.getLayoutData() != null) {
                button.setLayoutData(this.getLayoutData().get());
            }

            sizeProperty.manage(button);
            colorProperty.manage(button);
            text.manage(button);

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
        colorProperty.setForeground(color);
        return this;
    }

    public SWTButton backgroundColor(String color) {
        colorProperty.setBackground(color);
        return this;
    }

    public static SWTButton button(int flags, String text) {
        return new SWTButton(flags,text);
    }

}
