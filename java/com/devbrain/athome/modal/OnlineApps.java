package com.devbrain.athome.modal;

/**
 * Created by Nitin Bansal on 3/6/2017.
 */
public class OnlineApps {
    private int id;
    private String name;
    private String desc;
    private String imageUrl;
    private String playStoreUrl;
    private boolean isDownload;

    public OnlineApps(String itemName, int _id, String url, String storeUrl, String description, boolean isDownload) {
        super();
        this.name = itemName;
        this.id = _id;
        this.playStoreUrl = storeUrl;
        this.imageUrl = url;
        this.desc = description;
        this.isDownload = isDownload;
    }

    public String getPlayStoreUrl() {
        return playStoreUrl;
    }

    public void setPlayStoreUrl(String playStoreUrl) {
        this.playStoreUrl = playStoreUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void getImageUrl(String url) {
        this.imageUrl = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return name;
    }

    public void setItemName(String itemName) {
        name = itemName;
    }

    public boolean getisDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }
}
