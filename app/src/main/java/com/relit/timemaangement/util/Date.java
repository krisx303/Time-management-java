package com.relit.timemaangement.util;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Date implements Cloneable {

    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int DAY = 3;

    private int year;
    private int month;
    private int day;

    public Date(int day, int month, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static int diff(int type, Date startDate, Date endDate) {
        return endDate.getValueByType(type) - startDate.getValueByType(type);
    }


    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%02d/%02d/%d", day, month, year);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void increaseMonth() {
        month++;
        if(month==13){
            month = 1;
            year++;
        }
    }

    public boolean isBeforeMY(Date endDate) {
        if(this.year < endDate.year) return true;
        return month <= endDate.month;
    }

    public boolean isBefore(Date endDate){
        if(this.year < endDate.year) return true;
        if(this.month < endDate.month) return true;
        return day < endDate.day;
    }

    public String getStringMMYYYY(){
        return Helper.getMonths()[month-1] + " " + year;
    }

    public boolean isSameYearAndMonth(Date endDate) {
        return year == endDate.year && month == endDate.month;
    }

    private int getValueByType(int type){
        switch (type){
            case YEAR:
                return this.year;
            case MONTH:
                return this.month;
            case DAY:
                return this.day;
            default:
                return 0;
        }
    }

    public static Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(YEAR);
        int month = calendar.get(MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new Date(day, month, year);
    }

    @NonNull
    @Override
    public Date clone() {
        return new Date(day, month, year);
    }

    /** Format dd/mm/YYYY like 10/03/2023*/
    public static Date parseString(String toParse) {
        int day = Integer.parseInt(toParse.substring(0, 2));
        int month = Integer.parseInt(toParse.substring(3, 5));
        int year = Integer.parseInt(toParse.substring(6, 10));
        return new Date(day, month, year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Date)) return false;
        Date date = (Date) o;
        return year == date.year && month == date.month && day == date.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    public void setDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
}
