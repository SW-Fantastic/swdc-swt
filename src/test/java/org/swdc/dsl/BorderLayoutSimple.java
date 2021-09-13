package org.swdc.dsl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.swdc.swt.layouts.BorderLayout;
import org.swdc.swt.layouts.BorderLayoutData;

public class BorderLayoutSimple {
    Display display = new Display();
    Shell shell = new Shell(display);

    public BorderLayoutSimple() {
        shell.setLayout(new BorderLayout());

        Button buttonWest = new Button(shell, SWT.PUSH);
        buttonWest.setText("West");
        buttonWest.setLayoutData(new BorderLayoutData(BorderLayout.WEST, 120, 0));

        Button buttonEast = new Button(shell, SWT.PUSH);
        buttonEast.setText("East");
        buttonEast.setLayoutData(new BorderLayoutData(BorderLayout.EAST,120,0));

        Button buttonNorth = new Button(shell, SWT.PUSH);
        buttonNorth.setText("North");
        buttonNorth.setLayoutData(new BorderLayoutData(BorderLayout.NORTH));

        Button buttonSouth = new Button(shell, SWT.PUSH);
        buttonSouth.setText("South");
        buttonSouth.setLayoutData(new BorderLayoutData(BorderLayout.SOUTH));

        Text text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        text.setText("Center");
        text.setLayoutData(new BorderLayoutData(BorderLayout.CENTER,400,480));

        shell.pack();
        shell.open();
        //textUser.forceFocus();

        // Set up the event loop.
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                // If no more entries in event queue
                display.sleep();
            }
        }

        display.dispose();
    }

    private void init() {

    }

    public static void main(String[] args) {
        new BorderLayoutSimple();
    }
}
