package com.devbrain.athome.modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mukesh Jha on 11/30/2016.
 */
public class InitResponseWrapper implements Parcelable {
    public List<Banners> lstBanners;
   // private String shareUrl;
    public List<Category> lstCategories;

    public InitResponseWrapper() {

        lstBanners = new ArrayList<Banners>();
        lstCategories = new ArrayList<Category>();
    }

    protected InitResponseWrapper(Parcel in)
    {
       // shareUrl = in.readString();

        lstBanners = new ArrayList<Banners>();
        in.readTypedList(lstBanners, Banners.CREATOR);

        lstCategories = new ArrayList<Category>();
        in.readTypedList(lstCategories, Category.CREATOR);

    }


    public List<Banners> getLstBanners() {
        return lstBanners;
    }

    public void setLstBanners(List<Banners> lstBanners) {
        this.lstBanners = lstBanners;
    }

    public List<Category> getLstCategories() {
        return lstCategories;
    }

    public void setLstCategories(List<Category> lstCategories) {
        this.lstCategories = lstCategories;
    }


 /*   public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }*/

    //    ------------------------------------------------ Parcelable Implementation --------------------------------------------------
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeString(shareUrl);
        dest.writeTypedList(lstBanners);
        dest.writeTypedList(lstCategories);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InitResponseWrapper> CREATOR = new Creator<InitResponseWrapper>() {
        @Override
        public InitResponseWrapper createFromParcel(Parcel in) {
            return new InitResponseWrapper(in);
        }

        @Override
        public InitResponseWrapper[] newArray(int size) {
            return new InitResponseWrapper[size];
        }
    };
}
