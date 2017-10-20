package com.devbrain.athome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devbrain.athome.data.ProductInfo;
import com.devbrain.athome.interfaces.TotalAmountUpdateListener;
import com.devbrain.athome.modal.Utility;
import com.devbrain.athome.R;
import com.devbrain.athome.data.CartConstants;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import static com.devbrain.athome.data.CartConstants.cartItemsArrayList;

/**
 * Created by PC on 7/13/2016.
 */
public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {

    List<ProductInfo> contactList;
    Context context;
    LayoutInflater inflater;
//    ImageLoader imageLoader = ImageLoader.getInstance();
    TotalAmountUpdateListener totalAmountUpdateListener;

    public CartProductAdapter(Context context, List<ProductInfo> list) {
        this.context = context;
        this.contactList = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.confirmation_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        final ProductInfo productInfo = cartItemsArrayList.get(position);

//        Utility.setImageFromUrl(productInfo.getProdImageUrl(), holder.product_image);
//        imageLoader.displayImage(productInfo.getProdImageUrl(), holder.product_image);
        holder.img_menu.setImageResource(R.drawable.delete_icon);
        holder.img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartConstants.removeItemFromCart(productInfo);
                totalAmountUpdateListener.updateTotalAmount();
                notifyDataSetChanged();
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////

        final String prodUnit = productInfo.getProdUnit();
        final String minUnit = productInfo.getProdMinUnit();
        final int minAmount = productInfo.getProdMinOrder();


        final int orderCount = CartConstants.getProductCount(productInfo.getProdId());
        final String orderUnit = CartConstants.getProductUnit(productInfo.getProdId());
        final double orderPrice = CartConstants.getProductPrice(productInfo.getProdId());
//        Utility.setImageFromUrl(productInfo.getProdImageUrl(), holder.product_image);
//        imageLoader.displayImage(productInfo.getProdImageUrl(), holder.product_image);
        holder.product_name.setText(productInfo.getProdName());
        Glide.with(context).load(productInfo.getProdImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.product_bg).into(holder.product_image);

        holder.product_count.setText(orderCount + "");
        holder.product_unit.setText(orderUnit);
        holder.productTotalPrice.setText("Rs " + String.valueOf(orderPrice));

        holder.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = CartConstants.getProductCount(productInfo.getProdId());
                String unit = CartConstants.getProductUnit(productInfo.getProdId());

                if (unit == prodUnit) {
                    if (unit == minUnit) {
                        count = count + minAmount;
                    } else {
                        count++;
                    }
                } else {
                    if (unit == minUnit) {
                        count = count + minAmount;
                        if (count >= 1000) {
                            count = (int) Utility.lowToHigh(count);
                            unit = prodUnit;
                        }
                    }
                }

                CartConstants.updateItem(productInfo, count, unit);
                holder.product_count.setText(count + "");
                holder.product_unit.setText(unit);
                double price = CartConstants.getProductPrice(productInfo.getProdId());
                holder.productTotalPrice.setText("Rs " + String.valueOf(price));
                totalAmountUpdateListener.updateTotalAmount();

            }
        });
        holder.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = CartConstants.getProductCount(productInfo.getProdId());
                String unit = CartConstants.getProductUnit(productInfo.getProdId());
                if (unit == minUnit) {
                    count = count - minAmount;
                    if (count < minAmount) {
                        CartConstants.removeItemFromCart(productInfo);
                        totalAmountUpdateListener.updateTotalAmount();
                        notifyDataSetChanged();
                    } else {
                        CartConstants.updateItem(productInfo, count, unit);
                        holder.product_count.setText(count + "");
                        holder.product_unit.setText(minUnit);
                    }
                } else {
                    if (count <= 1) {
                        count = (int) Utility.highToLow(count);
                        count = count - minAmount;
                        unit = minUnit;
                    } else {
                        count--;
                        unit = prodUnit;
                    }
                    CartConstants.updateItem(productInfo, count, unit);
                    holder.product_count.setText(count + "");
                    holder.product_unit.setText(unit);
                }

                double price = CartConstants.getProductPrice(productInfo.getProdId());
                holder.productTotalPrice.setText("Rs " + String.valueOf(price));
                totalAmountUpdateListener.updateTotalAmount();
            }
        });
    }

    public void setTotalAmountUpdateListener(TotalAmountUpdateListener totalAmountUpdateListener) {
        this.totalAmountUpdateListener = totalAmountUpdateListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        ImageView img_menu;
        TextView product_name;
        //        TextView productTotalQuantity;
        TextView product_unit;
        ImageView product_image;
        TextView product_count;
        Button incrementButton;
        Button decrementButton;
        TextView productTotalPrice;

        public ViewHolder(View view) {
            super(view);
            product_name = (TextView) view.findViewById(R.id.product_name);
            product_unit = (TextView) view.findViewById(R.id.product_unit);
            product_image = (ImageView) view.findViewById(R.id.product_image);
            product_count = (TextView) view.findViewById(R.id.product_count);
            incrementButton = (Button) view.findViewById(R.id.inc_count_btn);
            decrementButton = (Button) view.findViewById(R.id.dec_count_btn);
            productTotalPrice = (TextView) view.findViewById(R.id.product_total_price);
//          productTotalQuantity = (TextView) view.findViewById(R.id.product_total_quantity);
            img_menu = (ImageView) view.findViewById(R.id.iv_confirm_menu);
        }
    }


//    private void setTotalQuantity(ProductInfo productInfo, double totalCount, TextView textView) {
//        double totalQuantity = productInfo.getProdPriceQuantity() * totalCount;
//        textView.setText(totalQuantity + " " + productInfo.getProdUnit());
//    }

}
