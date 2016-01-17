package com.auction.auction.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseContext extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "auction";
    private static final int CURRENT_DATABASE_VERSION = 1;
    protected SQLiteDatabase db;

    public DatabaseContext(Context context) {
        super(context, DATABASE_NAME, null, CURRENT_DATABASE_VERSION);
        open();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE categories (_id INTEGER PRIMARY KEY AUTOINCREMENT, categoryId VARCHAR(64) NOT NULL, name VARCHAR(64) NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categories");
        onCreate(db);
    }

    public void open() throws SQLException {
        db = getWritableDatabase();
    }

    public void close() {
        db.close();
    }
}
