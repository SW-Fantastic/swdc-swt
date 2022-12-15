package org.swdc.swt.widgets.form;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.Section;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.base.SWTExpansationControlWidget;

public class SWTFormSection extends SWTExpansationControlWidget<Section> implements SWTContainer {

    private Section section;

    private int flags;

    private SWTWidget widget;

    public SWTFormSection(int flags) {
        this.flags = flags;
    }

    @Override
    public void initWidget(Section created) {
        if (widget != null) {
            if(widget.getFirst() != widget.getLast()) {
                throw new RuntimeException("Section只能放置一个Widget");
            }
            super.initWidget(section);
            widget.setParent(this);
            Widget target = widget.getWidget(section);
            section.setClient((Control) target);
            SWTWidgets.setupLayoutData(this,section);
        }
    }

    @Override
    public Section getWidget(Composite parent) {
        if (section == null && parent != null) {
            section = SWTWidgets.factory().createSection(parent,this.flags);
            SWTWidgets.factory().paintBordersFor(section);

            section.addExpansionListener(new ExpansionAdapter() {
                @Override
                public void expansionStateChanged(ExpansionEvent e) {
                    expand(section.isExpanded());
                }
            });

            initWidget(section);
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
