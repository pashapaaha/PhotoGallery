package com.example.deviantartgallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UrlWork {

    private final String TAG = this.getClass().getSimpleName();

    private static final String API_KEY = "39591bd9c6c9594a21fc7adfe6dcdedd";

    public List<Picture> fetchItems() {
        Log.d(TAG, "fetchItems: start");
        try {
            String url = getRequestString();
            Log.d(TAG, "fetchItems: getting url is " + url);
            String jsonString = getUrlString(url);
            Log.d(TAG, "fetchItems: json successful");
            Log.i(TAG, "fetchItems: Received JSON:" + jsonString);

            return parseItems(new JSONObject(jsonString));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "fetchItems: return empty list");
        return Collections.emptyList();
    }

    private String getRequestString(){
        return Uri.parse("https://api.flickr.com/services/rest/")
                .buildUpon()
                .appendQueryParameter("method", "flickr.photos.getRecent")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("extras", "url_s")
                .build().toString();
    }

    public byte[] getUrlBytes(String urlParam) throws IOException {
        URL url = new URL(urlParam);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStream = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlParam);
            }
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();

        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String url) throws IOException {
        return new String(getUrlBytes(url));
    }

    private List<Picture> parseItems(JSONObject jsonBody)
            throws IOException, JSONException {
        Log.d(TAG, "parseItems: start");
        List<Picture> pictureList = new ArrayList<>();
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");
        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
            if (!photoJsonObject.has("url_s")) {
                continue;
            }
            String id = photoJsonObject.getString("id");
            String title = photoJsonObject.getString("title");
            String url = photoJsonObject.getString("url_s");
            pictureList.add(new Picture(id, title, url));
        }
        Log.d(TAG, "parseItems: end");
        return pictureList;
    }
}
