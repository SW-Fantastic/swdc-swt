package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

public class SWTTree extends SWTWidget<Tree> {

    private Tree tree;

    private int flag;

    public SWTTree(int flag) {
        this.flag = flag;
    }

    @Override
    public Tree getWidget(Composite parent) {
        if (this.tree == null && parent != null) {
            tree = new Tree(parent,flag);
            if (this.getLayoutData() != null) {
                tree.setLayoutData(getLayoutData().get());
            }
        }
        return tree;
    }

    public static SWTTree tree(int flag) {
        return new SWTTree(flag);
    }

}
