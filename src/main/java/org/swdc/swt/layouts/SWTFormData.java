package org.swdc.swt.layouts;

import org.eclipse.swt.layout.FormData;
import org.swdc.swt.widgets.SWTWidget;

public class SWTFormData implements LayoutData {

    private FormData data = new FormData();

    private SWTFormPlaceHolder left;
    private SWTFormPlaceHolder right;
    private SWTFormPlaceHolder top;
    private SWTFormPlaceHolder bottom;

    public SWTFormPlaceHolder left() {
        left = new SWTFormPlaceHolder(this);
        return left;
    }

    public SWTFormPlaceHolder right() {
        right = new SWTFormPlaceHolder(this);
        return right;
    }

    public SWTFormPlaceHolder top() {
        top = new SWTFormPlaceHolder(this);
        return top;
    }

    public SWTFormPlaceHolder bottom(){
        bottom = new SWTFormPlaceHolder(this);
        return bottom;
    }

    public FormData get(SWTWidget widget) {
        if (this.left != null) {
            data.left = this.left.attachment(widget);
        }
        if (this.right != null) {
            data.right = this.right.attachment(widget);
        }
        if (this.top != null) {
            data.top = this.top.attachment(widget);
        }
        if (this.bottom != null) {
            data.bottom = this.bottom.attachment(widget);
        }
        return data;
    }

    @Override
    public Object get() {
        throw new RuntimeException("方法无效");
    }
}
