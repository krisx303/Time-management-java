package com.relit.timemaangement.ui.addevent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.maltaisn.icondialog.data.Icon;
import com.relit.timemaangement.DateTimeChoosingView;
import com.relit.timemaangement.R;
import com.relit.timemaangement.ToolbarActivity;
import com.relit.timemaangement.domain.category.Category;
import com.relit.timemaangement.util.CategoryDialog;
import com.relit.timemaangement.util.Helper;
import com.relit.timemaangement.util.Hour;

public class AddCalendarEventActivity extends ToolbarActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private DateTimeChoosingView startHourChoosing, endHourChoosing;
    private Hour startHour, endHour;
    private EventDayType currentEventDayType = EventDayType.NOT_CHOSEN;
    private LinearLayout linearContainer;
    private DateTimeChoosingView dataChoosing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar_event);
        prepareToolbar("Stwórz nową aktywność");
        spinner = findViewById(R.id.spinner);
        startHourChoosing = findViewById(R.id.start_date);
        endHourChoosing = findViewById(R.id.end_date);
        startHour = Helper.getCurrentHour();
        startHourChoosing.setDataText(startHour.toString());
        endHour = startHour.add(1, 0);
        linearContainer = findViewById(R.id.linear_container);
        endHourChoosing.setDataText(endHour.toString());
        startHourChoosing.setOnButtonClickListener(this::onStartHourChoosing);
        endHourChoosing.setOnButtonClickListener(this::onEndHourChoosing);
        spinner.setOnItemSelectedListener(this);
        findViewById(R.id.category).setOnClickListener(v -> openCategoryDialog());
        openCategoryDialog();
    }

    private void openCategoryDialog(){
        CategoryDialog categoryDialog = new CategoryDialog(this, this::onCategoryChosen, Helper.getIcons(this));
        categoryDialog.show();
    }

    private void onCategoryChosen(Category category) {
        Icon icon = Helper.getIcons(this).get(category.getIconID());
        ImageView iconView = findViewById(R.id.category_icon);
        iconView.setImageDrawable(icon.getDrawable());
        iconView.setColorFilter(new LightingColorFilter(iconView.getSolidColor(), category.getColor()));
        ((TextView) findViewById(R.id.semester_name)).setText(category.getName());
        ((TextView) findViewById(R.id.semester_period)).setText(category.getShortcut());
    }

    private void onStartHourChoosing(View ignore) {
        Hour current = Helper.getCurrentHour();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this::onStartHourChosen, current.hour, current.minutes, true);
        timePickerDialog.show();
    }

    private void onStartHourChosen(TimePicker timePicker, int hour, int minutes) {
        startHour.setValues(hour, minutes);
        startHourChoosing.setDataText(startHour.toString());
    }

    private void onEndHourChoosing(View ignore) {
        Hour current = Helper.getCurrentHour();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this::onEndHourChosen, current.hour, current.minutes, true);
        timePickerDialog.show();
    }

    private void onEndHourChosen(TimePicker timePicker, int hour, int minutes) {
        endHour.setValues(hour, minutes);
        endHourChoosing.setDataText(endHour.toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        EventDayType eventDayType = EventDayType.values()[position];
        if (eventDayType == EventDayType.CUSTOM_DAY) {
            if (dataChoosing == null) {
                dataChoosing = new DateTimeChoosingView(this);
                dataChoosing.setHintText("Wybierz datę");
                dataChoosing.setDataText("08/03/2023");
                dataChoosing.setOnButtonClickListener(this::onDateChoosing);
            }
            if (currentEventDayType != EventDayType.CUSTOM_DAY)
                linearContainer.addView(dataChoosing);
        } else {
            if (dataChoosing != null) {
                linearContainer.removeView(dataChoosing);
            }
        }
        currentEventDayType = eventDayType;
    }

    private void onDateChoosing(View ignore) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this::onDateChosen, 12, 1, 2);
        datePickerDialog.show();
    }

    private void onDateChosen(DatePicker datePicker, int year, int month, int day) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}