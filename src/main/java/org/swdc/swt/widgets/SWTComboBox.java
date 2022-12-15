package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.beans.ObservableArrayList;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.TextSelectionProperty;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

import java.util.*;
import java.util.function.Function;

public class SWTComboBox extends SWTLabelControlWidget<Combo> implements Selectionable {

    private int flag;

    private Combo combo;

    private ObservableArrayList<Object> itemList = new ObservableArrayList<>();

    private Map<String, Object> itemsMap = new HashMap<>();
    private Function<Object, String> factory;

    private ObservableValue<String> selected = new ObservableValue<>();

    private TextSelectionProperty textSelectionProperty = new TextSelectionProperty();

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTComboBox(int flag) {
        this.flag = flag;
    }

    public void data(Collection data) {
        this.itemList.clear();
        this.itemList.addAll(data);

        if (this.combo != null) {
            this.combo.removeAll();
            for (Object elem: itemList) {
                int idx = itemList.indexOf(elem);
                String text = factory.apply(elem);
                combo.add(text,idx);
                itemsMap.put(text,elem);
            }
        }
    }

    @Override
    public Combo getWidget() {
        return combo;
    }

    public <T> T selected() {
        String text = text();
        return itemsMap.containsKey(text) ? (T)itemsMap.get(text) : null;
    }

    private void onSelectionChange(String valOld, String valNew){
        if (itemsMap.containsKey(selected.get())) {
            Object obj = itemsMap.get(selected.get());
            text(factory.apply(obj));
        }
    }

    public void select(String val) {
        selected.set(val);

    }

    public void copy() {
        if (combo == null) {
            return;
        }
        combo.copy();
    }

    public void paste() {
        if (this.combo == null) {
            return;
        }
        combo.paste();
    }

    public void cut(){
        if (this.combo == null) {
            return;
        }
        combo.copy();
    }

    public void textSelection(int start, int end) {
        textSelectionProperty.set(new Point(start,end));
    }

    public Point textSelection() {
        return textSelectionProperty.get();
    }

    public String selectedText() {
        Point selection = textSelectionProperty.get();
        return combo.getText().substring(selection.x,selection.y);
    }

    @Override
    public void initWidget(Combo created) {
        if (combo != null) {
            super.initWidget(combo);
            SWTWidgets.setupLayoutData(this,this.combo);
            if (this.itemList.size() > 0 ){
                List<Object> data = new ArrayList<>(itemList);
                this.data(data);
            }
            this.selected.addListener(this::onSelectionChange);
            this.onSelectionChange(null,null);

            this.textSelectionProperty.manage(combo);

            selectionProperty.manage(this);
            combo.addSelectionListener(selectionProperty.dispatcher());
        }
    }


    public void factory(Function<Object,String> factory) {
        this.factory = factory;
    }

    @Override
    public Combo getWidget(Composite parent) {
        if (this.combo == null && parent != null) {
            this.combo = new Combo(parent,this.flag);
            initWidget(combo);
        }
        return combo;
    }

    public ObservableArrayList<Object> getItems() {
        return itemList;
    }

    public static SWTComboBox comboBox(int flag) {
        return new SWTComboBox(flag);
    }

    @Override
    public void onAction(String methodName) {
        selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.setDelegate(this);
        selectionProperty.closure(closure);
    }
}
