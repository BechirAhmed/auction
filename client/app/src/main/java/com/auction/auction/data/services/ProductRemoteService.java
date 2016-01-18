package com.auction.auction.data.services;

import android.util.Log;

import com.auction.auction.data.models.AddProductRequestModel;
import com.auction.auction.data.models.AddProductResponseModel;
import com.auction.auction.data.models.Bid;
import com.auction.auction.data.models.Category;
import com.auction.auction.data.models.Product;
import com.auction.auction.utils.GetRequestUtils;
import com.auction.auction.utils.PostRequestUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRemoteService implements IProductRemoteService {
    private static final String PRODUCTS_REQUEST_URL_FORMAT = "http://android-auction.herokuapp.com/api/categories/%s/products";
    private static final String PRODUCT_BIDS_REQUEST_URL_FORMAT = "http://android-auction.herokuapp.com/api/products/%s/bids";
    private static final String ADD_PRODUCT_URL = "http://android-auction.herokuapp.com/api/categories/%s/products";

    private String mEncodedAuthKey;

    public ProductRemoteService(String encodedAuthKey) {
        this.mEncodedAuthKey = encodedAuthKey;
    }

    public List<Product> getProductsByCategoryId(String categoryId) {
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Authorization", "Basic " + mEncodedAuthKey);

        String productsResult = GetRequestUtils.make(String.format(PRODUCTS_REQUEST_URL_FORMAT, categoryId), requestHeaders);
        return new Gson().fromJson(productsResult, new TypeToken<List<Product>>(){}.getType());
    }

    public List<Bid> getProductBidsById(String productId) {
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Authorization", "Basic " + mEncodedAuthKey);

        String bidsResult = GetRequestUtils.make(String.format(PRODUCT_BIDS_REQUEST_URL_FORMAT, productId), requestHeaders);
        return new Gson().fromJson(bidsResult, new TypeToken<List<Bid>>(){}.getType());
    }

    public boolean addProduct(AddProductRequestModel product, String categoryId) {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", "Basic " + mEncodedAuthKey);

        String addProductResult = PostRequestUtils.make(String.format(ADD_PRODUCT_URL, categoryId), product, requestHeaders);

        try {
            AddProductResponseModel result = new Gson().fromJson(addProductResult, AddProductResponseModel.class);
            if (result != null) {
                if (result.name.equals(product.name)) {
                    return true;
                }
            }
        } catch (JsonParseException e) {
            return false;
        }

        return false;
    }
}