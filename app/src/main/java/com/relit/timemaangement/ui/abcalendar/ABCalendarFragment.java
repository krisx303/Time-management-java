package com.relit.timemaangement.ui.abcalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.relit.timemaangement.R;
import com.relit.timemaangement.domain.abcalendar.CalendarDataAB;

public class ABCalendarFragment {

    private final View root;

    public ABCalendarFragment(Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.fragment_calendarab, null);
    }

    public View getRoot() {
        return root;
    }

    public CalendarABView getABCalendarView(){
        return root.findViewById(R.id.calendar_view);
    }

    public void setTitle(String hello) {
        ((TextView) root.findViewById(R.id.month_title)).setText(hello);
    }

    public void setMonthData(CalendarDataAB monthData, int id, boolean isLast) {
        getABCalendarView().setMonthData(monthData, id, isLast);
    }
}