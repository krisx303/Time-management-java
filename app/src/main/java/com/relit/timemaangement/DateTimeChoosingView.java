package com.relit.timemaangement;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DateTimeChoosingView extends ViewGroup {
    private TextView dataTextView, hintTextView;
    private Button button;

    public DateTimeChoosingView(Context context) {
        this(context, null);
    }

    public DateTimeChoosingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DateTimeChoosingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_datetime_choosing, this, true);
        dataTextView = findViewById(R.id.data_text);
        hintTextView =findViewById(R.id.hint_text);
        button = findViewById(R.id.change_button);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DateTimeChoosingView,
                0, 0);
        String data = "", hint = "";
        try {
            data = a.getString(R.styleable.DateTimeChoosingView_data);
            hint = a.getString(R.styleable.DateTimeChoosingView_hint);
        } finally {
            a.recycle();
        }
        dataTextView.setText(data);
        hintTextView.setText(hint);
    }

    public void setOnButtonClickListener(OnClickListener onButtonClickListener) {
        button.setOnClickListener(onButtonClickListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 150; // Set the height to 150px

        // Measure the children with the same measure spec
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

        // Set the measured dimensions for this view
        setMeasuredDimension(width, height);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // Layout the children
        int childCount = getChildCount();
        int childLeft = 0;
        int childTop = 0;
        int childRight = right - left;
        int childBottom = bottom - top;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(childLeft, childTop, childRight, childBottom);
        }
    }

    public void setDataText(String text) {
        dataTextView.setText(text);
    }

    public void setHintText(String text) {
        hintTextView.setText(text);
    }
}
