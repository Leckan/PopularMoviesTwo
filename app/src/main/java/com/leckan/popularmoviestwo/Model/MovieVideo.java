package com.leckan.popularmoviestwo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Leckan on 6/26/2017.
 */

public class MovieVideo implements Parcelable {
    private String id ;
    private String iso_639_1 ;
    private String iso_3166_1 ;
    private String key ;
    private String name ;
    private String site ;
    private int size ;
    private String type ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(iso_3166_1);
        parcel.writeString(iso_639_1);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeInt(size);
        parcel.writeString(type);
    }

    public MovieVideo()
    {

    }
    public MovieVideo(Parcel parcel) {
        this.id = parcel.readString();
        this.iso_3166_1= parcel.readString();
        this.iso_639_1 = parcel.readString();
        this.key = parcel.readString();
        this.name = parcel.readString();
        this.site = parcel.readString();
        this.size = parcel.readInt();
        this.type = parcel.readString();
    }

    public static final Parcelable.Creator<MovieVideo> CREATOR = new Parcelable.Creator<MovieVideo>() {
        @Override
        public MovieVideo createFromParcel(Parcel parcel) {
            return new MovieVideo(parcel);
        }

        @Override
        public MovieVideo[] newArray(int size) {
            return new MovieVideo[size];
        }
    };
}
