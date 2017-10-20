package com.devbrain.athome.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Honey Agarwal on 18-Apr-17.
 */

public class ProductInfo {
    public static final String PROD_ID = "prdid";
    public static final String PROD_NAME = "prdname";
    public static final String CAT_ID = "catid";
    public static final String SUB_CAT_ID = "subcatid";
    public static final String PROD_DESC = "prddesc";
    public static final String PROD_IMAGE_URL = "prdimg";
    public static final String PROD_PRICE = "prdprice";
    public static final String PROD_UNIT = "prdunit";
    public static final String PROD_DISCOUNT = "prddiscount";
    public static final String PROD_MIN_ORDER = "prdminimumorder";
    public static final String PROD_MIN_UNIT = "prdminunit";
    public static final int PRODUCT_COUNT = 0;
    public static final int PRODUCT_PRICE_QUANTITY = 1;


    int prodId;
    String prodName;
    int catId;
    int subCatId;
    String prodDesc;
    String prodImageUrl;
    int prodPrice;
    String prodUnit;
    String prodMinUnit;
    int prodDiscount;
    int prodMinOrder;
    int productCount;
    int prodPriceQuantity;
    int discountedPrice;

    public ProductInfo(JSONObject jsonObj) {
        try {
            this.prodId = jsonObj.getInt(PROD_ID);
            this.catId = jsonObj.getInt(CAT_ID);
            this.subCatId = jsonObj.getInt(SUB_CAT_ID);
            this.prodName = jsonObj.getString(PROD_NAME);
            this.prodDesc = jsonObj.getString(PROD_DESC);
            this.prodPrice = jsonObj.getInt(PROD_PRICE);
            this.prodUnit = jsonObj.getString(PROD_UNIT);
            this.prodImageUrl = jsonObj.getString(PROD_IMAGE_URL);
            this.prodDiscount = jsonObj.getInt(PROD_DISCOUNT);
            this.prodMinOrder = jsonObj.getInt(PROD_MIN_ORDER);
            this.prodMinUnit = jsonObj.getString(PROD_MIN_UNIT);
            this.productCount = PRODUCT_COUNT;
            this.prodPriceQuantity = PRODUCT_PRICE_QUANTITY;
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

    public String getProdUnit() {
        return prodUnit;
    }

    public int getCatId() {
        return catId;
    }

    public int getSubCatId() {
        return subCatId;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public String getProdImageUrl() {
        return prodImageUrl;
    }

    public int getProdPrice() {
        return prodPrice;
    }

    public int getProdMinOrder() {
        return prodMinOrder;
    }

    public String getProdMinUnit() {
        return prodMinUnit;
    }

    public int getProdDiscount() {
        return prodDiscount;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getProdPriceQuantity() {
        return prodPriceQuantity;
    }

    public void setProdPriceQuantity(int prodPriceQuantity) {
        this.prodPriceQuantity = prodPriceQuantity;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }
}
