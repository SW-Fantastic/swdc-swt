package org.swdc.dsl;

import jakarta.inject.Inject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.swdc.swt.Widget;
import org.swdc.swt.layouts.SWTStackLayout;
import org.swdc.swt.widgets.*;
import org.swdc.swt.widgets.base.Initialize;
import org.swdc.swt.widgets.pane.SWTPane;

import java.util.List;

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

    @Inject
    private TestMountView mountView;

    @Inject
    private SWTTestView viewSelf;

    @Widget("tree")
    private SWTTree tree;

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

        mountView.getStage().show();
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

        try {
            SWTreeNode<String> data = new SWTreeNode<>(tree,SWT.NONE,"Root");
            SWTreeNode<String> childA = new SWTreeNode<>(data,SWT.NONE,"ChiA");
            SWTreeNode<String> childB = new SWTreeNode<>(data,SWT.NONE,"ChiB");

            List<SWTreeNode<String>> child = data.getChildren();
            child.add(childA);
            child.add(childB);

            tree.setRoot(data);

        } catch (Exception e) {
        }


    }
}
