package com.devbrain.athome.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devbrain.athome.R;
import com.devbrain.athome.activity.HomeMainActivity;
import com.devbrain.athome.adapter.ProductRecyclerAdapter;
import com.devbrain.athome.data.CartConstants;
import com.devbrain.athome.data.CategoryItem;
import com.devbrain.athome.data.ProductInfo;
import com.devbrain.athome.interfaces.CartItemUpdateListener;
import com.devbrain.athome.modal.Device;
import com.devbrain.athome.modal.Utility;
import com.devbrain.athome.parser.ProductJSONParser;
import com.devbrain.athome.rest.NetworkTransaction;
import com.devbrain.athome.rest.NetworkTransactionListener;
import com.devbrain.athome.rest.RestAPIURL;
import com.devbrain.athome.rest.TransactionType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.nostra13.universalimageloader.core.ImageLoader;

public class ProductListFragment extends com.devbrain.athome.fragment.BaseFragment {

    ProductRecyclerAdapter productRecyclerAdapter;
    ArrayList<ProductInfo> productInfoArrayList;
    ImageLoader imageLoader;
    RecyclerView productRecyclerView;
    ImageView categoryImageView;
    int categoryID;
    String TAG = "ProductListFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: "+categoryID);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        setRetainInstance(true);
//        imageLoader = MGrocery.getInstance().getImageLoader();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        productRecyclerView = (RecyclerView) view.findViewById(R.id.product_listview);
        categoryImageView = (ImageView) view.findViewById(R.id.category_image);
        productInfoArrayList = new ArrayList<>();
        productRecyclerAdapter = new ProductRecyclerAdapter(getContext(), productInfoArrayList);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productRecyclerView.setAdapter(productRecyclerAdapter);
        productRecyclerView.setNestedScrollingEnabled(false);

        Bundle bundle = getArguments();
        categoryID = bundle.getInt(CategoryItem.CAT_ID);
        String bannerPath = CartConstants.getBannerForId(categoryID);
//        imageLoader.displayImage(bannerPath, categoryImageView);
        if (CartConstants.productHashMap.containsKey(categoryID)) {
            productInfoArrayList.addAll(CartConstants.getItemFromProductHashMap(categoryID));
            productRecyclerAdapter.notifyDataSetChanged();
            Log.d(TAG, "productHashMap containsKey: " + categoryID);
        } else {
            Log.d(TAG, "productHashMap creating list: " + categoryID);
            getProductList(getActivity());
        }
        productRecyclerAdapter.notifyDataSetChanged();
        productRecyclerAdapter.setCartItemUpdateListener(cartItemUpdateListener);
        Glide.with(getActivity()).load(bannerPath).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.banner_bg).into(categoryImageView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: "+categoryID);
        productRecyclerAdapter.notifyDataSetChanged();
        cartItemUpdateListener.onItemUpdatedListener();
    }

    public void getProductList(final Activity activity) {

        JSONObject jObjDeviceInfo = Device.getInstance(activity).getDeviceIdentityJSON();
        try {
            jObjDeviceInfo.put(CategoryItem.CAT_ID, categoryID);
//            jObjDeviceInfo.put(SubCategoryItem.SUB_CAT_ID, subCategoryID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkTransaction.getInstance(activity).ProcessRequest(TransactionType.POST, RestAPIURL.PRODUCTS_URL, jObjDeviceInfo, new NetworkTransactionListener<String>() {
            @Override
            public void onSuccess(String object, Object obj) {
                Utility.printLog("onSuccess: " + object);
//                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
                ProductJSONParser productJSONParser = new ProductJSONParser(object);
                productInfoArrayList.addAll(productJSONParser.getProductArrayList());
//                productInfoArrayListVisible.addAll(productInfoArrayList);
                CartConstants.addItemToProductHashMap(categoryID, productInfoArrayList);
                productRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String object, Object obj) {
                Utility.printLog("onFail: " + object);
                Toast.makeText(activity, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onExist(String object, Object obj) {
                Utility.printLog("onExist: " + object);
                Toast.makeText(activity, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkError(String object, Object obj) {
                Utility.printLog("onNetworkError: " + object);
                Toast.makeText(activity, object, Toast.LENGTH_LONG).show();

            }
        });

    }

    CartItemUpdateListener cartItemUpdateListener = new CartItemUpdateListener() {
        @Override
        public void onItemUpdatedListener() {
            HomeMainActivity.setCartItemCount();
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: "+categoryID);
    }
}
