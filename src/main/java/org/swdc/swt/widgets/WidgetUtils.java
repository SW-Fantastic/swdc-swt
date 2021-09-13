package org.swdc.swt.widgets;

import java.util.Locale;

public class WidgetUtils {

    public static SWTColor colorHex(String colorStr){
        int[] rgb = hex2RGB(colorStr);
        if (rgb == null || rgb.length < 3) {
            return null;
        }
        return new SWTColor(rgb[0],rgb[1],rgb[2]);
    }

    public static SWTColor color(String color) {
        try {
            if (color.startsWith("#")) {
                return colorHex(color);
            } else if (color.toLowerCase().startsWith("rgb")) {

                color = color.toLowerCase();
                color = color.substring(color.indexOf("rgb(") + 1, color.indexOf(")") + 1);
                String[] rgb = color.split(",");

                if (rgb.length < 3) {
                    return null;
                }

                int r = Integer.parseInt(rgb[0]);
                int g = Integer.parseInt(rgb[1]);
                int b = Integer.parseInt(rgb[2]);

                return new SWTColor(r,g,b);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 16进制颜色字符串转换成rgb
     * @param hexStr
     * @return rgb
     */
    public static int[] hex2RGB(String hexStr){
        if(hexStr != null && !"".equals(hexStr) && hexStr.length() == 7){
            int[] rgb = new int[3];
            rgb[0] = Integer.valueOf(hexStr.substring( 1, 3 ), 16);
            rgb[1] = Integer.valueOf(hexStr.substring( 3, 5 ), 16);
            rgb[2] = Integer.valueOf(hexStr.substring( 5, 7 ), 16);
            return rgb;
        } else if (hexStr != null && !"".equals(hexStr) && hexStr.length() == 4) {
            int[] rgb = new int[3];
            rgb[0] = Integer.valueOf(hexStr.substring( 1, 2 ) + hexStr.substring( 1, 2 ), 16);
            rgb[1] = Integer.valueOf(hexStr.substring( 2, 3 ) + hexStr.substring( 2, 3 ), 16);
            rgb[2] = Integer.valueOf(hexStr.substring( 3, 4 ) + hexStr.substring( 3, 4 ), 16);
            return rgb;
        }
        return null;
    }


}
