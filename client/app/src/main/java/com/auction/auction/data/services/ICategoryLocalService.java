package com.auction.auction.data.services;

import com.auction.auction.data.models.Category;
import java.util.List;

public interface ICategoryLocalService {
    List<Category> all();

    void add(Category category);

    void removeAll();
}