package org.swdc.swt.widgets.pane;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.base.SWTControlWidget;

import java.util.Arrays;
import java.util.List;

public class SWTTabPane extends SWTControlWidget<TabFolder> implements SWTContainer {

    private TabFolder folder;

    private int flags;

    private SWTTab tabs;

    private SWTLayout layout;

    public SWTTabPane(int flags) {
        this.flags = flags;
    }

    public SWTTabPane tabLayout(SWTLayout layout) {
        this.layout = layout;
        if (this.folder != null) {
            this.folder.setLayout(layout.getLayout());
        }
        return this;
    }


    @Override
    public TabFolder getWidget(Composite parent) {
        if (this.folder == null && parent != null){
            this.folder = new TabFolder(parent,flags);

            if (this.layout != null) {
                folder.setLayout(layout.getLayout());
            }
            initWidget(folder);
            if (this.tabs != null) {
                SWTTab item = (SWTTab) tabs.getFirst();
                while (item != null) {
                    item.setParent(this);
                    item.getWidget(this.folder);
                    item = (SWTTab) item.getNext();
                }
            }
        }
        return folder;
    }

    @Override
    public void initWidget(TabFolder created) {
        if (folder == null) {
            return;
        }
        super.initWidget(folder);
        if (this.tabs != null) {
            SWTTab item = (SWTTab) tabs.getFirst();
            while (item != null) {
                item = (SWTTab) item.getNext();
            }
        }
        SWTWidgets.setupLayoutData(this,folder);
    }

    @Override
    public void children(SWTWidget widget) {
        if (!(widget instanceof SWTTab)) {
            throw new RuntimeException("TabPane内部只能使用SWTTab");
        }
        this.tabs = (SWTTab) widget;
    }

    @Override
    public List<SWTWidget> children() {
        return Arrays.asList(tabs);
    }

    public static SWTTabPane tabPane(int flags) {
        return new SWTTabPane(flags);
    }

}
