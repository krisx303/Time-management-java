package com.relit.timemaangement.ui.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.relit.timemaangement.CalendarEvent;
import com.relit.timemaangement.R;
import com.relit.timemaangement.util.Hour;

import java.util.Calendar;
import java.util.List;

public class CalendarView extends ViewGroup {

    private float columnWidth;
    private int days = 5;
    private int rows = 10;
    private int rowHeight = 220;
    static final float LEFT_MARGIN = 100f;
    private final Hour interval = new Hour(1, 30);
    private List<CalendarEvent> events;
    private final Paint paint = new Paint();

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setWillNotDraw(false);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.calendar, this, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        System.out.println(event.getX() + " " + event.getY());
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Measure the children
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // Determine the width and height of the custom view
        int width = getMeasuredWidth();

        // Set the dimensions of the custom view
        setMeasuredDimension(width, rows*rowHeight + 100);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Layout the children
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }

    private void drawGridLines(Canvas canvas){
        paint.setColor(Color.parseColor("#424242"));
        float x = LEFT_MARGIN;
        for (int i = 0; i < days; i++) {
            canvas.drawLine(x, 0, x, getHeight(), paint);
            x += columnWidth;
        }

        for (int i = 0; i < 10; i++) {
            canvas.drawLine(0,  i*rowHeight, getWidth(), i*rowHeight, paint);
        }
    }

    private void drawHours(Canvas canvas){
        float y = 10f;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35f);
        paint.setTextAlign(Paint.Align.RIGHT);
        Hour hour = new Hour(8, 0);
        for (int i = 0; i < 10; i++) {
            canvas.drawText(hour.toString(), LEFT_MARGIN - 5, y + 35,  paint);
            hour.addInterval(interval);
            y += rowHeight;
        }
    }

    private int calculateXFromDay(int day){
        return (int) (LEFT_MARGIN + (columnWidth * (day)));
    }

    private int calculateYFromHour(Hour hour){
        return (int)(((float)((hour.hour - 8) * 60 + hour.minutes) / 90) * rowHeight);
    }

    private int calculateHeightFromHour(Hour hour){
        return (int)(((float)(hour.hour * 60 + hour.minutes) / 90) * rowHeight);
    }

    private Hour getCurrentHour(Calendar calendar){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return new Hour(hour, minutes);
    }

    private void drawMarkedTime(Canvas canvas){
        paint.setColor(Color.parseColor("#323232"));
        Calendar calendar = Calendar.getInstance();
        Hour currentHour = getCurrentHour(calendar);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        canvas.drawRect(LEFT_MARGIN, 0, LEFT_MARGIN + (columnWidth * (weekday - 2)), getHeight(), paint);
        if(currentHour.hour < 8) return;
        int y = calculateYFromHour(currentHour);
        canvas.drawRect(LEFT_MARGIN, 0, LEFT_MARGIN + columnWidth * (weekday - 1), y, paint);
        paint.setColor(Color.parseColor("#4c40ff"));
        paint.setStrokeWidth(3f);
        canvas.drawLine(LEFT_MARGIN + columnWidth * (weekday - 2), y, LEFT_MARGIN + columnWidth * (weekday - 1), y, paint);
        paint.setStrokeWidth(1f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        columnWidth = (getWidth() - 100f) / days;
        paint.setStrokeWidth(1f);
        drawMarkedTime(canvas);
        drawGridLines(canvas);
        drawHours(canvas);

//        paint.setColor(Color.parseColor("#123123"));
//        for (CalendarEvent event : events) {
//            int y = calculateYFromHour(event.getStartHour());
//            int height = calculateHeightFromHour(event.getDuration());
//            int x = calculateXFromDay(event.getDay());
//            canvas.drawRect(x + 2,y, x+columnWidth - 2, y+height, paint);
//        }

    }

    public void setCalendarEvents(List<CalendarEvent> events) {
        this.events = events;
    }
}