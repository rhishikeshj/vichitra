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

/**
 * Image search manager entity.
 * Responsibilities of this class include talking to the image network service
 * and DB to store images returned for a query.
 */
public class ImageSearchManager {
    private static final String TAG = ImageSearchManager.class.getName();

    private final FlickrImageService imageService;
    private final ImageDatabase imageDatabase;
    private final ImageSearchNetworkManager imageSearchNetworkManager;

    /**
     * Constructor
     *
     * @param appContext
     */
    public ImageSearchManager(Context appContext) {
        imageService = new FlickrImageService();

        imageSearchNetworkManager = new ImageSearchNetworkManager(imageService);
        imageDatabase = Room.databaseBuilder(appContext,
                ImageDatabase.class, "vichitra-db").build();
    }

    /**
     * API to search for images given a query.
     * This method will replace all existing data for a query with new incoming data.
     *
     * @param query
     */
    public void searchForImages(final String query) {
        final ImageSearchManager manager = this;
        imageSearchNetworkManager.searchForImages(query, new ImageSearchNetworkListener() {
            @Override
            public void imagesFetchedForQuery(List<FlickrImage> images, String query) {
                manager.imagesFetchedForQuery(images, query);
            }

            @Override
            public void imagesNotFetchedForQuery(String query) {
                manager.imagesNotFetchedForQuery(query);
            }
        });
    }

    /**
     * API to search for more images for a given query.
     * This API will only add to the existing data in the DB
     *
     * @param query
     */
    public void searchForMoreImages(final String query) {
        final ImageSearchManager manager = this;
        imageSearchNetworkManager.searchForImages(query, new ImageSearchNetworkListener() {
            @Override
            public void imagesFetchedForQuery(List<FlickrImage> images, String query) {
                manager.moreImagesFetchedForQuery(images, query);
            }

            @Override
            public void imagesNotFetchedForQuery(String query) {
                manager.imagesNotFetchedForQuery(query);
            }
        });
    }

    /**
     * Return images from the DB.
     *
     * @param query
     * @return
     */
    public LiveData<List<FlickrImage>> getCachedImages(final String query) {
        return imageDatabase.flickrImageDao().getAllImages(query);
    }

    private void imagesFetchedForQuery(List<FlickrImage> images, String query) {
        imageDatabase.flickrImageDao().deleteImages(query);
        for (FlickrImage flickrImage : images) {
            flickrImage.query = query;
        }
        imageDatabase.flickrImageDao().addImages(images);
    }

    private void moreImagesFetchedForQuery(List<FlickrImage> images, String query) {
        for (FlickrImage flickrImage : images) {
            flickrImage.query = query;
        }
        imageDatabase.flickrImageDao().addImages(images);
    }

    private void imagesNotFetchedForQuery(String query) {
        Log.e(TAG, "Images not received !");
    }
}
