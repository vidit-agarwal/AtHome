package com.devbrain.athome.parser;

import com.devbrain.athome.data.ProductInfo;
import com.devbrain.athome.modal.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Honey Agarwal on 23-Mar-17.
 */

public class ProductJSONParser {
    ArrayList<ProductInfo> productArrayList = new ArrayList<>();
    public static final String JSON_STATUS = "status";
    public static final String JSON_PRODUCTS = "products";


    public ProductJSONParser(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
//          int  status = jsonObject.getInt(JSON_STATUS);

            JSONArray products = jsonObject.getJSONArray(JSON_PRODUCTS);
            Utility.printLog("init success: " + products.length());

            for (int i = 0; i < products.length(); i++) {
                JSONObject jsonObj = products.getJSONObject(i);
                ProductInfo productInfo = new ProductInfo(jsonObj);
                productArrayList.add(productInfo);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ProductInfo> getProductArrayList() {
        return productArrayList;
    }
}