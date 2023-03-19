package com.relit.timemaangement.domain.abcalendar;

import com.relit.timemaangement.util.Date;

import java.util.Arrays;
import java.util.Calendar;

public class CalendarDataAB {
    private final int[] nOfRows;
    private final int[] sumOfRows;
    private final CalendarColor[][] colors;
    private final String[][] labels;
    private CalendarColor activeBrushColor = CalendarColor.WEEK_DAY_NONE;

    private static int getDayOfWeek(Calendar calendar) {
        return (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7;
    }

    private static int getStartSkip(Calendar calendar, int startDay) {
        int startWeekday = getDayOfWeek(calendar);
        int y = startDay + startWeekday;
        int i = 5 * (y / 7) + Math.max(y % 7 - 1, 0);
        if (startWeekday < 5)
            return i;
        return i - 5;
    }

    private static void fulfillRows(Calendar calendar, int[] rows, int[] sumOfRows, Date startDate, Date endDate) {
        int i = 0;
        int months = Date.diff(Date.MONTH, startDate, endDate)+1;
        while (startDate.isBeforeMY(endDate)) {
            calendar.set(startDate.getYear(), startDate.getMonth() - 1, 1);
            int startWeekday = getDayOfWeek(calendar);
            int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            double div = (maxDay + startWeekday) / 7.0;
            if (startWeekday > 4) div -= 1.0;
            System.out.println(startDate.getStringMMYYYY() + " " + div);
            rows[i] = (int) Math.ceil(div);
            sumOfRows[i] += rows[i];
            calendar.set(Calendar.DAY_OF_MONTH, maxDay);
            if (getDayOfWeek(calendar) < 4 && i + 1 < months) sumOfRows[i] -= 1;
            startDate.increaseMonth();
            i++;
        }
        sumOfRows[months - 1] = 0;
        System.arraycopy(sumOfRows, 0, sumOfRows, 1, months - 1);
        sumOfRows[0] = 0;
        for (int month = 1; month < months; month++) {
            sumOfRows[month] += sumOfRows[month - 1];
        }
    }

    public static CalendarDataAB create(Date startDate, Date endDate) {
        int months = Date.diff(Date.MONTH, startDate, endDate)+1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(startDate.getYear(),startDate.getMonth() - 1, 1);
        int startSkip = getStartSkip(calendar, startDate.getDay());
        int dayOfWeek = getDayOfWeek(calendar);

        int[] rows = new int[months];
        int[] sumOfRows = new int[months];
        fulfillRows(calendar, rows, sumOfRows, startDate.clone(), endDate);
        int lastRow = sumOfRows[months - 1] + rows[months - 1];

        calendar.set(endDate.getYear(),endDate.getMonth() - 1, 1);
        int endSkip = sumOfRows[months - 1] * 5 + getStartSkip(calendar, endDate.getDay());
        calendar.set(startDate.getYear(), startDate.getMonth()- 1, 1);
        if (dayOfWeek < 5)
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        else
            calendar.add(Calendar.DAY_OF_MONTH, 7 - dayOfWeek);
        String[][] labels = new String[lastRow][5];
        for (int row = 0; row < lastRow; row++) {
            for (int i = 0; i < 5; i++) {
                labels[row][i] = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 2);
        }
        CalendarColor[][] colors = new CalendarColor[lastRow][5];
        int pos = 0;
        for (int row = 0; row < lastRow; row++) {
            for (int col = 0; col < 5; col++) {
                if (pos < startSkip || pos >= endSkip)
                    colors[row][col] = CalendarColor.WEEK_DAY_UNAVAILABLE;
                else
                    colors[row][col] = CalendarColor.WEEK_DAY_NONE;
                pos++;
            }
        }
        return new CalendarDataAB(rows, sumOfRows, colors, labels);
    }

    public CalendarDataAB(int[] nOfRows, int[] sumOfRows, CalendarColor[][] colors, String[]... labels) {
        this.nOfRows = nOfRows;
        this.sumOfRows = sumOfRows;
        this.colors = colors;
        this.labels = labels;
    }

    public int getNOfRows(int id) {
        return nOfRows[id];
    }

    public CalendarColor[] getColors(int id, int row) {
        return colors[sumOfRows[id] + row];
    }

    public String[] getLabels(int id, int row) {
        return labels[sumOfRows[id] + row];
    }

    public void paintCell(int id, int row, int col) {
        if (!activeBrushColor.isValidBrushColor()) return;
        int r = sumOfRows[id] + row;
        if (colors[r][col] == CalendarColor.WEEK_DAY_UNAVAILABLE) return;
        colors[r][col] = activeBrushColor;
    }

    public CalendarColor getActiveBrushColor() {
        return activeBrushColor;
    }

    public void setActiveBrushColor(CalendarColor activeBrushColor) {
        this.activeBrushColor = activeBrushColor;
    }

    public String getDataAsString() {
        int rows = sumOfRows[sumOfRows.length-1] + nOfRows[nOfRows.length-1];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int col = 0; col < 5; col++) {
                if(colors[i][col] != CalendarColor.WEEK_DAY_UNAVAILABLE)
                    builder.append(colors[i][col].letter);
            }
        }
        return builder.toString();
    }

    public boolean isAllDataValid() {
        int rows = sumOfRows[sumOfRows.length-1] + nOfRows[nOfRows.length-1];
        for (int i = 0; i < rows; i++) {
            for (int col = 0; col < 5; col++) {
                if(colors[i][col] == CalendarColor.WEEK_DAY_NONE)
                    return false;
            }
        }
        return true;
    }
}
