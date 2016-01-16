package com.auction.auction.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class PostRequestUtils {
    public static String make(String url, Object data, Map<String, String> headers) {
        HttpURLConnection urlConnection;
        String jsonStringToSend = JsonUtils.serializeToJson(data);

        try {
            urlConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");

            for (Map.Entry<String, String> header : headers.entrySet()) {
                String headerKey = header.getKey();
                String headerValue = header.getValue();
                urlConnection.setRequestProperty(headerKey, headerValue);
            }

            urlConnection.connect();

            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jsonStringToSend);
            writer.close();
            outputStream.close();

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
