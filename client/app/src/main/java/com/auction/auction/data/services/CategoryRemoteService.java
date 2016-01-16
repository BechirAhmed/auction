package com.auction.auction.data.services;

import com.auction.auction.data.models.Category;
import com.auction.auction.utils.GetRequestUtils;
import com.auction.auction.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRemoteService implements ICategoryRemoteService {
    private static final String CATEGORIES_REQUEST_URL = "http://192.168.1.106:3000/api/categories";
    private String mEncodedAuthKey;

    public CategoryRemoteService(String encodedAuthKey) {
        this.mEncodedAuthKey = encodedAuthKey;
    }

    public List<Category> getAll() {
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Authorization", "Basic " + mEncodedAuthKey);

        String categoriesResult = GetRequestUtils.make(CATEGORIES_REQUEST_URL, requestHeaders);
        return new Gson().fromJson(categoriesResult, new TypeToken<List<Category>>(){}.getType());
    }
}
