package org.swdc.swt;

import groovy.lang.Closure;

public interface Modifiable<T> {

    /**
     * groovy的界面文件使用，修改组件自身属性，调用组件方法等
     * @param closure
     * @return
     */
    default T define(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call(this);
        return (T) this;
    }

}
