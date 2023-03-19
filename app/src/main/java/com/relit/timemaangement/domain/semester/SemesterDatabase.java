package com.relit.timemaangement.domain.semester;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.relit.timemaangement.database.ColumnOption;
import com.relit.timemaangement.database.ColumnType;
import com.relit.timemaangement.database.RelitDatabase;

public class SemesterDatabase extends RelitDatabase<Semester> {
    public static final String SEMESTER_TABLE = "SEMESTER_TABLE";
    public static final String SEMESTER_ID = "SEMESTER_ID";
    public static final String NAME = "SEMESTER_NAME";
    public static final String DATA = "SEMESTER_DATA";
    public static final String START_DATE = "SEMESTER_START";
    public static final String END_DATE = "SEMESTER_END";

    public SemesterDatabase(Context context) {
        super(context, "semesters", SEMESTER_TABLE);
        addColumn(SEMESTER_ID, ColumnType.INTEGER, ColumnOption.AUTOINCREMENT, ColumnOption.PRIMARY_KEY);
        addColumn(NAME, ColumnType.TEXT);
        addColumn(DATA, ColumnType.TEXT);
        addColumn(START_DATE, ColumnType.TEXT);
        addColumn(END_DATE, ColumnType.TEXT);
    }

    @Override
    public boolean addElement(Semester semester) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, semester.getName());
        cv.put(DATA, semester.getData());
        cv.put(START_DATE, semester.getStartDateAsString());
        cv.put(END_DATE, semester.getEndDateAsString());
        return addElement(cv);
    }

    @Override
    protected Semester getElementFromCursor(Cursor cursor) {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String data = cursor.getString(2);
        String startDate = cursor.getString(3);
        String endDate = cursor.getString(4);
        System.out.println(name + " " + data + " " + startDate + " " + endDate);
        return new Semester(id, name, data, startDate, endDate);
    }

    public Semester getCategoryByID(int categoryID) {
        return searchUniqueQuery(SEMESTER_ID, categoryID).orElse(null);
    }

    public Semester getLatestSemester() {
        return searchWhereMaxQuery(SEMESTER_ID).orElse(null);
    }
}
