package org.swdc.swt.views;

import org.swdc.dependency.DependencyContext;
import org.swdc.dependency.DependencyScope;
import org.swdc.swt.ViewController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ViewManager implements DependencyScope {

    private DependencyContext context;

    private Map<Class,List<View>> viewClassMapped = new ConcurrentHashMap<>();
    private Map<String,View> viewNameMapped = new ConcurrentHashMap<>();
    private Map<Class,List<View>> abstractViewMapped = new ConcurrentHashMap<>();

    @Override
    public <T> T getByClass(Class<T> clazz) {
        List<View> views = viewClassMapped.get(clazz);
        if (views == null) {
            return null;
        }
        if (views.size() > 0) {
            throw new RuntimeException("存在多个相同的view，请使用其他方法获取。");
        }
        return (T)viewClassMapped.get(clazz).get(0);
    }

    @Override
    public <T> T getByName(String name) {
        if (viewNameMapped.containsKey(name)) {
            return (T)viewNameMapped.get(name);
        }
        return null;
    }

    @Override
    public <T> List<T> getByAbstract(Class<T> parent) {
        return (List<T>) abstractViewMapped.get(parent);
    }

    @Override
    public List<Object> getAllComponent() {
        return viewClassMapped
                .values()
                .stream().flatMap(l -> l.stream())
                .collect(Collectors.toList());
    }

    @Override
    public Class getScopeType() {
        return SWTView.class;
    }

    @Override
    public <T> T put(String name, Class clazz, Class multiple, T component) {

        View view = (View) component;
        view.factory(v -> {
            ViewController controller =(ViewController) v.getAnnotation(ViewController.class);
            if (controller == null) {
                return null;
            }
            Class controllerClazz = controller.value();
            return context.getByClass(controllerClazz);
        });
        view.loadView();

        List<View> views = viewClassMapped.get(clazz);
        if (views == null) {
            views = new ArrayList<>();
        }
        views.add(view);
        viewClassMapped.put(clazz,views);

        if (!name.equals(clazz.getName())) {
            viewNameMapped.put(name,view);
        }

        if (multiple != null) {
            List<View> absList = abstractViewMapped.get(multiple);
            if (absList == null) {
                absList = new ArrayList<>();
            }
            absList.add((View) component);
            abstractViewMapped.put(multiple,absList);
        }

        return component;
    }

    @Override
    public <T> T put(String name, Class clazz, T component) {
        return put(name,clazz,null,component);
    }

    @Override
    public void setContext(DependencyContext context) {
        this.context = context;
    }

}
