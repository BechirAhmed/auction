package com.auction.auction.data.services;

import com.auction.auction.data.models.Category;

import java.util.List;

public interface ICategoryRemoteService {
    List<Category> getAll();
}
