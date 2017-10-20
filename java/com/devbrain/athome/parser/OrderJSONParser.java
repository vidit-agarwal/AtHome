package com.devbrain.athome.parser;

import com.devbrain.athome.data.OrderInfo;
import com.devbrain.athome.modal.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Honey Agarwal on 23-Mar-17.
 */

public class OrderJSONParser {
    ArrayList<OrderInfo> orderArrayList = new ArrayList<>();
    public static final String JSON_STATUS = "status";
    public static final String JSON_ORDERS = "orders";


    public OrderJSONParser(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
//          int  status = jsonObject.getInt(JSON_STATUS);

            JSONArray products = jsonObject.getJSONArray(JSON_ORDERS);
            Utility.printLog(" OrderJSONParser count: " + products.length());

            for (int i = 0; i < products.length(); i++) {
                JSONObject jsonObj = products.getJSONObject(i);
                OrderInfo orderInfo = new OrderInfo(jsonObj);
                orderArrayList.add(orderInfo);
            }

        } catch (JSONException e) {
            Utility.printLog("onError: " + e.getMessage());
        }
    }

    public ArrayList<OrderInfo> getOrderArrayList() {
        return orderArrayList;
    }
}