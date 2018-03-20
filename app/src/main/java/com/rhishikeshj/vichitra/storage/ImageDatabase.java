package com.rhishikeshj.vichitra.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.rhishikeshj.vichitra.storage.util.Converters;

/**
 * Created by mjolnir on 20/03/18.
 */

@Database(entities = {FlickrResult.class}, version = 1)
@TypeConverters({Converters.class})

public abstract class ImageDatabase extends RoomDatabase {
    public abstract FlickrImageDao flickrImageDao();
}
