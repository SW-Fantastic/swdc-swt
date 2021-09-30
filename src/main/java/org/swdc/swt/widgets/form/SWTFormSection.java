package org.swdc.swt.widgets.form;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.Section;
import org.swdc.swt.beans.ExpandProperty;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

public class SWTFormSection extends SWTWidget<Section> implements SWTContainer {

    private Section section;

    private int flags;

    private SWTWidget widget;

    private TextProperty textProperty = new TextProperty();
    private ExpandProperty expandProperty = new ExpandProperty();

    public SWTFormSection(int flags) {
        this.flags = flags;
    }

    public SWTFormSection expand(boolean expand) {
        this.expandProperty.set(expand);
        return this;
    }

    public SWTFormSection text(String text) {
        this.textProperty.set(text);
        return this;
    }

    @Override
    public void ready(Stage stage) {
        if (widget != null) {
            if(widget.getFirst() != widget.getLast()) {
                throw new RuntimeException("Section只能放置一个Widget");
            }
            Widget target = widget.create(section,this);
            widget.initStage(stage);
            widget.ready(stage);
            section.setClient((Control) target);
            SWTWidgets.setupLayoutData(this,section);
        }
    }

    @Override
    protected Section getWidget(Composite parent) {
        if (section == null && parent != null) {
            section = SWTWidgets.factory().createSection(parent,this.flags);
            SWTWidgets.factory().paintBordersFor(section);

            this.expandProperty.manage(section);
            this.textProperty.manage(section);

            section.addExpansionListener(new ExpansionAdapter() {
                @Override
                public void expansionStateChanged(ExpansionEvent e) {
                    expandProperty.setDirectly(section.isExpanded());
                }
            });
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
