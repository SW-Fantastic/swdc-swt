package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.widgets.base.Controlable;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

public class SWTCComboBox extends SWTLabelControlWidget<CCombo> implements Selectionable {

    private int flag;
    private CCombo cCombo;

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTCComboBox(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready() {
        super.ready();
        if (cCombo == null){
            return;
        }
        selectionProperty.manage(this);
        cCombo.addSelectionListener(selectionProperty.dispatcher());
    }

    @Override
    protected CCombo getWidget(Composite parent) {
        if (cCombo == null && parent != null) {
            cCombo = new CCombo(parent,this.flag);
        }
        return cCombo;
    }

    @Override
    public void onAction(String methodName) {
        selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        selectionProperty.closure(closure);
    }
}
