package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.swdc.swt.beans.SizeProperty;

public class SWTTree extends SWTWidget<Tree> {

    private Tree tree;

    private int flag;

    private SizeProperty sizeProperty = new SizeProperty();

    public SWTTree(int flag) {
        this.flag = flag;
    }

    public SWTTree size(int width,int height) {
        this.sizeProperty.set(width,height);
        return this;
    }

    @Override
    public Tree getWidget(Composite parent) {
        if (this.tree == null && parent != null) {
            tree = new Tree(parent,flag);
            if (this.getLayoutData() != null) {
                tree.setLayoutData(getLayoutData().get());
            }
            sizeProperty.manage(tree);
        }
        return tree;
    }

    public static SWTTree tree(int flag) {
        return new SWTTree(flag);
    }

}
