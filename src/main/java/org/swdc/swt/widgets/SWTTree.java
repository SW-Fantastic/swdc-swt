package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.actions.TreeProperty;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.widgets.base.Selectionable;

public class SWTTree extends SWTWidget<Tree> implements Selectionable {

    private Tree tree;

    private int flag;

    private SelectionProperty selectionProperty = new SelectionProperty();

    private TreeProperty treeProperty = new TreeProperty();
    private Closure closureExpand;
    private Closure closureCollapse;

    private SWTreeNode root = null;

    public SWTTree(int flag) {
        this.flag = flag;
    }

    @Override
    public void ready() {
        super.ready();
        if (tree == null) {
            return;
        }
        selectionProperty.manage(this);
        tree.addSelectionListener(selectionProperty.dispatcher());

        tree.addTreeListener(treeProperty.dispatcher());
        treeProperty.manage(this);

        SWTWidgets.setupLayoutData(this,tree);
    }

    @Override
    protected Tree getWidget(Composite parent) {
        if (this.tree == null && parent != null) {
            tree = new Tree(parent,flag);
            if (this.getLayoutData() != null) {
                tree.setLayoutData(getLayoutData().get());
            }
        }
        return tree;
    }

    @Override
    public void onAction(String methodName) {
        this.selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        selectionProperty.closure(closure);
    }

    public void onTreeExpand(String method) {
        treeProperty.setExpandMethod(method);
    }

    public void onTreeCollapse(String method) {
        treeProperty.setCollapseMethod(method);
    }

    public void onTreeExpand(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        this.closureExpand = closure;
        this.treeProperty.closure(this.closureCollapse,this.closureExpand);
    }

    public void onTreeCollapse(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        this.closureCollapse = closure;
        this.treeProperty.closure(this.closureCollapse,this.closureExpand);
    }

    public void setRoot(SWTreeNode root) {
        if (this.root != null) {
            this.root.remove();
        }

        this.root = root;
        this.root.refresh();
    }

    public SWTreeNode getRoot() {
        return root;
    }

    public static SWTTree tree(int flag) {
        return new SWTTree(flag);
    }

}
