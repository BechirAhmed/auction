package com.auction.auction.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class GetRequestUtils {
    public static String make(String url, Map<String, String> headers) {
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("GET");

            for (Map.Entry<String, String> header : headers.entrySet()) {
                String headerKey = header.getKey();
                String headerValue = header.getValue();
                urlConnection.setRequestProperty(headerKey, headerValue);
            }

            urlConnection.connect();

            InputStream inputStream = null;
            int status = urlConnection.getResponseCode();
            if (status >= 400) {
                inputStream = urlConnection.getErrorStream();
            } else {
                inputStream = urlConnection.getInputStream();
            }


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();

            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
