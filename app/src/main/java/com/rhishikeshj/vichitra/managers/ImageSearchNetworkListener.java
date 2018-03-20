package com.rhishikeshj.vichitra.managers;

import com.rhishikeshj.vichitra.models.FlickrImage;

import java.util.List;

/**
 * Created by mjolnir on 20/03/18.
 */

public interface ImageSearchNetworkListener {

    void imagesFetchedForQuery(List<FlickrImage> images, String query);

    void imagesNotFetchedForQuery(String query);
}
