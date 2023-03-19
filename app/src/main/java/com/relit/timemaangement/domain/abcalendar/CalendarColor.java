package com.relit.timemaangement.domain.abcalendar;

import android.graphics.Color;

public enum CalendarColor{
    WEEK_DAY_NONE("#123123", 'N'),
    WEEK_DAY_A("#0a2a4a", 'A'),
    WEEK_DAY_B("#153d0c", 'B'),
    FREE_DAY("#7a3f00", 'F'),
    WEEK_DAY_UNAVAILABLE("#262626", 'U');

    public final int color;
    public final char letter;

    CalendarColor(String color, char letter) {
        this.color = Color.parseColor(color);
        this.letter = letter;
    }

    public boolean isValidBrushColor(){
        return this != WEEK_DAY_NONE && this != WEEK_DAY_UNAVAILABLE ;
    }
}