package com.devbrain.athome.data;

import android.util.Log;

import com.devbrain.athome.parser.OrdProdJSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Honey Agarwal on 27-Apr-17.
 */

public class OrderInfo {

    public static final String ORDER_ID = "orderid";
    public static final String USER_NAME = "name";
    public static final String USER_MOBILE = "mobile";
    public static final String USER_EMAIL = "email";
    public static final String ORDER_TOTAL_PAY = "totpay";
    public static final String ORDER_STATUS = "orderstatus";
    public static final String ORDER_ITEMS = "items";
    public static final String ORDER_ADDRESS = "orderadress";
    public static final String ORDER_DATE = "orderdate";
    public static final String DELIVERY_DATE = "deliverydate";


    int orderId;
    int totalPay;
    String userName;
    String userMobile;
    String userEmail;
    String orderStatus;
    String orderAddress;
    String orderDate;
    String deliveryDate;
    JSONArray itemsJsonArray;
    ArrayList<OrdProdItem> ordProdItems = new ArrayList<>();

    public OrderInfo(JSONObject jsonObj) {
        try {
            this.orderId = jsonObj.getInt(ORDER_ID);
            this.totalPay = jsonObj.getInt(ORDER_TOTAL_PAY);
            this.orderStatus = jsonObj.getString(ORDER_STATUS);
            this.orderAddress = jsonObj.getString(ORDER_ADDRESS);
            this.itemsJsonArray = jsonObj.getJSONArray(ORDER_ITEMS);
            this.userName = jsonObj.getString(USER_NAME);
            this.userMobile = jsonObj.getString(USER_MOBILE);
            this.userEmail = jsonObj.getString(USER_EMAIL);
            if (jsonObj.has(ORDER_DATE))
                this.orderDate = jsonObj.getString(ORDER_DATE);
            if (jsonObj.has(DELIVERY_DATE))
                this.deliveryDate = jsonObj.getString(DELIVERY_DATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OrdProdJSONParser ordProdJSONParser = new OrdProdJSONParser(itemsJsonArray);
        ordProdItems = ordProdJSONParser.getProductArrayList();
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTotalPay() {
        return totalPay;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public ArrayList<OrdProdItem> getOrdProdItems() {
        return ordProdItems;
    }

    public int getOrderItemsCount() {
        return ordProdItems.size();
    }

    public String getOrderDate() {
        return getFormattedDate(orderDate);
    }

    public String getDeliveryDate() {
        return getFormattedDate(deliveryDate);
    }

    public String getFormattedDate(String objectCreatedDateString) {
        if (objectCreatedDateString != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date objectCreatedDate = null;
            try {
                objectCreatedDate = format.parse(objectCreatedDateString);
            } catch (ParseException e) {
                Log.e("Order Info", e.getMessage());
            }

            String date = new SimpleDateFormat("dd MMM yyyy \nhh:mm aa").format(objectCreatedDate);
            return date;
        } else {
            return null;
        }
    }
}
