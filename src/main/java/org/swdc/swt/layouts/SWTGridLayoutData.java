package org.swdc.swt.layouts;

import org.eclipse.swt.layout.GridData;
import org.swdc.swt.Modifiable;

public class SWTGridLayoutData implements LayoutData{

    private GridData data;

    public SWTGridLayoutData () {
        data = new GridData();
    }

    public SWTGridLayoutData width(int width) {
        data.widthHint = width;
        return this;
    }

    public SWTGridLayoutData height(int height) {
        data.heightHint = height;
        return this;
    }



    public SWTGridLayoutData fillWidth(boolean val) {
        data.grabExcessHorizontalSpace = val;
        return this;
    }

    public SWTGridLayoutData fillHeight(boolean val) {
        data.grabExcessVerticalSpace = val;
        return this;
    }

    public SWTGridLayoutData colSpan(int span ) {
        this.data.horizontalSpan = span;
        return this;
    }

    public SWTGridLayoutData rowSpan(int span) {
        this.data.verticalSpan = span;
        return this;
    }

    public SWTGridLayoutData spans(int w, int h) {
        this.data.verticalSpan = h;
        this.data.horizontalSpan = w;
        return this;
    }

    public SWTGridLayoutData rowAlignment(int alignment) {
        this.data.horizontalAlignment = alignment;
        return this;
    }

    public SWTGridLayoutData columnAlignment(int alignment) {
        this.data.verticalAlignment = alignment;
        return this;
    }

    @Override
    public GridData get(){
        return data;
    }

}
