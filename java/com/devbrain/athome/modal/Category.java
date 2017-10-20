package com.devbrain.athome.modal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mukesh Jha on 11/30/2016.
 */
public class Category extends Base
{
    private String name;
    private String shortName;
    private String description;
    private String downloadRedirectUrl;

    public Category()
    {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadRedirectUrl() {
        return downloadRedirectUrl;
    }

    public void setDownloadRedirectUrl(String downloadRedirectUrl) {
        this.downloadRedirectUrl = downloadRedirectUrl;
    }

    //    ------------------------------------------------ Parcelable Implementation --------------------------------------------------

    protected Category(Parcel in) {
        name = in.readString();
        shortName = in.readString();
        description = in.readString();
        downloadRedirectUrl = in.readString();
        setId(in.readString());
        setIconUrl(in.readString());

    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(shortName);
        dest.writeString(description);
        dest.writeString(downloadRedirectUrl);
        dest.writeString(getId());
        dest.writeString(getIconUrl());
    }
}
