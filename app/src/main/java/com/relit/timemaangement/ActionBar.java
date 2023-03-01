package com.relit.timemaangement;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ActionBar extends ViewGroup {

    private TextView mTextView;

    public ActionBar(Context context) {
        this(context, null);
    }

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.action_bar, this, true);
        mTextView = findViewById(R.id.title);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ActionBar,
                0, 0);
        String text = "Placeholder";
        //boolean isBackButtonVisible = false;
        try {
            text = a.getString(R.styleable.ActionBar_title);
            //isBackButtonVisible = a.getBoolean(R.styleable.ActionBar_showBackButton, false);
        } finally {
            a.recycle();
        }
        mTextView.setText(text);
        // initialize other views and set default values, etc.
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Measure the children
        measureChildren(widthMeasureSpec, heightMeasureSpec);


        // Determine the width and height of the custom view
        int width = getMeasuredWidth();
        setMeasuredDimension(width, 150);
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

    public void setText(String text) {
        mTextView.setText(text);
    }
}