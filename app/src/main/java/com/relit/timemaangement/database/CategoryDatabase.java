package com.relit.timemaangement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.relit.timemaangement.ui.category.Category;

public class CategoryDatabase extends RelitDatabase<Category> {

    public static final String CATEGORY_TABLE = "CATEGORY_TABLE";
    public static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String NAME = "CATEGORY_NAME";
    public static final String SHORTCUT = "CATEGORY_SHORTCUT";
    public static final String ICON_ID = "CATEGORY_ICON_ID";
    public static final String COLOR = "CATEGORY_COLOR";

    public CategoryDatabase(Context context) {
        super(context, "cat", CATEGORY_TABLE);
        addColumn(CATEGORY_ID, ColumnType.INTEGER, ColumnOption.AUTOINCREMENT, ColumnOption.PRIMARY_KEY);
        addColumn(NAME, ColumnType.TEXT);
        addColumn(SHORTCUT, ColumnType.TEXT);
        addColumn(ICON_ID, ColumnType.INTEGER);
        addColumn(COLOR, ColumnType.INTEGER);
    }

    @Override
    public boolean addElement(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, category.getName());
        cv.put(SHORTCUT, category.getShortcut());
        cv.put(ICON_ID, category.getIconID());
        cv.put(COLOR, category.getColor());
        return addElement(cv);
    }

    @Override
    protected Category getElementFromCursor(Cursor cursor) {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String shortcut = cursor.getString(2);
        int iconID = cursor.getInt(3);
        int color = cursor.getInt(4);
        return new Category(id, name, shortcut, iconID, color);
    }
}
