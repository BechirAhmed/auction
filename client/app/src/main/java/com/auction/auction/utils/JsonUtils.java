package com.auction.auction.utils;

import com.google.gson.Gson;

public class JsonUtils {
    public static String serializeToJson(Object obj) {
        return new Gson().toJson(obj);
    }
}