package org.swdc.swt.beans;

public interface ObservableCollection<T> {

    void bind(ObservableCollection<T> collection);

    void bindBidrect(ObservableCollection<T> collection);

    void addListener(CollectionChangeListener<T> listener);

    void removeListener(CollectionChangeListener<T> listener);

}
