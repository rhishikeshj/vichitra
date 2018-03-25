package com.rhishikeshj.vichitra.services;

import com.rhishikeshj.vichitra.models.FlickrImage;

import java.util.List;

/**
 * Created by mjolnir on 20/03/18.
 */

public interface ImageService {
    /**
     * API for getting images for a given query
     *
     * @param query
     * @return
     */
    List<FlickrImage> getImagesForQuery(String query);
}
