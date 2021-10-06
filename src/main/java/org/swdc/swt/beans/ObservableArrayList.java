package org.swdc.swt.beans;


import java.util.*;
import java.util.stream.Collectors;

public class ObservableArrayList<T> extends BaseObservableCollection<T> implements List<T> {

    private ArrayList<T> list = new ArrayList<>();


    public ObservableArrayList() {
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {

        if (emitter  != null) {
            CollectionChanged<T> changed = new CollectionChanged<T>(-1,Arrays.asList(t),CollectionAction.Add);
            emitter.onNext(changed);
        }

        return list.add(t);

    }

    @Override
    public boolean remove(Object o) {

        if (emitter != null) {
            CollectionChanged<T> changed = new CollectionChanged<T>(-1,Arrays.asList((T)o),CollectionAction.Add);
            emitter.onNext(changed);
        }

        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {

        if (emitter != null) {
            CollectionChanged<T> changed = new CollectionChanged<T>(-1,c,CollectionAction.Add);
            emitter.onNext(changed);
        }

        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index,c);
    }

    @Override
    public boolean removeAll(Collection c) {

        if (emitter != null) {
            CollectionChanged<T> changed = new CollectionChanged<T>(-1,c,CollectionAction.Remove);
            emitter.onNext(changed);

        }

        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {

        if (emitter != null) {
            CollectionChanged<T> changed = new CollectionChanged<T>(-1,c,CollectionAction.Retain);
            emitter.onNext(changed);

        }

        return list.retainAll(c);
    }

    @Override
    public void clear() {

        if (emitter != null) {
            CollectionChanged<T> changed = new CollectionChanged<T>(-1,list,CollectionAction.Remove);
            emitter.onNext(changed);
        }


        list.clear();

    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    public T set(int idx, T elem) {
        if (emitter != null) {
            CollectionChanged<T> changed = new CollectionChanged<T>(idx,Arrays.asList(elem),CollectionAction.Set);
            emitter.onNext(changed);
        }

        return list.set(idx,elem);
    }

    public void add(int idx, T elem) {
        if (emitter != null) {
            CollectionChanged<T> changed = new CollectionChanged<T>(idx,Arrays.asList(elem),CollectionAction.Add);
            emitter.onNext(changed);
        }
        list.add(idx,elem);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex,toIndex);
    }

    @Override
    protected void onCollectionChanged(CollectionChanged<T> item) {
        switch (item.getAction()) {
            case Remove: {
                List<T> data = item.getItem();
                List<T> remove = data.stream()
                        .filter(this::contains)
                        .collect(Collectors.toList());

                this.removeAll(remove);


                break;
            }

            case Add: {
                List<T> data = item.getItem();
                List<T> added = data
                        .stream()
                        .filter(i -> !this.contains(i))
                        .collect(Collectors.toList());
                this.addAll(added);
                break;
            }

            case Retain: {
                this.retainAll(item.getItem());
                break;
            }

            case Set: {
                this.list.set(item.getIndex(),item.getItem().get(0));
                break;
            }
        }

        if (this.listeners.size() > 0) {
            for (CollectionChangeListener<T> listener : listeners) {
                listener.changed(item);
            }
        }
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
