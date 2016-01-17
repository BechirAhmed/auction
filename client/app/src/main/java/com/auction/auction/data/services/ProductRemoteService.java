package com.auction.auction.data.services;

import android.util.Log;

import com.auction.auction.data.models.Bid;
import com.auction.auction.data.models.Category;
import com.auction.auction.data.models.Product;
import com.auction.auction.utils.GetRequestUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRemoteService implements IProductRemoteService {
    private static final String PRODUCTS_REQUEST_URL_FORMAT = "http://android-auction.herokuapp.com/api/categories/%s/products";
    private static final String PRODUCT_BIDS_REQUEST_URL_FORMAT = "http://android-auction.herokuapp.com/api/products/%s/bids";

    private String mEncodedAuthKey;

    public ProductRemoteService(String encodedAuthKey) {
        this.mEncodedAuthKey = encodedAuthKey;
    }

    public List<Product> getProductsByCategoryId(String categoryId) {
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Authorization", "Basic " + mEncodedAuthKey);

        Log.d("RemoteService", " categoryId: " + categoryId);
        Log.d("RemoteService", " authToken: " + mEncodedAuthKey);
        String productsResult = GetRequestUtils.make(String.format(PRODUCTS_REQUEST_URL_FORMAT, categoryId), requestHeaders);
        Log.d("wat", " " + productsResult);
        return new Gson().fromJson(productsResult, new TypeToken<List<Product>>(){}.getType());
    }

    public List<Bid> getProductBidsById(String productId) {
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Authorization", "Basic " + mEncodedAuthKey);

        String bidsResult = GetRequestUtils.make(String.format(PRODUCT_BIDS_REQUEST_URL_FORMAT, productId), requestHeaders);
        return new Gson().fromJson(bidsResult, new TypeToken<List<Bid>>(){}.getType());
    }
}
