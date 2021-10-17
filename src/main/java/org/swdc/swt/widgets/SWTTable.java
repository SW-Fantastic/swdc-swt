package org.swdc.swt.widgets;

import groovy.lang.Closure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.swdc.swt.actions.SelectionProperty;
import org.swdc.swt.beans.ObservableArrayList;
import org.swdc.swt.widgets.base.SWTControlWidget;
import org.swdc.swt.widgets.base.Selectionable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    private SelectionProperty selectionProperty = new SelectionProperty();

    public SWTTable(int flags) {
        this.flags = flags;
    }

    @Override
    public void ready() {

        super.ready();

        if (table == null) {
            return;
        }

        this.table.setHeaderVisible(showHeader);
        this.table.setLinesVisible(lines);
        if (this.columns != null) {
            SWTWidget item = columns.getFirst();
            while (item != null) {
                item.create(this.table,this);
                item = item.getNext();
            }
        }

        if (this.items != null && this.items.size() > 0 ) {
            List<Object> copied = new ArrayList<>(this.items);
            this.data(copied);
        }

        if (fixColumnWidth) {
            this.table.addControlListener(autoColumnSizeListener);
        }

        selectionProperty.manage(this);
        this.table.addSelectionListener(selectionProperty.dispatcher());

        SWTWidgets.setupLayoutData(this,this.table);

    }

    @Override
    protected Table getWidget(Composite parent) {
        if (this.table == null && parent != null) {
            if (SWTWidgets.isFormAPI(parent)) {
                FormToolkit toolkit = SWTWidgets.factory();
                this.table = toolkit.createTable(parent,this.flags);
                toolkit.paintBordersFor(parent);
            } else {
                this.table = new Table(parent,this.flags);
            }
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

    public SWTTable data(List<Object> data) {
        this.items.clear();
        this.items.addAll(data);
        if (this.table != null) {
            for (TableItem obj: table.getItems()){
                obj.dispose();
            }
            this.table.clearAll();

            for (Object item: data) {
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

    public void setItems(ObservableArrayList<Object> items) {
        this.items = items;
        this.items = items;
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
