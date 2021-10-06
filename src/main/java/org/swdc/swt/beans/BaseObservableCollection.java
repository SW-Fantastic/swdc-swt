package org.swdc.swt.beans;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseObservableCollection<T> implements Collection<T>,ObservableCollection<T> {

    protected Disposable disposable;

    protected ObservableEmitter<CollectionChanged<T>> emitter;

    protected enum CollectionAction {
        Add,
        Remove,
        Retain,
        Set
    }

    public static class CollectionChanged<T> {

        private CollectionAction action;
        private List<T> items;
        private int index;

        protected CollectionChanged(int index , Collection<? extends T> item, CollectionAction action) {
            this.items = item.stream()
                    .map(i -> (T)i)
                    .collect(Collectors.toList());
            this.action = action;
            this.index = index;
        }

        public CollectionAction getAction() {
            return action;
        }

        public List<T> getItem() {
            return items;
        }

        public int getIndex() {
            return index;
        }
    }

    protected List<CollectionChangeListener<T>> listeners = new ArrayList<>();

    protected Observable<CollectionChanged<T>> reactive = Observable
            .create((ObservableEmitter<CollectionChanged<T>> emitter) ->{
                this.emitter = emitter;
            })
            .publish()
            .autoConnect();

    private Observer<CollectionChanged<T>> observer = new Observer<CollectionChanged<T>>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(@NonNull CollectionChanged<T> changed) {
            onCollectionChanged(changed);
        }

        @Override
        public void onError(@NonNull Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    protected abstract void onCollectionChanged(CollectionChanged<T> item);

    @Override
    public void bind(ObservableCollection<T> collection) {
        BaseObservableCollection<T>  baseCollection = (BaseObservableCollection<T>) collection;
        baseCollection.reactive.subscribe(this.observer);
    }

    @Override
    public void bindBidrect(ObservableCollection<T> collection) {
        BaseObservableCollection<T>  baseCollection = (BaseObservableCollection<T>) collection;
        baseCollection.reactive.subscribe(this.observer);
        this.reactive.subscribe(baseCollection.observer);
    }


    @Override
    public void addListener(CollectionChangeListener<T> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(CollectionChangeListener<T> listener) {
        this.listeners.add(listener);
    }

}
