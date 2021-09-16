package org.swdc.swt.layouts;

import org.eclipse.swt.layout.RowData;

public class SWTRowData implements LayoutData {

    private RowData rowData = new RowData();

    public SWTRowData size(int width, int height) {
        rowData.height = height;
        rowData.width = width;
        return this;
    }

    @Override
    public Object get() {
        return rowData;
    }
}
