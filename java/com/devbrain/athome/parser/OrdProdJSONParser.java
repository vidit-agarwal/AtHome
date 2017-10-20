package com.devbrain.athome.parser;

import com.devbrain.athome.data.OrdProdItem;
import com.devbrain.athome.modal.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Honey Agarwal on 23-Mar-17.
 */

public class OrdProdJSONParser {
    ArrayList<OrdProdItem> productArrayList = new ArrayList<>();
    public static final String JSON_STATUS = "status";
    public static final String JSON_PRODUCTS = "items";


    public OrdProdJSONParser(JSONArray products) {
        JSONObject jsonObject = null;
        try {
            Utility.printLog(" OrdProdJSONParser count: " + products.length());

            for (int i = 0; i < products.length(); i++) {
                JSONObject jsonObj = products.getJSONObject(i);
                OrdProdItem productInfo = new OrdProdItem(jsonObj);
                productArrayList.add(productInfo);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<OrdProdItem> getProductArrayList() {
        return productArrayList;
    }
}