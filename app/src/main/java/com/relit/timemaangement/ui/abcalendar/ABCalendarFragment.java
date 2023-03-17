package com.relit.timemaangement.ui.abcalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.relit.timemaangement.R;

public class ABCalendarFragment extends ViewGroup {

    public ABCalendarFragment(Context context) {
        this(context, null);
    }

    public ABCalendarFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setWillNotDraw(false);
    }

    public ABCalendarFragment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.fragment_calendarab, this, true);
        ((TextView) findViewById(R.id.month_title)).setText(R.string.calendar);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Measure the children
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // Set the dimensions of the custom view
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }
}