package com.relit.timemaangement.util;

import android.graphics.Color;

public class Helper {
    public static int getContrastColor(int color) {
        double luminance = (0.2126 * Color.red(color) + 0.7152 * Color.green(color) + 0.0722 * Color.blue(color)) / 255;
        return luminance > 0.5 ? Color.BLACK : Color.WHITE;
    }
}
