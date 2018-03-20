package com.rhishikeshj.vichitra.storage;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.List;

/**
 * Created by mjolnir on 20/03/18.
 */

@Entity(tableName = "flickr_images")
public class FlickrResult {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String query;
    public String imageLink;
    public String thumbnailLink;
    public String description;

    public Date createdAt;
    public Date publishedAt;
    public String author;
    public String authorId;

    public List<String> tags;
}
