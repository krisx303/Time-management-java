package com.relit.timemaangement.ui.abcalendar;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.relit.timemaangement.MainActivity;
import com.relit.timemaangement.R;
import com.relit.timemaangement.TimeManagement;
import com.relit.timemaangement.ToolbarActivity;
import com.relit.timemaangement.domain.abcalendar.CalendarColor;
import com.relit.timemaangement.domain.abcalendar.CalendarDataAB;
import com.relit.timemaangement.domain.semester.Semester;
import com.relit.timemaangement.util.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ABCalendarActivity extends ToolbarActivity {

    private ImageButton nextButton, prevButton;
    private ViewPager viewPager;
    private final List<ABCalendarFragment> calendarABViews = new ArrayList<>();
    private CalendarDataAB calendarData;
    private Date startDate, endDate;
    private String semesterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcalendar);
        prepareToolbar("Uzupełnij swój kalendarz");

        viewPager = findViewById(R.id.viewPager);
        nextButton = findViewById(R.id.right_arrow);
        prevButton = findViewById(R.id.left_arrow);
        nextButton.setOnClickListener(this::onNextButtonClick);
        prevButton.setOnClickListener(this::onPrevButtonClick);
        findViewById(R.id.radio_button_1).setOnClickListener(v -> setActiveColorBrush(CalendarColor.WEEK_DAY_NONE));
        findViewById(R.id.radio_button_2).setOnClickListener(v -> setActiveColorBrush(CalendarColor.WEEK_DAY_A));
        findViewById(R.id.radio_button_3).setOnClickListener(v -> setActiveColorBrush(CalendarColor.WEEK_DAY_B));
        findViewById(R.id.radio_button_4).setOnClickListener(v -> setActiveColorBrush(CalendarColor.FREE_DAY));

        startDate = Date.parseString( getIntent().getStringExtra("startDate"));
        endDate = Date.parseString( getIntent().getStringExtra("endDate"));
        semesterName = getIntent().getStringExtra("semesterName");

        List<View> views = new ArrayList<>();

        fulfillViews(views, startDate.clone(), endDate);
        CustomPagerAdapter adapter = new CustomPagerAdapter(views);
        viewPager.setAdapter(adapter);
    }

    private void setActiveColorBrush(CalendarColor brushColor) {
        calendarData.setActiveBrushColor(brushColor);
    }

    private void onPrevButtonClick(View view) {
        int previousPage = viewPager.getCurrentItem() - 1;
        viewPager.setCurrentItem(previousPage);
        calendarABViews.get(viewPager.getCurrentItem()).getABCalendarView().invalidate();
    }

    private void onNextButtonClick(View view) {
        int nextPage = viewPager.getCurrentItem() + 1;
        viewPager.setCurrentItem(nextPage);
        calendarABViews.get(viewPager.getCurrentItem()).getABCalendarView().invalidate();
    }

    private void fulfillViews(List<View> views, Date startDate, Date endDate) {

        calendarData = CalendarDataAB.create(startDate, endDate);
        Calendar calendar = Calendar.getInstance();
        calendar.set(startDate.getYear(), startDate.getMonth(), 1);


        ABCalendarFragment abCalendar = new ABCalendarFragment(this);
        abCalendar.setMonthData(calendarData, 0, false);
        abCalendar.setTitle(startDate.getStringMMYYYY());
        views.add(abCalendar.getRoot());

        int id = 1;
        while (startDate.getYear() < endDate.getYear() || startDate.getMonth() < endDate.getMonth()){
            calendarABViews.add(abCalendar);
            startDate.increaseMonth();
            abCalendar = new ABCalendarFragment(this);
            abCalendar.setMonthData(calendarData, id, startDate.isSameYearAndMonth(endDate));
            abCalendar.setTitle(startDate.getStringMMYYYY());
            views.add(abCalendar.getRoot());
            id++;
        }
        calendarABViews.add(abCalendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.abcalendar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_menu_item) {
            saveData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        if(!calendarData.isAllDataValid()){
            Toast.makeText(this, "Zostały jeszcze niezaznaczone dni!", Toast.LENGTH_SHORT).show();
            return;
        }
        String data = calendarData.getDataAsString();
        TimeManagement.getSemesterDatabase().addElement(new Semester(0, semesterName, data, startDate, endDate));
        TimeManagement.notifySemesterID();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
