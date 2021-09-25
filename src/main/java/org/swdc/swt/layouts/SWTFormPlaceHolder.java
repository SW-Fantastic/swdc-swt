package org.swdc.swt.layouts;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Control;
import org.swdc.swt.widgets.SWTWidget;

public class SWTFormPlaceHolder {

    private String controlId;

    private int numerator;

    private int offset;

    private int denominator;

    private int alignment;

    private SWTFormData data;

    public SWTFormPlaceHolder(SWTFormData data) {
        this.data = data;
    }

    public SWTFormData getData() {
        return data;
    }

    public SWTFormPlaceHolder id(String controlId) {
        this.controlId = controlId;
        return this;
    }

    public SWTFormPlaceHolder denominator(int denominator) {
        this.denominator = denominator;
        return this;
    }

    public SWTFormPlaceHolder numerator(int numerator) {
        this.numerator = numerator;
        return this;
    }

    public SWTFormPlaceHolder offset(int offset) {
        this.offset = offset;
        return this;
    }

    public SWTFormPlaceHolder alignment(int alignment) {
        this.alignment = alignment;
        return this;
    }

    public int getAlignment() {
        return alignment;
    }

    public FormAttachment attachment(SWTWidget widget) {
        FormAttachment attachment = new FormAttachment();
        if (this.controlId != null) {
            Control control = (Control) widget.findById(controlId);
            if (control == null) {
                throw new RuntimeException("指定的组件不存在：" + controlId);
            }
            attachment.control = control;
        }
        attachment.numerator = this.numerator;
        attachment.denominator = this.denominator;
        attachment.offset = this.offset;
        attachment.alignment = this.alignment;
        return attachment;
    }

}
