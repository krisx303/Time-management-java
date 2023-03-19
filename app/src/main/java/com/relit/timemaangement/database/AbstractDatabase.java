package com.relit.timemaangement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractDatabase<T> extends SQLiteOpenHelper {

    private final String tableName;
    private final List<Column> columns;
    public static final int INFINITY = -1;

    public AbstractDatabase(Context context, String dbName, String tableName) {
        super(context, dbName + ".db", null, 1);
        this.tableName = tableName;
        this.columns = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(getCreateTableStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    protected void addColumn(String columnName, ColumnType type, ColumnOption... options) {
        columns.add(new Column(columnName, type, options));
    }

    protected String getCreateTableStatement() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(tableName);
        builder.append(" (");
        String content = columns.stream().map(Object::toString).collect(Collectors.joining(", "));
        builder.append(content);
        builder.append(")");
        return builder.toString();
    }

    /** Use this in onCreate method to provide a initial data in database*/
    protected boolean addInitialValue(SQLiteDatabase database, T element){
        return database.insert(tableName, null, getCVFromElement(element)) != -1;
    }

    protected boolean addElement(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long insert = db.insert(tableName, null, cv);
        db.close();
        return insert != -1;
    }

    public boolean addElement(T element){
        return addElement(getCVFromElement(element));
    }

    protected abstract T getElementFromCursor(Cursor cursor);

    protected abstract ContentValues getCVFromElement(T element);

    public List<T> rawQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<T> list = getElementsFromCursor(cursor);
        cursor.close();
        db.close();
        return list;
    }

    public List<T> getNFirstElements(int limit) {
        String query = "SELECT * FROM " + tableName;
        if (limit != INFINITY)
            query += " LIMIT" + limit;
        return rawQuery(query);
    }

    protected List<T> getElementsFromCursor(Cursor cursor) {
        List<T> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                list.add(getElementFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<T> getAllElements() {
        return getNFirstElements(INFINITY);
    }

    public List<T> searchQuery(String columnName, Object value) {
        return searchQuery(columnName, value, INFINITY);
    }

    public List<T> searchQuery(String columnName, Object value, int limit) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM ");
        builder.append(tableName).append(" WHERE ");
        builder.append(columnName).append(" = ");
        String strValue;
        if (value instanceof String) {
            strValue = "\"" + value + "\"";
        } else {
            strValue = value.toString();
        }
        builder.append(strValue);
        if(limit != INFINITY){
            builder.append(" LIMIT ").append(limit);
        }
        return rawQuery(builder.toString());
    }

    public Optional<T> searchUniqueQuery(String columnName, Object value) {
        List<T> ts = searchQuery(columnName, value, 1);
        if (ts.size() == 0) return Optional.empty();
        return Optional.of(ts.get(0));
    }


    public void updateElement(UpdateQuery updateQuery) {
        String query = "UPDATE " + tableName + " " + updateQuery.toString();
        this.getWritableDatabase().execSQL(query);
    }

    public Optional<T> searchWhereMaxQuery(String columnName){
        String query = "SELECT * FROM " + tableName + " WHERE " + columnName + " = (SELECT MAX(" +
                columnName + ") FROM " + tableName + ") LIMIT 1;";
        List<T> ts = rawQuery(query);
        if (ts.size() == 0) return Optional.empty();
        return Optional.of(ts.get(0));
    }
}
