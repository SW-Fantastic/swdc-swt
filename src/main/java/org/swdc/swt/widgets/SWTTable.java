package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.beans.ObservableArrayList;
import org.swdc.swt.widgets.base.SWTControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

import java.util.*;
import java.util.List;

public class SWTTable extends SWTControlWidget<Table> implements SWTContainer, Selectionable {

    private ControlListener autoColumnSizeListener = new ControlAdapter() {
        @Override
        public void controlResized(ControlEvent controlEvent) {
            TableColumn[] columns = table.getColumns();
            for (TableColumn column: columns) {
                column.setWidth(table.getSize().x / columns.length);
            }
        }
    };

    private boolean fixColumnWidth;

    private Table table;

    private SWTWidget<TableColumn> columns;

    private int flags;

    private boolean showHeader = true;

    private boolean lines;

    private ObservableArrayList<Object> items = new ObservableArrayList<>();

    private Map<TableItem, Map<SWTTableColumn, TableEditor>> editors = new HashMap<>();

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTTable(int flags) {
        this.flags = flags;
    }

    @Override
    public void initWidget(Table created) {

        if (table == null) {
            return;
        }
        super.initWidget(table);

        this.table.setHeaderVisible(showHeader);
        this.table.setLinesVisible(lines);
        if (this.columns != null) {
            SWTTableColumn item =(SWTTableColumn) columns.getFirst();
            while (item != null) {
                item.getWidget(table);
                item = (SWTTableColumn)item.getNext();
            }
        }

        if (this.items != null && this.items.size() > 0 ) {
            this.refresh();
        }

        if (fixColumnWidth) {
            this.table.addControlListener(autoColumnSizeListener);
        }

        selectionProperty.manage(this);
        this.table.addSelectionListener(selectionProperty.dispatcher());

        SWTWidgets.setupLayoutData(this,this.table);
        this.items.addListener(changed -> {
            this.refresh();
        });

    }

    @Override
    public Table getWidget(Composite parent) {
        if (this.table == null && parent != null) {
            if (SWTWidgets.isFormAPI(parent)) {
                FormToolkit toolkit = SWTWidgets.factory();
                this.table = toolkit.createTable(parent,this.flags);
                toolkit.paintBordersFor(parent);
            } else {
                this.table = new Table(parent,this.flags);
            }
            this.initWidget(table);
        }
        return table;
    }

    public SWTTable autoColumnWidth(boolean fix) {
        this.fixColumnWidth = fix;
        if (table == null) {
            return this;
        }
        if (fix) {
            table.addControlListener(autoColumnSizeListener);
        } else {
            table.removeControlListener(autoColumnSizeListener);
        }
        return this;
    }

    public SWTTable refresh(){
        if (this.table != null) {
            for (TableItem obj: table.getItems()){
                obj.dispose();
            }
            this.table.clearAll();
            for (Object item: this.items) {
                TableItem cell = new TableItem(this.table,SWT.NORMAL);
                SWTTableColumn column = (SWTTableColumn) this.columns;

                int columnIndex = 0;
                while (column != null) {
                    SWTTableColumn.ColumnFactory factory = column.getFactory();
                    if (factory == null) {
                        cell.setText(columnIndex,"");
                    } else {
                        cell.setText(columnIndex,factory.getValue(item));
                    }
                    SWTTableColumn.ColumnEditorFactory editorFactory = column.getEditorFactory();
                    if (editorFactory != null) {
                        Map<SWTTableColumn,TableEditor> colEditorMap = editors
                                .computeIfAbsent(cell, t -> new HashMap<>());
                        if (colEditorMap.containsKey(column)) {
                            TableEditor editor = colEditorMap.remove(column);
                            editor.getEditor()
                                    .dispose();
                        }
                        SWTWidget swtCtrl = editorFactory.create(item);

                        Control widget = (Control) swtCtrl.getWidget(table);
                        widget.computeSize(SWT.DEFAULT,table.getItemHeight());

                        TableEditor editor = new TableEditor(table);
                        editor.grabHorizontal = true;
                        editor.grabVertical = true;
                        editor.setEditor(widget,cell,columnIndex);

                        colEditorMap.put(column,editor);
                    }
                    column = (SWTTableColumn) column.getNext();
                    columnIndex ++;
                }
            }

            if (this.getLayoutData() != null) {
                table.setLayoutData(this.getLayoutData().get());
            }

        }
        return this;
    }

    public SWTTable data(List<Object> data) {
        if (data != null) {
            this.items.clear();
            this.items.addAll(data);
        }
        return refresh();
    }

    @Override
    public void children(SWTWidget widget) {
        this.columns = widget;
        if (this.table != null) {
            SWTWidget item = columns.getFirst();
            while (item != null) {
                item.getWidget(this.table);
                item = item.getNext();
            }
        }
    }

    public SWTTable lines(boolean lines) {
        this.lines = lines;
        if (table != null) {
            this.table.setLinesVisible(lines);
        }
        return this;
    }

    public SWTTable headers(boolean show) {
        this.showHeader = show;
        if (table != null) {
            table.setHeaderVisible(show);
        }
        return this;
    }

    public ObservableArrayList<Object> getItems() {
        return items;
    }


    @Override
    public void onAction(String methodName) {
        this.selectionProperty.setSelectionMethod(methodName);
    }

    @Override
    public void onAction(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        selectionProperty.closure(closure);
    }

    public static SWTTable table(int flags) {
        return new SWTTable(flags);
    }

}
