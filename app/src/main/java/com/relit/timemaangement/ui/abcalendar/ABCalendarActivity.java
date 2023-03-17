package com.relit.timemaangement.ui.abcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.relit.timemaangement.R;

import java.util.ArrayList;
import java.util.List;

public class ABCalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcalendar);
        ViewPager viewPager = findViewById(R.id.viewPager);
        List<View> views = new ArrayList<>();
        View inflate = getLayoutInflater().inflate(R.layout.fragment_calendarab, null);
        views.add(inflate);
        CalendarABView childAt = (CalendarABView) new ABCalendarFragment(this).getChildAt(0);
        views.add(childAt);
        childAt.setDate(1, 2, 3);
        views.add(getLayoutInflater().inflate(R.layout.fragment_calendarab, null));
        CustomPagerAdapter adapter = new CustomPagerAdapter(views);
        viewPager.setAdapter(adapter);
    }
}
