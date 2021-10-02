package org.swdc.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.swdc.swt.widgets.base.SWTLabelWidget;

public class SWTTableColumn extends SWTLabelWidget<TableColumn> {

    interface ColumnFactory<T> {
        String getValue(T item);
    }

    private int flags;

    private TableColumn tableColumn;

    private ColumnFactory factory;

    public SWTTableColumn(int flags) {
        this.flags = flags;
    }

    public SWTTableColumn width(int width) {
        this.sizeProperty().width(width);
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
    protected TableColumn getWidget(Composite parent) {
        if (this.tableColumn == null && parent != null) {
            if (!(parent instanceof Table)) {
                throw new RuntimeException("Table column 需要在table中创建。");
            }

            tableColumn = new TableColumn((Table)parent,this.flags);
        }
        return tableColumn;
    }

    public static SWTTableColumn tableColumn(int flags) {
        return new SWTTableColumn(flags);
    }

}
