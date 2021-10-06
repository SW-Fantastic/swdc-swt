package org.swdc.swt.beans;

public interface CollectionChangeListener<T> {

    void changed(BaseObservableCollection.CollectionChanged<T> changed);

}
