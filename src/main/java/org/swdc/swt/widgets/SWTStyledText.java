package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

public class SWTStyledText extends SWTControlWidget<StyledText> implements Selectionable {

    private int flag;

    private StyledText text;

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTStyledText(int flag) {
        this.flag = flag;
    }

    @Override
    public void initWidget(StyledText created) {
        if (text != null) {
            super.initWidget(text);
            selectionProperty.manage(this);
            text.addSelectionListener(selectionProperty.dispatcher());
        }
    }

    @Override
    public StyledText getWidget() {
        return text;
    }

    @Override
    public StyledText getWidget(Composite parent) {
        if (parent != null && text == null) {
            text = new StyledText(parent,flag);
            initWidget(text);
        }
        return text;
    }

    @Override
    public void onAction(String methodName) {
        this.selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        selectionProperty.closure(closure);
    }

}
