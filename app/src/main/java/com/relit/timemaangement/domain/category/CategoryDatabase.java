package com.relit.timemaangement.domain.category;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.relit.timemaangement.database.ColumnOption;
import com.relit.timemaangement.database.ColumnType;
import com.relit.timemaangement.database.AbstractDatabase;
import com.relit.timemaangement.database.UpdateQuery;

public class CategoryDatabase extends AbstractDatabase<Category> {
    public static final String CATEGORY_TABLE = "CATEGORY_TABLE";
    public static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String NAME = "CATEGORY_NAME";
    public static final String SHORTCUT = "CATEGORY_SHORTCUT";
    public static final String ICON_ID = "CATEGORY_ICON_ID";
    public static final String COLOR = "CATEGORY_COLOR";

    public CategoryDatabase(Context context) {
        super(context, "categories", CATEGORY_TABLE);
        addColumn(CATEGORY_ID, ColumnType.INTEGER, ColumnOption.AUTOINCREMENT, ColumnOption.PRIMARY_KEY);
        addColumn(NAME, ColumnType.TEXT);
        addColumn(SHORTCUT, ColumnType.TEXT);
        addColumn(ICON_ID, ColumnType.INTEGER);
        addColumn(COLOR, ColumnType.INTEGER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        super.onCreate(sqLiteDatabase);
        addInitialValue(sqLiteDatabase, new Category("Bez Kategorii", "Bez Kategorii", 3, 47));
    }

    @Override
    protected ContentValues getCVFromElement(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, category.getName());
        cv.put(SHORTCUT, category.getShortcut());
        cv.put(ICON_ID, category.getIconID());
        cv.put(COLOR, category.getColor());
        return cv;
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

    public Category getCategoryByID(int categoryID) {
        return searchUniqueQuery(CATEGORY_ID, categoryID).orElse(null);
    }

    public void updateCategory(Category category){
        UpdateQuery query = new UpdateQuery(CATEGORY_ID, category.getId());
        query.addValueToSet(NAME, category.getName());
        query.addValueToSet(SHORTCUT, category.getShortcut());
        query.addValueToSet(ICON_ID, category.getIconID());
        query.addValueToSet(COLOR, category.getColor());
        updateElement(query);
    }
}
