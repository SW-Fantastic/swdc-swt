package org.swdc.swt.widgets.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.Section;
import org.swdc.swt.beans.ObservableSizeValue;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

public class SWTFormSection extends SWTWidget<Section> implements SWTContainer {

    private Section section;

    private int flags;

    private SWTWidget widget;

    private ObservableValue<Point> size = new ObservableSizeValue(new Point(SWT.DEFAULT,SWT.DEFAULT));

    private ObservableValue<String> title = new ObservableValue<>("");

    private ObservableValue<Boolean> expand = new ObservableValue<>(false);

    public SWTFormSection(int flags) {
        this.flags = flags;
        this.expand.addListener((oldVal,newVal) -> {
          if (this.section != null) {
              this.section.setExpanded(newVal == null ? oldVal != null && oldVal : newVal);
          }
        });

        this.title.addListener((oldVal, newVal) -> {
            String title = newVal == null ? oldVal == null ? "" : oldVal : newVal;
            if (this.section != null) {
                this.section.setText(title);
            }
        });

        this.size.addListener((oldVal, newVal) -> {
            if (this.section != null && !this.size.isEmpty()) {
                this.section.setSize(this.size.get());
            }
        });

    }

    public SWTFormSection expand(boolean expand) {
        this.expand.set(expand);
        return this;
    }

    public SWTFormSection text(String text) {
        this.title.set(text);
        return this;
    }

    public SWTFormSection size(int width, int height) {
        this.size.set(new Point(width,height));
        return this;
    }

    @Override
    public void ready(Stage stage) {
        if (widget != null) {
            if(widget.getFirst() != widget.getLast()) {
                throw new RuntimeException("Section只能放置一个Widget");
            }
            Widget target = widget.getWidget(section);
            widget.setStage(stage);
            widget.ready(stage);
            section.setClient((Control) target);
        }
    }

    @Override
    public Section getWidget(Composite parent) {
        if (section == null && parent != null) {
            section = SWTWidgets.factory().createSection(parent,this.flags);
            SWTWidgets.factory().paintBordersFor(section);
            if (this.getLayoutData() != null) {
                section.setLayoutData(getLayoutData().get());
            }
            if (!size.isEmpty()) {
                section.setSize(size.get());
            }
            section.setExpanded(expand.get());
            section.setText(title.get());
        }
        return section;
    }

    @Override
    public void children(SWTWidget widget) {
        this.widget = widget;
    }

    public static SWTFormSection section(int flag) {
        return new SWTFormSection(flag);
    }

}
