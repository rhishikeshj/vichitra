package com.rhishikeshj.vichitra.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rhishikeshj.vichitra.models.FlickrImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by mjolnir on 20/03/18.
 */

public class FlickrImageService implements ImageService {

    private static final String TAG = FlickrImageService.class.getName();
    private static final int FLICKR_API_PREFIX_LENGTH = 15;

    @Override
    public List<FlickrImage> getImagesForQuery(String query) {
        URL url;
        HttpURLConnection urlConnection = null;
        String server_response = null;

        try {
            url = new URL("https://api.flickr.com/services/feeds/photos_public.gne?tags="
                    + query
                    + "&format=json&callback=");
            urlConnection = (HttpURLConnection) url.openConnection();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                server_response = readStream(urlConnection.getInputStream());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type listType = new TypeToken<List<FlickrImage>>() {
        }.getType();
        server_response = server_response.substring(FLICKR_API_PREFIX_LENGTH,
                server_response.length() - 1);

        JsonObject flickrFeed = new JsonParser().parse(server_response).getAsJsonObject();
        JsonArray flickrImages = flickrFeed.getAsJsonArray("items");

        return new Gson().fromJson(flickrImages, listType);
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
