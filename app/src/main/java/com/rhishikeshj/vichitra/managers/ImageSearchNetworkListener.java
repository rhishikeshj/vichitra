package com.rhishikeshj.vichitra.managers;

import com.rhishikeshj.vichitra.models.FlickrImage;

import java.util.List;

/**
 * Created by mjolnir on 20/03/18.
 */

public interface ImageSearchNetworkListener {

    /**
     * Success handler for the Image Service.
     *
     * @param images
     * @param query
     */
    void imagesFetchedForQuery(List<FlickrImage> images, String query);

    /**
     * Error handler for the Image service.
     *
     * @param query
     */
    void imagesNotFetchedForQuery(String query);
}
