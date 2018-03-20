package com.rhishikeshj.vichitra.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Map;

/**
 * Created by mjolnir on 20/03/18.
 */

@Entity(tableName = "flickr_images")
public class FlickrImage {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @SerializedName("title")
    public String title;

    @SerializedName("link")
    public String imageLink;

    @SerializedName("media")
    public Map<String, String> imageInfo;

    @SerializedName("date_taken")
    public Date createdAt;

    public String query;

    @SerializedName("description")
    public String description;

    @SerializedName("published")
    public Date publishedAt;

    @SerializedName("author")
    public String author;

    @SerializedName("author_id")
    public String authorId;

    @SerializedName("tags")
    public String tags;

    public String getThumbnailLink() {
        return imageInfo.get("m");
    }
}
