package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.swdc.swt.beans.ProgressProperty;
import org.swdc.swt.widgets.base.SWTControlWidget;

public class SWTProgressBar extends SWTControlWidget<ProgressBar> {

    private int flag;

    private ProgressBar progressBar;

    private ProgressProperty progressProperty = new ProgressProperty();

    public SWTProgressBar(int flag) {
        this.flag = flag;
    }

    @Override
    public void initWidget(ProgressBar created) {
        if (progressBar == null) {
            return;
        }
        super.initWidget(progressBar);
        SWTWidgets.setupLayoutData(this,this.progressBar);
    }

    @Override
    public ProgressBar getWidget() {
        return progressBar;
    }

    @Override
    public ProgressBar getWidget(Composite parent) {
        if (progressBar == null && parent != null) {
            this.progressBar = new ProgressBar(parent,this.flag);
            this.progressProperty.manage(progressBar);
            initWidget(progressBar);
        }
        return progressBar;
    }
}
