package com.rhishikeshj.vichitra.storage;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by mjolnir on 20/03/18.
 */

@Dao
public interface FlickrImageDao {
    @Insert
    void addImages(FlickrResult... newImages);

    @Delete
    void deleteImages(FlickrResult... images);

    @Query("select * from flickr_images where query like :query")
    LiveData<List<FlickrResult>> getAllImages(String query);
}
