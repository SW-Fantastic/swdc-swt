package org.swdc.swt.beans;

public interface ChangeListener<T> {

    void accept(T oldVal, T newVal);

}
