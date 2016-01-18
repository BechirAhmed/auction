package com.auction.auction.data.services;

import android.database.Cursor;

import com.auction.auction.data.CategoriesRepository;
import com.auction.auction.data.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryLocalService implements ICategoryLocalService {
    CategoriesRepository mRepository;

    public CategoryLocalService(CategoriesRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public List<Category> all() {
        Cursor cursor = mRepository.all();
        List<Category> categories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(CategoriesRepository.CATEGORY_ID_COLUMN));
                String name = cursor.getString(cursor.getColumnIndex(CategoriesRepository.CATEGORY_NAME_COLUMN));
                Category currentCategory = new Category();
                currentCategory.id = id;
                currentCategory.name = name;
                categories.add(currentCategory);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return categories;
    }

    @Override
    public void add(Category category) {
        if (category == null) {
            throw new NullPointerException("Cannot save a null category.");
        }

        if (category.name.length() == 0) {
            throw new IllegalArgumentException("Invalid category.");
        }

        if (category.id.length() == 0) {
            throw new IllegalArgumentException("Invalid category ID.");
        }

        mRepository.add(category);
    }

    public void removeAll() {
        mRepository.removeAll();
    }

    public String getCategoryId(String name) {
        Cursor cursor = mRepository.getByName(name);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor.getString(cursor.getColumnIndex(CategoriesRepository.CATEGORY_ID_COLUMN));
    }
}
