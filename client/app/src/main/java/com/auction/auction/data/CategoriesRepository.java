package com.auction.auction.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.auction.auction.data.models.Category;

public class CategoriesRepository extends DatabaseContext {
    private static final String TABLE_NAME = "categories";
    public static final String CATEGORY_ID_COLUMN = "categoryId";
    public static final String CATEGORY_NAME_COLUMN = "name";

    public CategoriesRepository(Context context) {
        super(context);
    }

    public void add(Category category) {
        ContentValues contentVals = new ContentValues();
        contentVals.put(CATEGORY_ID_COLUMN, category.id);
        contentVals.put(CATEGORY_NAME_COLUMN, category.name);
        this.db.insert(TABLE_NAME, null, contentVals);
        Log.d("Inserted category", category.name);
    }

    public Cursor all() {
        return this.db.query(TABLE_NAME, new String[] {CATEGORY_ID_COLUMN, CATEGORY_NAME_COLUMN}, null, null, null, null, null);
    }

    public void removeAll() {
        this.db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public Cursor getByName(String name) {
        return this.db.query(TABLE_NAME, new String[] {CATEGORY_ID_COLUMN, CATEGORY_NAME_COLUMN}, "name=?", new String[] { name }, null, null, null, null);
    }
}
