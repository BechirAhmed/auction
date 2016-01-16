package com.auction.auction.utils;

import com.google.gson.Gson;

public class JsonUtils {
    public static String serializeToJson(Object obj) {
        Gson gson = new Gson();
        String result = gson.toJson(obj);
        return result;
    }
}