package com.devbrain.athome.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devbrain.athome.R;
import com.devbrain.athome.activity.ProductDetailsActivity;
import com.devbrain.athome.data.CartConstants;
import com.devbrain.athome.data.ProductInfo;
import com.devbrain.athome.interfaces.CartItemUpdateListener;
import com.devbrain.athome.modal.Utility;

import java.util.ArrayList;
import java.util.List;

//import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Honey on 6/10/2016.
 */
public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {
    private List<ProductInfo> listFileInfos = new ArrayList<>();
    private LayoutInflater inflater;
    //    Activity activity;
    private Context mContext;
    private int THOUSAND = 1000;
    private int HUNDRED = 100;
    //    private ImageLoader imageLoader;
    private CartItemUpdateListener cartItemUpdateListener;

    public ProductRecyclerAdapter(Context context, List<ProductInfo> listFileInfos) {
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

        View view = inflater.inflate(R.layout.product_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ProductInfo productInfo = listFileInfos.get(position);
        final String prodUnit = productInfo.getProdUnit();
//        final int minAmount = productInfo.getProdMinOrder();

        final int orderCount = CartConstants.getProductCount(productInfo.getProdId());
        final String orderUnit = CartConstants.getProductUnit(productInfo.getProdId());
        int minOrder = productInfo.getProdMinOrder();
        String minUnit = productInfo.getProdMinUnit();
        if (minOrder >= THOUSAND && !minUnit.equals(prodUnit)) {
            minOrder = (int) Utility.lowToHigh(minOrder);
            minUnit = prodUnit;
        }
        final int prodMinOrder = minOrder;
        final String prodMinUnit = minUnit;
        holder.product_min_order.setText("min: " + prodMinOrder + " " + prodMinUnit);

//        Utility.setImageFromUrl(productInfo.getProdImageUrl(), holder.product_image);
//        imageLoader.displayImage(productInfo.getProdImageUrl(), holder.product_image);

        if (productInfo.getProdImageUrl() != null) {
            Glide.with(mContext).load(productInfo.getProdImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(80, 80).placeholder(R.drawable.product_bg).into(holder.product_image);
        } else {
            Glide.clear(holder.product_image);
            holder.product_image.setImageResource(R.drawable.product_bg);
        }

        holder.product_name.setText(productInfo.getProdName());
        if (productInfo.getProdDiscount() > 0) {
            holder.discount_price.setText("Rs " + productInfo.getProdPrice() + "/" + prodUnit);
            holder.discount_price.setPaintFlags(holder.product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.product_price.setText("Rs " + productInfo.getDiscountedPrice() + "/" + prodUnit);
        } else {
            holder.discount_price.setVisibility(View.GONE);
            holder.product_price.setText("Rs " + productInfo.getProdPrice() + "/" + prodUnit);
        }

        holder.product_count.setText(String.valueOf(orderCount));
        holder.product_unit.setText(orderUnit);

        if (orderCount == 0) {
            holder.addToCartButton.setVisibility(View.VISIBLE);
            holder.countLayout.setVisibility(View.GONE);
        } else {
            holder.addToCartButton.setVisibility(View.GONE);
            holder.countLayout.setVisibility(View.VISIBLE);
        }

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = productInfo.getProdMinOrder();
                String unit = productInfo.getProdMinUnit();
                if (count >= THOUSAND && !unit.equals(prodUnit)) {
                    count = (int) Utility.lowToHigh(count);
                    unit = prodUnit;
                }
                CartConstants.addItemToCart(productInfo, count, unit);
                holder.addToCartButton.setVisibility(View.GONE);
                holder.countLayout.setVisibility(View.VISIBLE);
                holder.product_count.setText(String.valueOf(count));
                holder.product_unit.setText(unit);
                cartItemUpdateListener.onItemUpdatedListener();

            }
        });
        holder.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = CartConstants.getProductCount(productInfo.getProdId());
                String unit = CartConstants.getProductUnit(productInfo.getProdId());

                if (unit.equals(prodUnit)) {
                    if (unit.equals(prodMinUnit)) {
                        count = count + prodMinOrder;
                    } else {
                        count++;
                    }
                } else {
                    if (unit.equals(prodMinUnit)) {
                        count = count + prodMinOrder;
                        if (count >= THOUSAND) {
                            count = (int) Utility.lowToHigh(count);
                            unit = prodUnit;
                        }
                    }
                }

                CartConstants.updateItem(productInfo, count, unit);
                holder.product_count.setText(String.valueOf(count));
                holder.product_unit.setText(unit);
            }
        });
        holder.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = CartConstants.getProductCount(productInfo.getProdId());
                String unit = CartConstants.getProductUnit(productInfo.getProdId());
                if (unit.equals(prodMinUnit)) {
                    count = count - prodMinOrder;
                    if (count < prodMinOrder) {
                        CartConstants.removeItemFromCart(productInfo);
                        holder.addToCartButton.setVisibility(View.VISIBLE);
                        holder.countLayout.setVisibility(View.GONE);
                        cartItemUpdateListener.onItemUpdatedListener();
                    } else {
                        CartConstants.updateItem(productInfo, count, unit);
                        holder.product_count.setText(String.valueOf(count));
                        holder.product_unit.setText(prodMinUnit);
                    }
                } else {
                    if (count <= 1) {
                        count = (int) Utility.highToLow(count);
                        count = count - prodMinOrder;
                        unit = prodMinUnit;
                    } else {
                        count--;
                        unit = prodUnit;
                    }
                    CartConstants.updateItem(productInfo, count, unit);
                    holder.product_count.setText(String.valueOf(count));
                    holder.product_unit.setText(unit);
                }

            }
        });

        holder.product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartConstants.setClickedProductInfo(productInfo);
                mContext.startActivity(new Intent(mContext, ProductDetailsActivity.class));
            }
        });
        holder.detailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartConstants.setClickedProductInfo(productInfo);
                mContext.startActivity(new Intent(mContext, ProductDetailsActivity.class));
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView product_name;
        TextView product_min_order;
        TextView product_price;
        ImageView product_image;
        TextView discount_price;
        TextView product_count;
        TextView product_unit;
        Button incrementButton;
        Button decrementButton;
        Button addToCartButton;
        LinearLayout countLayout;
        LinearLayout detailsLayout;
        RelativeLayout mainLayout;
