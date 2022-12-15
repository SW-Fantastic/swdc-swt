package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.actions.SelectionProperty;
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
    public void initWidget(CCombo created) {
        if (cCombo == null){
            return;
        }
        super.initWidget(cCombo);
        selectionProperty.manage(this);
        cCombo.addSelectionListener(selectionProperty.dispatcher());
    }

    @Override
    public CCombo getWidget() {
        return cCombo;
    }

    @Override
    public CCombo getWidget(Composite parent) {
        if (cCombo == null && parent != null) {
            cCombo = new CCombo(parent,this.flag);
            initWidget(cCombo);
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
