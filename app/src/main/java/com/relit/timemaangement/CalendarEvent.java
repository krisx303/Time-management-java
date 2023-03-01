package com.relit.timemaangement;

import com.relit.timemaangement.util.Hour;

import java.util.Date;

public class CalendarEvent {
    int day;
    private Hour startHour;
    private Hour duration;

    public CalendarEvent(int day, Hour startHour, Hour duration) {
        this.day = day;
        this.startHour = startHour;
        this.duration = duration;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Hour getStartHour() {
        return startHour;
    }

    public void setStartHour(Hour startHour) {
        this.startHour = startHour;
    }

    public Hour getDuration() {
        return duration;
    }

    public void setDuration(Hour duration) {
        this.duration = duration;
    }
}
