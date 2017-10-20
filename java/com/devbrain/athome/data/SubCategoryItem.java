package com.devbrain.athome.data;

import org.json.JSONException;
import org.json.JSONObject;


public class SubCategoryItem {

    public static final String SUB_CAT_ID = "subcatid";
    public static final String SUB_CAT_NAME = "subcatname";
    public static final String SUB_CAT_DESC = "subcatdesc";
    public static final String SUB_CAT_ING = "subcatimg";

    int subCategoryId;
    String subCategoryName;
    String subCategoryDesc;
    String subCategoryImagePath;


    public SubCategoryItem(JSONObject jsonObj) {
        try {
            this.subCategoryId = jsonObj.getInt(SUB_CAT_ID);
            this.subCategoryName = jsonObj.getString(SUB_CAT_NAME);
            this.subCategoryDesc = jsonObj.getString(SUB_CAT_DESC);
            this.subCategoryImagePath = jsonObj.getString(SUB_CAT_ING);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public static String getSubCatDesc() {
        return SUB_CAT_DESC;
    }

    public String getSubCategoryImagePath() {
        return subCategoryImagePath;
    }
}