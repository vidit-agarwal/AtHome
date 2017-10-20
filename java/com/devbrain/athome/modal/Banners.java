package com.devbrain.athome.modal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mukesh Jha on 11/30/2016.
 */
public class Banners extends Base implements Parcelable
{
    private int resourceId;
    private String redirectUrl;

    public Banners()
    {}

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


    //    ------------------------------------------------ Parcelable Implementation --------------------------------------------------


    protected Banners(Parcel in) {
        redirectUrl = in.readString();
        setResourceId(in.readInt());
    }

    public static final Parcelable.Creator<Banners> CREATOR = new Parcelable.Creator<Banners>() {
        @Override
        public Banners createFromParcel(Parcel in) {
            return new Banners(in);
        }

        @Override
        public Banners[] newArray(int size) {
            return new Banners[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

        dest.writeString(redirectUrl);
        dest.writeInt(getResourceId());
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