//        Spinner quantitySpinner;
//        Spinner unitSpinner;

        public ViewHolder(View vi) {
            super(vi);
            product_name = (TextView) vi.findViewById(R.id.product_name);
            product_min_order = (TextView) vi.findViewById(R.id.product_min_order);
            product_price = (TextView) vi.findViewById(R.id.product_price);
            product_image = (ImageView) vi.findViewById(R.id.product_image);
            discount_price = (TextView) vi.findViewById(R.id.discount_price);
            product_count = (TextView) vi.findViewById(R.id.product_count);
            product_unit = (TextView) vi.findViewById(R.id.product_unit);
            incrementButton = (Button) vi.findViewById(R.id.inc_count_btn);
            decrementButton = (Button) vi.findViewById(R.id.dec_count_btn);
            addToCartButton = (Button) vi.findViewById(R.id.addToCartButton);
            countLayout = (LinearLayout) vi.findViewById(R.id.count_layout);
            detailsLayout = (LinearLayout) vi.findViewById(R.id.details_layout);
            mainLayout = (RelativeLayout) vi.findViewById(R.id.lytMain);
//            quantitySpinner = (Spinner) vi.findViewById(R.id.quantity_spinner);
//            unitSpinner = (Spinner) vi.findViewById(R.id.unit_spinner);
        }

    }

    @Override
    public int getItemCount() {
        return listFileInfos.size();
    }

}
