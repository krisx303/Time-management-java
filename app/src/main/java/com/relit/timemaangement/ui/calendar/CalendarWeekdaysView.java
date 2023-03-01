package com.relit.timemaangement.ui.calendar;

import static com.relit.timemaangement.ui.calendar.CalendarView.LEFT_MARGIN;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.relit.timemaangement.R;

public class CalendarWeekdaysView extends ViewGroup {

    private float columnGap;
    private int days = 5;
    private String[] weekdays = new String[]{"pon.", "wt.", "śr.", "czw.", "pt.", "sb.", "nd."};
    private int firstDay = 0;

    private final Paint paint = new Paint();

    public CalendarWeekdaysView(Context context) {
        this(context, null);
    }

    public CalendarWeekdaysView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setWillNotDraw(false);
    }

    public CalendarWeekdaysView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.calendar, this, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Measure the children
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // Determine the width and height of the custom view
        int width = getMeasuredWidth();

        // Set the dimensions of the custom view
        setMeasuredDimension(width, 70);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {}

    private void drawWeekdays(Canvas canvas){
        paint.setColor(Color.WHITE);
        paint.setTextSize(40f);
        paint.setTextAlign(Paint.Align.CENTER);

        float x = LEFT_MARGIN + columnGap / 2;

        for (int i = 0; i < days; i++) {
            String weekday = weekdays[(firstDay+i) % days];
            canvas.drawText(weekday, x, 60, paint);
            x += columnGap;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        columnGap = (getWidth() - 100f) / days;
        paint.setColor(Color.parseColor("#3a3a3a"));
        paint.setStrokeWidth(1f);

        drawWeekdays(canvas);
    }
}