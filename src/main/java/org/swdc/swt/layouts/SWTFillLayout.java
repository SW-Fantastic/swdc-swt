package org.swdc.swt.layouts;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Layout;

public class SWTFillLayout implements SWTLayout {

    private FillLayout layout = null;

    public SWTFillLayout(int flag) {
        layout = new FillLayout(flag);
    }

    public SWTFillLayout margin(int marginLeftRight, int marginTopBottom) {
        this.layout.marginWidth = marginLeftRight;
        this.layout.marginHeight = marginTopBottom;
        return this;
    }

    public SWTFillLayout spacing(int spec) {
        this.layout.spacing = spec;
        return this;
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    public static SWTFillLayout fillLayout(int flags) {
        return new SWTFillLayout(flags);
    }
}
