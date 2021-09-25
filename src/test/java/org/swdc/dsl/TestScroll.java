package org.swdc.dsl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.swdc.swt.layouts.SWTGridLayout;
import org.swdc.swt.widgets.Stage;
import org.swdc.swt.widgets.pane.SWTPane;
import org.swdc.swt.widgets.pane.SWTScrollPane;

public class TestScroll {

    Display display = new Display();
    Shell shell = new Shell(display);

    public TestScroll() {

        //textUser.forceFocus();

       /*Composite wrapper = new Composite(shell,SWT.NORMAL);
        wrapper.setLayout(new FillLayout());
        wrapper.setSize(120,120);

        ScrolledComposite scrolledComposite = new ScrolledComposite(wrapper,SWT.H_SCROLL|SWT.V_SCROLL);
        scrolledComposite.setMinSize(120,120);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
        scrolledComposite.setAlwaysShowScrollBars(true);

        Composite composite = new Composite(scrolledComposite,SWT.NORMAL);
        composite.setSize(1000,1000);

        scrolledComposite.setContent(composite);

        composite.requestLayout();*/

       class StageTest extends Stage {
            @Override
            public Shell getShell() {
                return shell;
            }
        }

        shell.setLayout(new GridLayout());

        SWTScrollPane pane = new SWTScrollPane(SWT.H_SCROLL | SWT.V_SCROLL);
        pane.size(120,120);
        pane.children(new SWTPane(SWT.NORMAL).size(1000,1000));
        pane.layout(SWTGridLayout.cell().fillHeight(false).fillWidth(true));

        pane.create(shell,null);

        StageTest test = new StageTest();
        test.children(pane);

        pane.initStage(test);
        pane.ready(test);

        shell.pack();
        shell.open();
        // Set up the event loop.
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                // If no more entries in event queue
                display.sleep();
            }
        }

        display.dispose();
    }

    public static void main(String[] args) {
        new TestScroll();
    }

}
