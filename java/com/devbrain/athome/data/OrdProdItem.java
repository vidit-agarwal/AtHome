package com.devbrain.athome.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Honey Agarwal on 27-Apr-17.
 */

public class OrdProdItem {

    public static final String PROD_ID = "prdid";
    public static final String PROD_NAME = "prdname";
    public static final String PROD_IMAGE_URL = "prdimg";
    public static final String PROD_PRICE = "prdprice";
    public static final String PROD_DISCOUNT = "prddiscount";
    public static final String PROD_QUANTITY = "quantity";
    public static final String PROD_ORDER_UNIT = "prditemunit";


    int prodId = 0;
    String prodName = "";
    String prodImageUrl = "";
    String prodOrderUnit = "";
    int prodPrice = 0;
    int prodDiscount = 0;
    int prodQuantity = 0;
    int discountedPrice = 0;

    public OrdProdItem(JSONObject jsonObj) {
        try {
            this.prodId = jsonObj.getInt(PROD_ID);
            this.prodPrice = jsonObj.getInt(PROD_PRICE);
            this.prodName = jsonObj.getString(PROD_NAME);
            this.prodDiscount = jsonObj.getInt(PROD_DISCOUNT);
            this.prodQuantity = jsonObj.getInt(PROD_QUANTITY);
            this.prodOrderUnit = jsonObj.getString(PROD_ORDER_UNIT);
            this.prodImageUrl = jsonObj.getString(PROD_IMAGE_URL);
            if (prodDiscount > 0) {
                this.discountedPrice = Math.round(prodPrice * (1 - (prodDiscount * 0.01f)));
            } else {
                this.discountedPrice = prodPrice;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getProdId() {
        return prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public String getProdImageUrl() {
        return prodImageUrl;
    }

    public int getProdPrice() {
        return prodPrice;
    }

    public int getProdDiscount() {
        return prodDiscount;
    }

    public int getProdQuantity() {
        return prodQuantity;
    }

    public String getProdOrderUnit() {
        return prodOrderUnit;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }
}
