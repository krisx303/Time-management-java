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

import com.relit.timemaangement.R;
import com.relit.timemaangement.domain.abcalendar.CalendarColor;
import com.relit.timemaangement.domain.abcalendar.CalendarDataAB;

public class CalendarABView extends ViewGroup {

    private float columnWidth;
    private static final int days = 5;
    private static final int rowHeight = 220;
    private final Paint paint = new Paint();
    private CalendarDataAB calendarData;
    private boolean initialized = false;
    private int ID = 0;

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
        LayoutInflater.from(context).inflate(R.layout.calendar, this, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if(!initialized) return true;
        int y = (int) (event.getY() / rowHeight);
        int x = (int) (event.getX() / columnWidth);
        if (x < 0 || x >= days || y < 0 || y >= getNOfRows())
            return true;
        calendarData.paintCell(ID, y, x);
        invalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), rowHeight * getNOfRows() + 2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }

    private void drawGridLines(Canvas canvas) {
        paint.setColor(Color.parseColor("#424242"));
        float x = 0;
        for (int i = 0; i < days; i++) {
            canvas.drawLine(x, 0, x, getHeight(), paint);
            x += columnWidth;
        }

        for (int i = 0; i < getNOfRows() + 1; i++) {
            canvas.drawLine(0, i * rowHeight, getWidth(), i * rowHeight, paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        columnWidth = (float) getWidth() / days;
        paint.setStrokeWidth(1f);
        if(!initialized) return;
        int y;
        for (int row = 0; row < getNOfRows(); row++) {
            CalendarColor[] colorsRow = calendarData.getColors(ID, row);
            for (int col = 0; col < days; col++) {
                if (colorsRow[col] == CalendarColor.WEEK_DAY_NONE) continue;
                paint.setColor(colorsRow[col].color);
                int x = (int) (col * columnWidth);
                y = row * rowHeight;
                canvas.drawRect(x, y, x + columnWidth, y + rowHeight, paint);
            }
        }
        drawGridLines(canvas);
        paint.setColor(Color.WHITE);
        paint.setTextSize(35f);
        paint.setTextAlign(Paint.Align.RIGHT);

        y = rowHeight / 2;
        for (int j = 0; j < getNOfRows(); j++) {
            for (int i = 0; i < days; i++) {
                canvas.drawText(calendarData.getLabels(ID, j)[i], (int) ((columnWidth) * (i + 0.6f)), y, paint);
            }
            y += rowHeight;
        }
    }


    public void setMonthData(CalendarDataAB monthData, int id, boolean isLast) {
        this.initialized = true;
        this.ID = id;
        this.calendarData = monthData;
    }

    private int getNOfRows(){
        if(!initialized) return 0;
        return calendarData.getNOfRows(ID);
    }
}