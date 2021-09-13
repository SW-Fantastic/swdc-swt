package org.swdc.swt.widgets;

import org.eclipse.swt.graphics.Color;

public class SWTColor {

    private Color color;

    public SWTColor(int r, int g, int b) {
        this.color = new Color(null,r,g,b);
    }

    public Color getColor() {
        return color;
    }
}
