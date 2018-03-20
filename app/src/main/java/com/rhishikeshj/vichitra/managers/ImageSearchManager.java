package com.rhishikeshj.vichitra.managers;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.rhishikeshj.vichitra.models.FlickrImage;
import com.rhishikeshj.vichitra.services.FlickrImageService;
import com.rhishikeshj.vichitra.storage.ImageDatabase;

import java.util.List;

/**
 * Created by mjolnir on 20/03/18.
 */

public class ImageSearchManager implements ImageSearchNetworkListener {
    private static final String TAG = ImageSearchManager.class.getName();

    private final FlickrImageService imageService;
    private final ImageDatabase imageDatabase;
    private final ImageSearchNetworkManager imageSearchNetworkManager;

    public ImageSearchManager(Context appContext) {
        imageService = new FlickrImageService();

        imageSearchNetworkManager = new ImageSearchNetworkManager(imageService, this);
        imageDatabase = Room.databaseBuilder(appContext,
                ImageDatabase.class, "vichitra-db").build();
    }

    public void searchForImages(final String query) {
        imageSearchNetworkManager.searchForImages(query);
    }

    public LiveData<List<FlickrImage>> getCachedImages(final String query) {
        return imageDatabase.flickrImageDao().getAllImages(query);
    }

    @Override
    public void imagesFetchedForQuery(List<FlickrImage> images, String query) {
        for (FlickrImage flickrImage : images) {
            if (flickrImage != null) {
                flickrImage.query = query;
            }
        }

        imageDatabase.flickrImageDao().addImages(images);
    }

    @Override
    public void imagesNotFetchedForQuery(String query) {
        Log.e(TAG, "Images not received !");
    }
}
