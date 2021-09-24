package org.swdc.swt.widgets.pane;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;

public class SWTCTabPane extends SWTWidget<CTabFolder> implements SWTContainer {

    private int flags;

    private CTabFolder folder;

    private SWTCTab tabs;

    private SizeProperty sizeProperty = new SizeProperty();

    private SWTLayout layout;

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

    public SWTCTabPane size(int width, int height) {
        this.sizeProperty.set(width,height);
        return this;
    }

    @Override
    public CTabFolder getWidget(Composite parent) {
        if (this.folder == null && parent != null){
            this.folder = new CTabFolder(parent,flags);

            if (this.layout != null) {
                folder.setLayout(layout.getLayout());
            }

            if (this.getLayoutData() != null) {
                this.folder.setLayoutData(getLayoutData().get());
            }

            if (this.tabs != null) {
                SWTCTab item = (SWTCTab) tabs.getFirst();
                while (item != null) {
                    item.getWidget(this.folder);
                    item = (SWTCTab) item.getNext();
                }
            }
            this.sizeProperty.manage(folder);
        }

        return folder;
    }

    @Override
    public void ready(Stage stage) {
        if (this.tabs != null) {
            SWTCTab item = (SWTCTab) tabs.getFirst();
            while (item != null) {
                item.setStage(stage);
                item.ready(stage);
                item = (SWTCTab) item.getNext();
            }
        }
    }

    @Override
    public void children(SWTWidget widget) {
        if (!(widget instanceof SWTCTab)) {
            throw new RuntimeException("TabPane内部只能使用SWTTab");
        }
        this.tabs = (SWTCTab) widget;
    }

}
