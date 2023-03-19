package com.relit.timemaangement.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NonSwipeableViewPager extends ViewPager {

    public NonSwipeableViewPager(Context context) {
        super(context);
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Disable swipe gesture by returning false
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Disable swipe gesture by returning false
        return false;
    }
}
