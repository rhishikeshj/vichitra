package com.rhishikeshj.vichitra.storage;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rhishikeshj.vichitra.models.FlickrImage;

import java.util.List;

/**
 * Created by mjolnir on 20/03/18.
 */

@Dao
public interface FlickrImageDao {
    @Insert
    void addImages(List<FlickrImage> newImages);

    @Delete
    void deleteImages(List<FlickrImage> images);

    @Query("select * from flickr_images where query like :query")
    LiveData<List<FlickrImage>> getAllImages(String query);
}
