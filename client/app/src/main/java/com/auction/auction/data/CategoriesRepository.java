package com.auction.auction.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.auction.auction.data.models.Category;

public class CategoriesRepository extends DatabaseContext {
    private static final String TABLE_NAME = "categories";

    public CategoriesRepository(Context context) {
        super(context);
    }

    public void add(Category category) {
        ContentValues contentVals = new ContentValues();
        contentVals.put("categoryId", category.id);
        contentVals.put("name", category.name);
        this.db.insert(TABLE_NAME, null, contentVals);
        Log.d("Inserted category: ", category.name);
    }

    public Cursor all() {
        return this.db.query(TABLE_NAME, new String[] {"categoryId", "name"}, null, null, null, null, null);
    }

    public void removeAll() {
        this.db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
