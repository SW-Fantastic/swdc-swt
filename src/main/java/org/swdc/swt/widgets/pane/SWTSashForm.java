package org.swdc.swt.widgets.pane;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.base.SWTControlWidget;

import java.util.Arrays;
import java.util.List;

public class SWTSashForm extends SWTControlWidget<SashForm> implements SWTContainer {

    private int flag;

    private SashForm form;

    private SWTWidget widget;

    private ObservableValue<Integer> spec = new ObservableValue<>(2);

    private ObservableValue<int[]> percentage = new ObservableValue<>(){
        @Override
        protected boolean doEquals(int[] self, int[] another) {
            return Arrays.equals(self,another);
        }
    };

    public SWTSashForm(int flag) {
        this.flag = flag;
        spec.addListener(this::onSpecChange);
        percentage.addListener(this::onPercentageChange);
    }

    private void onPercentageChange(int[] oldArr, int[] newArr) {
        if (this.percentage.isEmpty() || form == null) {
            return;
        }
        this.form.setWeights(percentage.get());
    }

    private void onSpecChange(int valOld, int valNew) {
        if (this.form != null && !spec.isEmpty()) {
            this.form.SASH_WIDTH = spec.get();
            this.form.requestLayout();
        }
    }

    public SWTSashForm spacing(int spec) {
        this.spec.set(spec);
        return this;
    }

    @Override
    public void initWidget(SashForm created) {
        if (form == null) {
            return;
        }
        super.initWidget(form);
        SWTWidget swtWidget = widget;
        while (swtWidget != null) {
            swtWidget.setParent(this);
            swtWidget.getWidget(form);
            swtWidget = swtWidget.getNext();
        }
        if (!percentage.isEmpty()) {
            form.setWeights(percentage.get());
        }
        SWTWidgets.setupLayoutData(this,form);
    }

    @Override
    public SashForm getWidget(Composite parent) {
        if (this.form == null && parent != null) {
            this.form = new SashForm(parent,this.flag);
            form.SASH_WIDTH = spec.get();
            this.initWidget(form);
        }
        return form;
    }

    public SWTSashForm percentage(int ...per) {
        percentage.set(per);
        return this;
    }

    @Override
    public void children(SWTWidget widget) {
        this.widget = widget;
    }

    @Override
    public List<SWTWidget> children() {
        return Arrays.asList(this.widget);
    }
}
