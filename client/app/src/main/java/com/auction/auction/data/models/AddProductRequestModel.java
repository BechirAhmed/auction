package com.auction.auction.data.models;

public class AddProductRequestModel {
    public String name;
    public Double price;
    public Double currentPrice;
    public String imageUrl;
    public String description;

    public AddProductRequestModel(String name, Double startingPrice, Double realPrice, String imageUrl, String description) {
        this.name = name;
        this.currentPrice = startingPrice;
        this.price = realPrice;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}