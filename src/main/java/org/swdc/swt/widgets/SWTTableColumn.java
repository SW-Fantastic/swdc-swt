package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class SWTTableColumn extends SWTWidget<TableColumn> {

    interface ColumnFactory<T> {
        String getValue(T item);
    }

    private int flags;

    private String text;

    private int width;

    private TableColumn tableColumn;

    private ColumnFactory factory;

    public SWTTableColumn(int flags, String text) {
        this.text = text;
        this.flags = flags;
    }

    public void text(String text) {
        this.text = text;
        if (this.tableColumn != null) {
            this.tableColumn.setText(text);
        }
    }

    public SWTTableColumn width(int width) {
        this.width = width;
        if (this.tableColumn != null) {
            this.tableColumn.setWidth(width);
        }
        return this;
    }


    public SWTTableColumn factory(ColumnFactory factory) {
        this.factory = factory;
        return this;
    }

    public ColumnFactory getFactory() {
        return factory;
    }

    @Override
    public TableColumn getWidget(Composite parent) {
        if (this.tableColumn == null && parent != null) {
            if (!(parent instanceof Table)) {
                throw new RuntimeException("Table column 需要在table中创建。");
            }

            tableColumn = new TableColumn((Table)parent,this.flags);
            tableColumn.setWidth(this.width);
            if(this.text != null) {
                tableColumn.setText(this.text);
            }
        }
        return tableColumn;
    }

    public static SWTTableColumn tableColumn(int flags, String text) {
        return new SWTTableColumn(flags,text);
    }

}
