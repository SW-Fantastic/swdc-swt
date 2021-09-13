package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;

public class SWTScale extends SWTWidget<Scale> {

    private int flags;

    private Scale scale;

    public SWTScale(int flags) {
        this.flags = flags;
    }

    @Override
    public Scale getWidget(Composite parent) {
        if (this.scale == null && parent != null) {
            scale = new Scale(parent,this.flags);
            if (this.getLayoutData() != null) {
                scale.setLayoutData(getLayoutData().get());
            }
        }
        return scale;
    }

    public static SWTScale scale(int flags) {
        return new SWTScale(flags);
    }

}
