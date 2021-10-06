package org.swdc.swt.beans;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ObservableValue<T> {

    private T val = null;

    private ObservableEmitter<T> emitter;
    private Observable<T> reactive = Observable
            .create((ObservableEmitter<T> em) -> this.emitter = em)
            .publish()
            .autoConnect();

    private List<ChangeListener<T>> listeners = new ArrayList<>();

    private Disposable disposable;

    private Observer<T> observer = new Observer<>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            disposable = d;
        }
        @Override
        public void onNext(@NonNull T s) {
            if (objectEquals(val,s)){
                return;
            }
            set(s);
        }
        @Override
        public void onError(@NonNull Throwable e) {
        }
        @Override
        public void onComplete() {
        }
    };

    public ObservableValue() {

    }

    public ObservableValue(T init) {
        this.val = init;
    }

    public boolean isEmpty() {
        return val == null;
    }

    public void addListener(ChangeListener<T> listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ChangeListener<T> listener) {
        this.listeners.remove(listener);
    }

    /**
     * 重写此方法判断对象是否一致。
     * @param self 当前的对象
     * @param another Observable出现了变化，改变后的新对象。
     * @return 返回True，则更新ObservableValue。
     */
    protected boolean doEquals(T self, T another) {
        return  self.equals(another);
    }

    public boolean objectEquals(T self,T another) {
        if (self == null && another != null) {
            return false;
        } else if (another == null && self != null) {
            return false;
        } else if (another == null) {
            return true;
        }
        return doEquals(self,another);
    }

    public void bind(ObservableValue<T> source){
        source.reactive.subscribe(observer);
    }

    public void bindBidirect(ObservableValue<T> target){
        target.reactive.subscribe(observer);
        this.reactive.subscribe(target.observer);
    }

    public void unBind() {
        if (this.disposable == null) {
            return;
        }
        this.disposable.dispose();
        this.disposable = null;
    }

    public void set(T s) {
        T old = val;
        this.val = s;
        if (emitter != null ) {
            this.emitter.onNext(s);
        }
        if (listeners.size() > 0) {
            for (ChangeListener<T> listener : listeners) {
                listener.accept(old,s);
            }
        }
    }

    public void setWithoutListener(T s) {
        T old = val;
        this.val = s;
        if (emitter != null ) {
            this.emitter.onNext(s);
        }
    }

    public T get() {
        return this.val;
    }

}
