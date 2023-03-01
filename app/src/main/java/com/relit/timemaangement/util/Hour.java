package com.relit.timemaangement.util;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

public class Hour {
    public int hour;
    public int minutes;

    public Hour(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public void addInterval(Hour interval){
        this.minutes += interval.minutes;
        if (this.minutes > 59){
            this.hour += 1;
            this.minutes -= 60;
        }
        this.hour += interval.hour;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format("%d:%02d", this.hour, this.minutes);
    }
}
