package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.widgets.base.Controlable;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

public class SWTButton extends SWTLabelControlWidget<Button> implements Selectionable, Controlable {

    private int flags;

    private Button button;

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTButton(int flags) {
        this.flags = flags;
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
        }
        return button;
    }

    /**
     * 在CheckBox的时候使用。
     * @return
     */
    public boolean isSelected() {
        return button.getSelection();
    }

    @Override
    public void ready() {
        super.ready();
        if (button == null) {
            return;
        }
        SWTWidgets.setupLayoutData(this,this.button);

        // 接管本组件的SelectionEvent
        selectionProperty.manage(this);
        // 添加本Section的Listener到button。
        button.addSelectionListener(selectionProperty.dispatcher());
    }

    public void onAction(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        selectionProperty.closure(closure);
    }

    public void onAction(String methodName) {
        this.selectionProperty.setSelectionMethod(methodName);
    }

    public static SWTButton button(int flags) {
        return new SWTButton(flags);
    }

}
