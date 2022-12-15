package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.swdc.swt.beans.BaseObservableCollection;
import org.swdc.swt.beans.ObservableArrayList;

import java.util.List;
import java.util.function.Function;

public class SWTreeNode<T> {

    private SWTTree tree;

    private SWTreeNode parent;

    private int flag;

    private TreeItem item;

    private T value;

    private Function<T,String> labelFactory = Object::toString;

    private ObservableArrayList<SWTreeNode<T>> children = new ObservableArrayList<>();

    public SWTreeNode(SWTTree tree, int flag, T value) {
        this.tree = tree;
        this.flag = flag;

        this.value = value;

        children.addListener(this::collectionChanged);
    }


    public SWTreeNode(SWTreeNode parent, int flag, T value) {
        this.flag = flag;
        this.value = value;

        SWTreeNode node = parent;
        while (node != null) {
            if (node.tree != null) {
                this.tree = node.tree;
                break;
            } else {
                node = node.parent;
            }
        }
        this.parent = parent;
        this.children.addListener(this::collectionChanged);
    }

    private void collectionChanged(BaseObservableCollection.CollectionChanged<SWTreeNode<T>> changed) {
        if (changed.getAction() == BaseObservableCollection.CollectionAction.Remove) {
            refresh();
        } else if (changed.getAction() == BaseObservableCollection.CollectionAction.Add) {
            refresh();
        } else if (changed.getAction() == BaseObservableCollection.CollectionAction.Set) {
            List<SWTreeNode<T>> changedItem = changed.getItem();
            if (changedItem != null) {
                for (SWTreeNode<T> node: changedItem) {
                    node.refresh();
                }
            }
        }
    }

    public void setLabelFactory(Function<T, String> labelFactory) {
        this.labelFactory = labelFactory;
    }

    public void refresh(){
        Tree tree = this.tree.getWidget();
        if (tree == null) {
            return;
        }

        if (this.item == null) {
            if ( parent != null && parent.item != null) {
                item = new TreeItem(parent.item, flag);
            } else {
                item = new TreeItem(tree,flag);
            }
        }

        item.setText(labelFactory.apply(value));

        for (SWTreeNode<T> node : children) {
            node.refresh();
        }
    }

    public void remove() {
        if (item != null) {
            item.dispose();
        }
        for (SWTreeNode<T> node: children) {
            node.remove();
        }
        if (parent != null) {
            parent.getChildren().remove(this);
        }
    }


    public ObservableArrayList<SWTreeNode<T>> getChildren() {
        return children;
    }

    public void clean() {
        children.clear();
    }

    public void labelFactory(Function<T, String> labelFactory) {
        this.labelFactory = labelFactory;
    }
}
