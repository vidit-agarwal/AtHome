package com.devbrain.athome.parser;

import com.devbrain.athome.data.SubCategoryItem;
import com.devbrain.athome.modal.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Honey Agarwal on 23-Mar-17.
 */

public class SubCategoryJSONParser {
    ArrayList<SubCategoryItem> subCategoryArrayList = new ArrayList<>();
    public static final String JSON_STATUS = "status";
    public static final String JSON_SUB_CATEGORIES = "subCategories";

    public SubCategoryJSONParser(String json) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
//           int status = jsonObject.getInt(JSON_STATUS);
            JSONArray subCategories = jsonObject.getJSONArray(JSON_SUB_CATEGORIES);
            Utility.printLog("init success: " + subCategories.length());

            for (int i = 0; i < subCategories.length(); i++) {
                JSONObject jsonObj = subCategories.getJSONObject(i);
                SubCategoryItem subCategoryItem = new SubCategoryItem(jsonObj);
                subCategoryArrayList.add(subCategoryItem);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SubCategoryItem> getSubCategoryArrayList() {
        return subCategoryArrayList;
    }
}