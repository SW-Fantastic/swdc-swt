package org.swdc.swt.widgets.pane;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;

public class SWTTabPane extends SWTWidget<TabFolder> implements SWTContainer {

    private TabFolder folder;

    private int flags;

    private SWTTab tabs;

    private int width;
    private int height;

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
        this.width = width;
        this.height = height;
        if (this.folder!= null) {
            this.folder.setSize(width,height);
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

            if (this.getLayoutData() != null) {
                this.folder.setLayoutData(getLayoutData().get());
            }

            if (this.tabs != null) {
                SWTTab item = (SWTTab) tabs.getFirst();
                while (item != null) {
                    item.getWidget(this.folder);
                    item = (SWTTab) item.getNext();
                }
            }
        }

        return folder;
    }

    @Override
    public void ready(Stage stage) {
        if (this.tabs != null) {
            SWTTab item = (SWTTab) tabs.getFirst();
            while (item != null) {
                item.setStage(stage);
                item.ready(stage);
                item = (SWTTab) item.getNext();
            }
        }
    }

    @Override
    public void children(SWTWidget widget) {
        if (!(widget instanceof SWTTab)) {
            throw new RuntimeException("TabPane内部只能使用SWTTab");
        }
        this.tabs = (SWTTab) widget;
    }

    public static SWTTabPane tabPane(int flags) {
        return new SWTTabPane(flags);
    }

}
