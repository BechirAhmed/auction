package com.auction.auction.data.models;

public class AddProductRequestModel {
    public String name;
    public Integer price;
    public Integer realPrice;
    public String imageUrl;
    public String description;

    public AddProductRequestModel(String name, String price, String realPrice, String imageUrl, String description) {
        this.name = name;
        this.price = Integer.parseInt(price);
        this.realPrice = Integer.parseInt(realPrice);
        this.imageUrl = imageUrl;
        this.description = description;
    }
}