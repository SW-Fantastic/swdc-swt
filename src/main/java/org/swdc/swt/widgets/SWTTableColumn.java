package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;

public class SWTTableColumn extends SWTWidget<TableColumn> {

    interface ColumnFactory<T> {
        String getValue(T item);
    }

    private int flags;

    private TextProperty text = new TextProperty();

    private SizeProperty sizeProperty = new SizeProperty();

    private TableColumn tableColumn;

    private ColumnFactory factory;

    public SWTTableColumn(int flags, String text) {
        this.text.set(text);
        this.flags = flags;
    }

    public SWTTableColumn text(String text) {
        this.text.set(text);
        return this;
    }

    public SWTTableColumn width(int width) {
        this.sizeProperty.width(width);
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
            this.text.manage(tableColumn);
            this.sizeProperty.manage(tableColumn);
        }
        return tableColumn;
    }

    public static SWTTableColumn tableColumn(int flags, String text) {
        return new SWTTableColumn(flags,text);
    }

}
