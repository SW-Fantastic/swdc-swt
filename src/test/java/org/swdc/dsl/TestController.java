package org.swdc.dsl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.swdc.swt.Widget;
import org.swdc.swt.layouts.SWTStackLayout;
import org.swdc.swt.widgets.SWTComboBox;
import org.swdc.swt.widgets.base.Initialize;
import org.swdc.swt.widgets.SWTButton;
import org.swdc.swt.widgets.SWTLabel;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.pane.SWTPane;

public class TestController implements Initialize {

    @Widget("lblTest")
    private SWTLabel label;

    @Widget("stack")
    private SWTPane stackPane;

    @Widget("btnA")
    private SWTButton btnA;

    @Widget("btnB")
    private SWTButton btnB;

    @Widget("demo")
    private SWTWidget demo;

    @Widget("comb")
    private SWTComboBox comboBox;

    private boolean flag = false;

    public void hello(SelectionEvent event) {
        System.err.println("Hello it was clicked");
        if (flag) {
            label.text("Test Flag A");
            label.size(180, SWT.DEFAULT);
        } else {
            label.text("Test Flag B");
            label.size(120,SWT.DEFAULT);
        }
        flag = !flag;

        demo.call("test");
    }

    public void change() {
        if (flag) {
            SWTStackLayout stackLayout = (SWTStackLayout) stackPane.getLayout();
            stackLayout.top(btnA);
        } else {
            SWTStackLayout stackLayout = (SWTStackLayout) stackPane.getLayout();
            stackLayout.top(btnB);
        }
        flag = !flag;
    }

    public void keyPress(KeyEvent event){
        System.err.println("pressed : " + event.character);
    }

    public void mouseMove(MouseEvent event) {
        System.err.println("moved");
    }

    public void dBClick() {
        System.err.println("dbClick");
    }

    public void mouseTrack() {
        System.err.println("track");
    }

    public void resize() {
        System.err.println("resized");
    }

    @Override
    public void initialize() {
        SWTStackLayout stackLayout = (SWTStackLayout) stackPane.getLayout();
        stackLayout.top(btnA);
        btnA.onAction("change");
        btnB.onAction("change");
    }
}
