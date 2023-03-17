package com.relit.timemaangement.ui.abcalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.relit.timemaangement.CalendarEvent;
import com.relit.timemaangement.R;
import com.relit.timemaangement.util.Helper;
import com.relit.timemaangement.util.Hour;

import java.util.Calendar;
import java.util.List;

public class CalendarABView extends ViewGroup {

    private float columnWidth;
    private int days = 5;
    private int rowHeight = 220;
    private final Paint paint = new Paint();
    private int year, month, day, rows = 0;

    public CalendarABView(Context context) {
        this(context, null);
    }

    public CalendarABView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setWillNotDraw(false);
    }

    public CalendarABView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.fragment_calendar, this, true);
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
        setMeasuredDimension(width, rowHeight*rows+2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }

    private void drawGridLines(Canvas canvas){
        paint.setColor(Color.parseColor("#424242"));
        float x = 0;
        for (int i = 0; i < days; i++) {
            canvas.drawLine(x, 0, x, getHeight(), paint);
            x += columnWidth;
        }

        for (int i = 0; i < 8; i++) {
            canvas.drawLine(0,  i*rowHeight, getWidth(), i*rowHeight, paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        columnWidth = (getWidth()) / days;
        paint.setStrokeWidth(1f);
        drawGridLines(canvas);
        paint.setColor(Color.WHITE);
        paint.setTextSize(35f);
        paint.setTextAlign(Paint.Align.RIGHT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);

        int startDay = (calendar.get(Calendar.DAY_OF_WEEK)+5) % 7;
        calendar.add(Calendar.DAY_OF_MONTH, -startDay);
        int y = (int)(rowHeight/2);
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                canvas.drawText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), (int) ((columnWidth)*(i+0.6f)), y,  paint);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            y += rowHeight;
        }
    }

    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        for (int i = 0; i < 10; i++) {
            this.rows += 1;
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            if(calendar.get(Calendar.MONTH) != month) break;
        }
    }
}