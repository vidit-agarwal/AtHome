package com.devbrain.athome.data;

import com.devbrain.athome.modal.Utility;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Honey Agarwal on 20-Apr-17.
 */

public class CartConstants {

    public static int SHOW_CART_REQUEST_CODE = 11;
    public static int REGISTER_USER_REQUEST_CODE = 22;
    public static int PAYMENT_REQUEST_CODE = 33;
    public static ProductInfo clickedProductInfo;
    public static OrderInfo clickedOrderInfo;
    public static HashMap<Integer, Integer> productCount = new HashMap<>();
    public static HashMap<Integer, String> productUnit = new HashMap<>();
    public static HashMap<Integer, Double> productPrice = new HashMap<>();
    public static ArrayList<ProductInfo> cartItemsArrayList = new ArrayList<>();
    public static ArrayList<CategoryItem> categoryItemArrayList = new ArrayList<>();
    public static ArrayList<CategoryItem> bannerItemArrayList = new ArrayList<>();
    public static HashMap<Integer, ArrayList<ProductInfo>> productHashMap = new HashMap<>();
//    public static HashMap<Integer, ArrayList<SubCategoryItem>> subCategoryHashMap = new HashMap<>();

    public static boolean addItemToCart(ProductInfo productInfo, int count, String unit) {

        if (!cartItemsArrayList.contains(productInfo)) {
            cartItemsArrayList.add(productInfo);
            productCount.put(productInfo.getProdId(), count);
            productUnit.put(productInfo.getProdId(), unit);
            double price = getTotalPrice(productInfo, count, unit);
            productPrice.put(productInfo.getProdId(), price);
            return true;
        }
        return false;
    }


    public static boolean removeItemFromCart(ProductInfo productInfo) {

        if (cartItemsArrayList.contains(productInfo)) {
            cartItemsArrayList.remove(productInfo);
            productCount.remove(productInfo.getProdId());
            productUnit.remove(productInfo.getProdId());
            productPrice.remove(productInfo.getProdId());
            return true;
        }
        return false;
    }

    public static void updateItem(ProductInfo productInfo, int count, String unit) {
        if (cartItemsArrayList.contains(productInfo)) {
            productCount.put(productInfo.getProdId(), count);
            productUnit.put(productInfo.getProdId(), unit);
            double price = getTotalPrice(productInfo, count, unit);
            productPrice.put(productInfo.getProdId(), price);
        }
    }

    public static int getProductCount(int productId) {
        if (productCount.containsKey(productId)) {
            return productCount.get(productId);
        } else {
            return 0;
        }
    }

    public static String getProductUnit(int productId) {
        if (productUnit.containsKey(productId)) {
            return productUnit.get(productId);
        } else {
            return "";
        }
    }

    public static double getProductPrice(int productId) {
        if (productPrice.containsKey(productId)) {
            return productPrice.get(productId);
        } else {
            return 0;
        }
    }


    public static void setClickedProductInfo(ProductInfo clickedProductInfo) {
        CartConstants.clickedProductInfo = clickedProductInfo;
    }

    public static ProductInfo getClickedProductInfo() {
        return clickedProductInfo;
    }

    public static HashMap<Integer, Integer> getProductCount() {
        return productCount;
    }

    public static HashMap<Integer, String> getProductUnit() {
        return productUnit;
    }

    public static ArrayList<ProductInfo> getItemFromProductHashMap(Integer catID) {
        return productHashMap.get(catID);
    }

    public static void addItemToProductHashMap(Integer catID, ArrayList<ProductInfo> productInfos) {
        productHashMap.put(catID, productInfos);
    }

//    public static ArrayList<SubCategoryItem> getItemFromSubCategoryArrayList(Integer catID) {
//        return subCategoryHashMap.get(catID);
//    }
//
//    public static void addItemToSubCategoryArrayList(Integer catID, ArrayList<SubCategoryItem> subCategoryItems) {
//        subCategoryHashMap.put(catID, subCategoryItems);
//    }

    public static void setCategoryItemArrayList(ArrayList<CategoryItem> categoryItemArrayList) {
        CartConstants.categoryItemArrayList = categoryItemArrayList;
    }

    public static ArrayList<CategoryItem> getCategoryItemArrayList() {
        return categoryItemArrayList;
    }

    public static void setBannerItemArrayList(ArrayList<CategoryItem> bannerItemArrayList) {
        CartConstants.bannerItemArrayList = bannerItemArrayList;
    }

    public static ArrayList<CategoryItem> getBannerItemArrayList() {
        return bannerItemArrayList;
    }

    public static String getBannerForId(int categoryId) {
        String path = null;
        for (CategoryItem categoryItem : CartConstants.getBannerItemArrayList()) {
            if (categoryItem.getCategoryId() == categoryId) {
                path = categoryItem.getCategoryImagePath();
                break;
            }
        }
        return path;
    }

    public static ArrayList<ProductInfo> getCartItemsArrayList() {
        return cartItemsArrayList;
    }

    public static OrderInfo getClickedOrderInfo() {
        return clickedOrderInfo;
    }

    public static void setClickedOrderInfo(OrderInfo clickedOrderInfo) {
        CartConstants.clickedOrderInfo = clickedOrderInfo;
    }

    public static double getTotalPrice(ProductInfo productInfo, int count, String unit) {
        double totalPrice = 0;
        if (Utility.isUnitHigh(unit)) {
            totalPrice = (productInfo.getDiscountedPrice() * count);
        } else {
            totalPrice = productInfo.getDiscountedPrice() * Utility.lowToHigh(count);
        }

        return totalPrice;
    }

}
