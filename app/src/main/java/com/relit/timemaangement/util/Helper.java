package com.relit.timemaangement.util;

import android.app.Activity;
import android.graphics.Color;

import androidx.annotation.Nullable;

import com.maltaisn.icondialog.data.Icon;
import com.relit.timemaangement.TimeManagement;

import java.util.Calendar;
import java.util.Map;

public class Helper {
    public static int getContrastColor(int color) {
        double luminance = (0.2126 * Color.red(color) + 0.7152 * Color.green(color) + 0.0722 * Color.blue(color)) / 255;
        return luminance > 0.5 ? Color.BLACK : Color.WHITE;
    }

    public static Hour getCurrentHour(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return new Hour(hour, minutes);
    }

    @Nullable
    public static Map<Integer, Icon> getIcons(Activity activity) {
        return ((TimeManagement) activity.getApplication()).getIconPack().getIcons();
    }
}
