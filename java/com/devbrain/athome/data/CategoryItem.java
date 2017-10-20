package com.devbrain.athome.data;

import org.json.JSONException;
import org.json.JSONObject;

public class CategoryItem {

    public static final String CAT_ID = "catid";
    public static final String CAT_NAME = "catname";
    public static final String CAT_DESC = "catdesc";
    public static final String CAT_ING = "catimg";

    int categoryId;
    String categoryName;
    String categoryDesc;
    String categoryImagePath;



    public CategoryItem(JSONObject jsonObj) {
        try {
            this.categoryId = jsonObj.getInt(CAT_ID);
            this.categoryName = jsonObj.getString(CAT_NAME);
            this.categoryDesc = jsonObj.getString(CAT_DESC);
            this.categoryImagePath = jsonObj.getString(CAT_ING);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public String getCategoryImagePath() {
        return categoryImagePath;
    }
}