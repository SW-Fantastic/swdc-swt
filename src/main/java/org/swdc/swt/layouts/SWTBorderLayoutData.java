package org.swdc.swt.layouts;

public class SWTBorderLayoutData implements LayoutData{

    private BorderLayoutData data;

    public SWTBorderLayoutData(BorderLayoutData data) {
        this.data = data;
    }

    @Override
    public Object get() {
        return data;
    }
}
