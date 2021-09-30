package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.beans.*;
import org.swdc.swt.layouts.LayoutData;
import org.swdc.swt.layouts.SWTFormData;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class SWTButton extends SWTWidget<Button> {

    private int flags;

    private Button button;

    private TextProperty text = new TextProperty();
    private ColorProperty colorProperty = new ColorProperty();

    private SelectionProperty selectionProperty = new SelectionProperty();

    private SelectionListener clickListener = selectionProperty.dispatcher();


    public SWTButton(int flags, String text) {
        this.text.set(text);
        this.flags = flags;
    }

    public SWTButton text(String text) {
        this.text.set(text);
        return this;
    }

    @Override
    protected Button getWidget(Composite parent) {
        if (this.button == null && parent != null) {
            if (SWTWidgets.isFormAPI(parent)) {
                FormToolkit toolkit = SWTWidgets.factory();
                button = toolkit.createButton(parent,"",flags);
                toolkit.paintBordersFor(parent);
            } else {
                button = new Button(parent,flags);
            }
            colorProperty.manage(button);
            text.manage(button);

        }
        return button;
    }

    @Override
    public void ready(Stage stage) {
        if (button == null) {
            return;
        }
        SWTWidgets.setupLayoutData(this,this.button);

        // 接管本组件的SelectionEvent
        selectionProperty.manage(this);
        // 添加本Section的Listener到button。
        button.addSelectionListener(clickListener);
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
        this.selectionProperty.set(methodName);
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
