package com.auction.auction.models;

public class AddProductRequestModel {
    public String name;
    public String price;
    public String realPrice;
    public String imgUrl;
    public String description;

    public AddProductRequestModel(String name, String price, String realPrice, String imgUrl, String description) {
        this.name = name;
        this.price = price;
        this.realPrice = realPrice;
        this.imgUrl = imgUrl;
        this.description = description;
    }
}
