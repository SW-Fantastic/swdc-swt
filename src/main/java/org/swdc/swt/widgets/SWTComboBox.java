package org.swdc.swt.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.swdc.swt.beans.ObservableArrayList;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.beans.TextSelectionProperty;
import org.swdc.swt.widgets.base.Controlable;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

import java.util.*;
import java.util.function.Function;

public class SWTComboBox extends SWTLabelControlWidget<Combo> implements Controlable {

    private int flag;

    private Combo combo;

    private ObservableArrayList<Object> itemList = new ObservableArrayList<>();

    private Map<String, Object> itemsMap = new HashMap<>();
    private Function<Object, String> factory;

    private ObservableValue<String> selected = new ObservableValue<>();

    private TextSelectionProperty textSelectionProperty = new TextSelectionProperty();

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
    public void ready() {
        super.ready();
        if (combo != null) {
            SWTWidgets.setupLayoutData(this,this.combo);
            if (this.itemList.size() > 0 ){
                List<Object> data = new ArrayList<>(itemList);
                this.data(data);
            }
            this.selected.addListener(this::onSelectionChange);
            this.onSelectionChange(null,null);

            this.textSelectionProperty.manage(combo);

        }
    }


    public void factory(Function<Object,String> factory) {
        this.factory = factory;
    }

    @Override
    protected Combo getWidget(Composite parent) {
        if (this.combo == null && parent != null) {
            this.combo = new Combo(parent,this.flag);

        }
        return combo;
    }

    public ObservableArrayList<Object> getItems() {
        return itemList;
    }

    public static SWTComboBox comboBox(int flag) {
        return new SWTComboBox(flag);
    }

}
