package com.devbrain.athome.modal;

import android.os.Parcelable;

/**
 * Created by Mukesh Jha on 11/30/2016.
 */
public abstract class Base implements Parcelable
{
    private String id;
    private String iconUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
