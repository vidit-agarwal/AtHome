package com.devbrain.athome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devbrain.athome.interfaces.CartItemUpdateListener;
import com.devbrain.athome.R;
import com.devbrain.athome.data.OrdProdItem;

import java.util.Collections;
import java.util.List;

//import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Honey on 6/10/2016.
 */
public class OrderProdRecyclerAdapter extends RecyclerView.Adapter<OrderProdRecyclerAdapter.ViewHolder> {
    List<OrdProdItem> listFileInfos = Collections.EMPTY_LIST;
    private LayoutInflater inflater;
    //    Activity activity;
    Context mContext;
    //    ImageLoader imageLoader;
    CartItemUpdateListener cartItemUpdateListener;

    public OrderProdRecyclerAdapter(Context context, List<OrdProdItem> listFileInfos) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.listFileInfos = listFileInfos;
//        imageLoader = MGrocery.getInstance().getImageLoader();
    }

    public void setCartItemUpdateListener(CartItemUpdateListener cartItemUpdateListener) {
        this.cartItemUpdateListener = cartItemUpdateListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.order_product_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final OrdProdItem productInfo = listFileInfos.get(position);
//        Utility.setImageFromUrl(productInfo.getProdImageUrl(), holder.product_image);
//        imageLoader.displayImage(productInfo.getProdImageUrl(), holder.product_image);
//        Glide.with(mContext).load(productInfo.getProdImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.product_bg).into(holder.product_image);
        holder.product_name.setText(productInfo.getProdName() + "");
        holder.product_quantity.setText(productInfo.getProdQuantity() + " " + productInfo.getProdOrderUnit());
        holder.product_price.setText("Rs " + productInfo.getProdPrice());
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView product_name;
        TextView product_quantity;
        TextView product_price;
//        TextView product_unit;
//        Spinner quantitySpinner;
//        Spinner unitSpinner;

        public ViewHolder(View vi) {
            super(vi);
            product_name = (TextView) vi.findViewById(R.id.product_name);
            product_quantity = (TextView) vi.findViewById(R.id.product_quantity);
            product_price = (TextView) vi.findViewById(R.id.product_price);
//            product_unit = (TextView) vi.findViewById(product_unit);
//            quantitySpinner = (Spinner) vi.findViewById(R.id.quantity_spinner);
//            unitSpinner = (Spinner) vi.findViewById(R.id.unit_spinner);
        }

    }

//    public void setTotalPrice(OrdProdItem productInfo, TextView textView) {
//        double totalPrice = 0;
//        if (Utility.isUnitHigh(productInfo.getProdOrderUnit())) {
//            totalPrice = (productInfo.getDiscountedPrice() * productInfo.getProdQuantity());
//        } else {
//            totalPrice = productInfo.getDiscountedPrice() * Utility.lowToHigh(productInfo.getProdQuantity());
//        }
//        textView.setText(totalPrice + " Rs");
//    }

    @Override
    public int getItemCount() {
        return listFileInfos.size();
    }


}
