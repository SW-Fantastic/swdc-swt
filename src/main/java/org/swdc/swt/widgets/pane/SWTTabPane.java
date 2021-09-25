package org.swdc.swt.widgets.pane;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.util.Arrays;
import java.util.List;

public class SWTTabPane extends SWTWidget<TabFolder> implements SWTContainer {

    private TabFolder folder;

    private int flags;

    private SWTTab tabs;

    private SizeProperty sizeProperty = new SizeProperty();

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

    public SWTTabPane size(int width, int height) {
        this.sizeProperty.set(width,height);
        return this;
    }

    @Override
    protected TabFolder getWidget(Composite parent) {
        if (this.folder == null && parent != null){
            this.folder = new TabFolder(parent,flags);

            if (this.layout != null) {
                folder.setLayout(layout.getLayout());
            }

            if (this.tabs != null) {
                SWTTab item = (SWTTab) tabs.getFirst();
                while (item != null) {
                    item.create(this.folder,this);
                    item = (SWTTab) item.getNext();
                }
            }
            this.sizeProperty.manage(folder);
        }

        return folder;
    }

    @Override
    public void ready(Stage stage) {
        if (folder == null) {
            return;
        }
        if (this.tabs != null) {
            SWTTab item = (SWTTab) tabs.getFirst();
            while (item != null) {
                item.initStage(stage);
                item.ready(stage);
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
