package com.relit.timemaangement.ui.category;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryDatabaseHelper extends SQLiteOpenHelper {
    public static final String CATEGORY_TABLE = "CATEGORY_TABLE";
    public static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String NAME = "CATEGORY_NAME";
    public static final String SHORTCUT = "CATEGORY_BARCODE";
    public static final String ICON_ID = "CATEGORY_BIN";
    public static final String COLOR = "CATEGORY_FAVORITE";

    public CategoryDatabaseHelper(Context context){
        super(context, "categories.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CATEGORY_TABLE + " (" + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + SHORTCUT + " TEXT, " + ICON_ID + " INTEGER, " + COLOR + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addElement(Category category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, category.getName());
        cv.put(SHORTCUT, category.getShortcut());
        cv.put(ICON_ID, category.getIconID());
        cv.put(COLOR, category.getColor());
        long insert = db.insert(CATEGORY_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public List<Category> getAllCategories(){
        List<Category> list;
        String query = "SELECT * FROM " + CATEGORY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        list = getCategoriesFromQuery(query);
        cursor.close();
        db.close();
        return list;
    }

    private List<Category> getCategoriesFromQuery(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Category> categories = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                int categoryID = cursor.getInt(0);
                String name = cursor.getString(1);
                String shortcut = cursor.getString(2);
                int iconID = cursor.getInt(3);
                int color = cursor.getInt(4);
                categories.add(new Category(categoryID, name, shortcut, iconID, color));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public Category getCategoryByID(int categoryID) {
        String query = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + CATEGORY_ID + " = " + categoryID;
        Cursor cursor = this.getWritableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            return new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
        }
        return null;
    }

    public void updateCategory(Category category) {
//        String query = String.format("UPDATE %s SET %s = %s, %s = %s, %s = %d, %s = %d WHERE %s = %d", CATEGORY_TABLE, );
//        String query = "UPDATE " + CATEGORY_TABLE + " SET " + CHECKED + " = " + (product.isChecked() ? 1 : 0) + " WHERE " + BARCODE + " = " + product.getBarcode();
//        this.getWritableDatabase().execSQL(query);
    }
}
