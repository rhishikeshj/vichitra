package com.rhishikeshj.vichitra.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

/**
 * Created by mjolnir on 20/03/18.
 */

@Entity(tableName = "flickr_images")
public class FlickrImage implements Parcelable {

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
    @SerializedName("published")
    public Date publishedAt;

    public String query;

    @SerializedName("description")
    public String description;

    @SerializedName("author")
    public String author;

    @SerializedName("author_id")
    public String authorId;

    @SerializedName("tags")
    public String tags;

    public String getThumbnailLink() {
        return imageInfo.get("m");
    }

    public FlickrImage() {
    }

    private FlickrImage(Parcel in) {
        id = in.readInt();
        title = in.readString();
        imageLink = in.readString();

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        imageInfo = new Gson().fromJson(in.readString(), type);

        createdAt = new Date(in.readLong());
        publishedAt = new Date(in.readLong());
        query = in.readString();
        description = in.readString();
        author = in.readString();
        authorId = in.readString();
        tags = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(imageLink);
        parcel.writeString(new Gson().toJson(imageInfo));
        parcel.writeLong(createdAt.getTime());
        parcel.writeLong(publishedAt.getTime());
        parcel.writeString(query);
        parcel.writeString(description);
        parcel.writeString(author);
        parcel.writeString(authorId);
        parcel.writeString(tags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FlickrImage> CREATOR = new Creator<FlickrImage>() {
        @Override
        public FlickrImage createFromParcel(Parcel parcel) {
            return new FlickrImage(parcel);
        }

        @Override
        public FlickrImage[] newArray(int i) {
            return new FlickrImage[i];
        }
    };
}
