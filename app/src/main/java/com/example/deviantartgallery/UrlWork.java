package com.example.deviantartgallery;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class UrlWork {

    private  final String TAG = this.getClass().getSimpleName();

    private static final String API_KEY = "39591bd9c6c9594a21fc7adfe6dcdedd";

    public void fetchItems(){
        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "fetchItems: Received JSON:" + jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
