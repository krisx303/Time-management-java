package com.relit.timemaangement.ui.abcalendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.relit.timemaangement.DateTimeChoosingView;
import com.relit.timemaangement.R;
import com.relit.timemaangement.ToolbarActivity;
import com.relit.timemaangement.util.Date;

public class AddABCalendarActivity extends ToolbarActivity {

    private DateTimeChoosingView dateStartView, dateEndView;
    private Date startDate, endDate;
    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_abcalendar);
        prepareToolbar(R.string.add_new_semester);
        startDate = new Date(19, 3, 2023);
        endDate = new Date(20, 6, 2023);
        dateStartView = findViewById(R.id.start_date);
        dateEndView = findViewById(R.id.end_date);
        dateStartView.setOnButtonClickListener(this::onStartDateChoosing);
        dateEndView.setOnButtonClickListener(this::onEndDateChoosing);
        editName = findViewById(R.id.edit_semester_name);
        dateStartView.setDataText(startDate.toString());
        dateEndView.setDataText(endDate.toString());
        findViewById(R.id.confirm_button).setOnClickListener(this::onConfirm);
    }

    private void onConfirm(View view) {
        Date currentDate = Date.getCurrentDate();
        if (!startDate.isBefore(endDate) || startDate.isBefore(currentDate)) {
            Toast.makeText(this, "Niepoprawne daty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editName.getText().length() < 3) {
            Toast.makeText(this, "Niepoprawna nazwa!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ABCalendarActivity.class);
        intent.putExtra("startDate", startDate.toString());
        intent.putExtra("endDate", endDate.toString());
        intent.putExtra("semesterName", editName.getText().toString());
        startActivity(intent);
    }

    private void onStartDateChoosing(View ignore) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this::onStartDateChosen, startDate.getYear(), startDate.getMonth()-1, startDate.getDay());
        datePickerDialog.show();
    }

    private void onStartDateChosen(DatePicker datePicker, int year, int month, int day) {
        startDate.setDate(day, month + 1, year);
        dateStartView.setDataText(startDate.toString());
    }

    private void onEndDateChoosing(View ignore) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this::onEndDateChosen, endDate.getYear(), endDate.getMonth()-1, endDate.getDay());
        datePickerDialog.show();
    }

    private void onEndDateChosen(DatePicker datePicker, int year, int month, int day) {
        endDate.setDate(day, month + 1, year);
        dateEndView.setDataText(endDate.toString());
    }
}