package org.swdc.swt.widgets;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.widgets.base.SWTControlWidget;

public class SWTStyledText extends SWTControlWidget<StyledText> {

    private int flag;

    private StyledText text;

    public SWTStyledText(int flag) {
        this.flag = flag;
    }

    @Override
    protected StyledText getWidget(Composite parent) {
        if (parent != null && text == null) {
            text = new StyledText(parent,flag);
        }
        return text;
    }
}
