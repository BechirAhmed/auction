package com.auction.auction.data.services;

import com.auction.auction.data.models.Bid;
import com.auction.auction.data.models.Product;

import java.util.List;

public interface IProductRemoteService {
    List<Product> getProductsByCategoryId(String categoryId);
    List<Bid> getProductBidsById(String productId);
}