package com.devbrain.athome.parser;

import com.devbrain.athome.data.CategoryItem;
import com.devbrain.athome.modal.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Honey Agarwal on 23-Mar-17.
 */

public class CategoryJSONParser {
    ArrayList<CategoryItem> categoryArrayList = new ArrayList<>();
    public static final String JSON_STATUS = "status";
    public static final String JSON_CATEGORIES = "categories";


    public CategoryJSONParser(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
//          int  status = jsonObject.getInt(JSON_STATUS);

            JSONArray categories = jsonObject.getJSONArray(JSON_CATEGORIES);
            Utility.printLog("init success: " + categories.length());

            for (int i = 0; i < categories.length(); i++) {
                JSONObject jsonObj = categories.getJSONObject(i);
                CategoryItem categoryItem = new CategoryItem(jsonObj);
                categoryArrayList.add(categoryItem);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CategoryItem> getCategoryArrayList() {
        return categoryArrayList;
    }
}