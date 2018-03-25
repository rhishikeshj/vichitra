package com.rhishikeshj.vichitra.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.rhishikeshj.vichitra.managers.ImageSearchManager;
import com.rhishikeshj.vichitra.models.FlickrImage;

import java.util.List;

/**
 * Created by mjolnir on 20/03/18.
 */

public class ImageSearchViewModel extends ViewModel {
    private LiveData<List<FlickrImage>> images;
    private ImageSearchManager imageSearchManager;

    public void setImageSearchManager(ImageSearchManager imageSearchManager) {
        this.imageSearchManager = imageSearchManager;
    }

    public LiveData<List<FlickrImage>> getImagesForQuery(String query) {
        if (this.imageSearchManager == null) {
            throw new IllegalStateException("Please initialize the ImageSearchManager for this view-model");
        }
        if (images == null) {
            images = imageSearchManager.getCachedImages(query);
            this.imageSearchManager.searchForImages(query);
        }

        return images;
    }

    public LiveData<List<FlickrImage>> getMoreImagesForQuery(String query) {
        if (this.imageSearchManager == null) {
            throw new IllegalStateException("Please initialize the ImageSearchManager for this view-model");
        }
        if (images != null) {
            this.imageSearchManager.searchForMoreImages(query);
        }

        return images;
    }

}
