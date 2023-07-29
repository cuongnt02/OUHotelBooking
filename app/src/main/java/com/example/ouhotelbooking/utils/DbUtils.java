package com.example.ouhotelbooking.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbUtils {

    private SQLiteDatabase db;

    public DbUtils(SQLiteDatabase database) {
        db = database;
    }

    public Cursor queryAllColumns(String tableName, String whereClause, String[] whereParams) {
        Cursor cursor = db.query(
                tableName,
                null,
                whereClause,
                whereParams,
                null,
                null,
                null,
                null
        );
        return cursor;
    }
}
