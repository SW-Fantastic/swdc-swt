package org.swdc.swt.widgets.pane;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.beans.SelectProperty;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.base.SWTControlWidget;

import java.util.Arrays;
import java.util.List;

public class SWTCTabPane extends SWTControlWidget<CTabFolder> implements SWTContainer {

    private int flags;

    private CTabFolder folder;

    private SWTCTab tabs;

    private SWTLayout layout;

    private SelectProperty selectProperty = new SelectProperty();

    public SWTCTabPane(int flag) {
        this.flags = flag;
    }

    public SWTCTabPane tabLayout(SWTLayout layout) {
        this.layout = layout;
        if (this.folder != null) {
            this.folder.setLayout(layout.getLayout());
        }
        return this;
    }

    @Override
    public CTabFolder getWidget(Composite parent) {
        if (this.folder == null && parent != null){
            this.folder = new CTabFolder(parent,flags);

            if (this.layout != null) {
                folder.setLayout(layout.getLayout());
            }
            this.selectProperty.manage(folder);
            initWidget(folder);
            if (this.tabs != null) {
                SWTCTab item = (SWTCTab) tabs.getFirst();
                while (item != null) {
                    item.setParent(this);
                    item.getWidget(this.folder);
                    item = (SWTCTab) item.getNext();
                }
            }
        }

        return folder;
    }

    @Override
    public void initWidget(CTabFolder created) {
        if (created == null) {
            return;
        }
        super.initWidget(created);
        if (this.tabs != null) {
            SWTCTab item = (SWTCTab) tabs.getFirst();
            while (item != null) {
                item = (SWTCTab) item.getNext();
            }
            SWTWidgets.setupLayoutData(this,folder);
        }
    }

    public void select(int idx) {
        if (this.folder == null) {
            return;
        }
        if (folder.getChildren().length < idx) {
            return;
        }
        selectProperty.set(idx);
    }


    @Override
    public void children(SWTWidget widget) {
        if (!(widget instanceof SWTCTab)) {
            throw new RuntimeException("TabPane内部只能使用SWTTab");
        }
        this.tabs = (SWTCTab) widget;
    }

    @Override
    public List<SWTWidget> children() {
        return Arrays.asList(tabs);
    }
}
