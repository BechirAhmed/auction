package com.auction.auction.utils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageUploadUtils {
    private static final String IMGUR_CLIENT_ID = "545cd26906d87f6";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final String API_ENDPOINT = "https://api.imgur.com/3/image";

    private final OkHttpClient client;

    public ImageUploadUtils() {
        this.client = new OkHttpClient();
    }

    public String Upload(String filePath) throws Exception {
        File imageFile = new File(filePath);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.getName(),
                        RequestBody.create(MEDIA_TYPE_PNG, imageFile))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url(API_ENDPOINT)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        return response.body().string();
    }
}