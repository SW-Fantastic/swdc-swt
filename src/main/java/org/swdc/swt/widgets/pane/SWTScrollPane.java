package org.swdc.swt.widgets.pane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.swdc.swt.layouts.SWTLayout;
import org.swdc.swt.widgets.SWTContainer;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;

/**
 *
 * 如果发现滚动框在需要滚动的时候会
 * 自动延伸到最大的大小，
 *
 * 这并不是bug，你需要为ScrollPane的Parent设置一个width和height。
 * GridLayout则直接使用GridData的heightHint和widthHint，即SWTGridData的width和height。
 *
 */
public class SWTScrollPane extends SWTWidget<Composite> implements SWTContainer {

    private ScrolledComposite scrolledComposite;
    private Composite wrapper;

    private SWTWidget widget;

    private SWTLayout layout;

    private int flag;

    private int width;

    private  int height;

    private boolean fixWidth;

    private boolean fixHeight;

    public SWTScrollPane(int flag) {
        this.flag = flag;
    }

    public SWTScrollPane size(int width, int height) {
        this.width = width;
        this.height = height;
        if (this.scrolledComposite != null) {
            scrolledComposite.setMinSize(width,height);
        }
        return this;
    }

    public SWTScrollPane layout(SWTLayout layout) {
        this.layout = layout;
        return this;
    }

    @Override
    public void ready(Stage stage) {
        if (this.widget != null) {
            Widget composite = this.widget.getWidget(scrolledComposite);
            Control control = (Control) composite;
            scrolledComposite.setContent(control);
            control.requestLayout();
        }
    }

    public SWTScrollPane fixWidth(boolean fixWidth) {
        this.fixWidth = fixWidth;
        this.fixHeight = !fixWidth;
        return this;
    }

    public SWTScrollPane fixHeight(boolean fixHeight) {
        this.fixHeight = fixHeight;
        this.fixWidth = !fixHeight;
        return this;
    }

    @Override
    public Composite getWidget(Composite parent) {
        if (scrolledComposite == null && parent != null) {
            wrapper = new Composite(parent,SWT.NONE);
            wrapper.setLayout(new FillLayout());
            wrapper.setSize(width,height);
            scrolledComposite = new ScrolledComposite(wrapper,flag);
            scrolledComposite.setExpandVertical(true);
            scrolledComposite.setExpandHorizontal(true);
            scrolledComposite.setAlwaysShowScrollBars(true);

            scrolledComposite.addListener( SWT.Resize, event -> {
                Rectangle clientArea = scrolledComposite.getClientArea();
                Button button = new Button((Composite) scrolledComposite.getContent(),SWT.PUSH);
                button.setText("test BTN");
                Point minSize = null;
                if (fixWidth) {
                    minSize = scrolledComposite.getContent().computeSize( clientArea.width, SWT.DEFAULT );
                } else if (fixHeight){
                    minSize = scrolledComposite.getContent().computeSize( SWT.DEFAULT, clientArea.height );
                } else {
                    minSize = scrolledComposite.getContent().computeSize( SWT.DEFAULT, SWT.DEFAULT );
                }
                scrolledComposite.setMinSize( minSize );
            } );
        }
        return wrapper;
    }

    @Override
    public void children(SWTWidget widget) {
        if (widget.getFirst() != widget.getLast()) {
            throw new RuntimeException("只能放一个");
        }
        this.widget = widget;
    }

    public static SWTScrollPane scrollPane(int flag) {
        return new SWTScrollPane(flag);
    }

}
