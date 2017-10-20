package com.devbrain.athome.data;

/**
 * Created by Honey Agarwal on 18-May-17.
 */

public class CartItem {

    int productCount;
    String productUnit;
    int totalAmount;


    public CartItem(int productCount, String productUnit, int totalAmount) {
        this.productCount = productCount;
        this.productUnit = productUnit;
        this.totalAmount = totalAmount;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
