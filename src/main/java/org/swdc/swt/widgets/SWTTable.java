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

import java.util.ArrayList;
import java.util.List;

public class SWTTable extends SWTWidget<Table> implements SWTContainer {

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

    private int width;

    private int height;

    private boolean lines;

    private List<Object> items = new ArrayList<>();

    public SWTTable(int flags) {
        this.flags = flags;
    }

    @Override
    public void ready(Stage stage) {
        this.table.setHeaderVisible(showHeader);
        this.table.setSize(width,height);
        this.table.setLinesVisible(lines);
        if (this.columns != null) {
            SWTWidget item = columns.getFirst();
            while (item != null) {
                item.getWidget(this.table);
                item.setStage(stage);
                item.ready(stage);
                item = item.getNext();
            }
        }

        if (this.items != null && this.items.size() > 0 ) {
            this.data(this.items);
        }

        if (fixColumnWidth) {
            this.table.addControlListener(autoColumnSizeListener);
        }

    }

    @Override
    public Table getWidget(Composite parent) {
        if (this.table == null && parent != null) {
            this.table = new Table(parent,this.flags);

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
        this.items = data;
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

    public SWTTable size(int width, int height) {
        this.width = width;
        this.height = height;
        if (this.table != null) {
            table.setSize(width,height);
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

    public static SWTTable table(int flags) {
        return new SWTTable(flags);
    }
}
