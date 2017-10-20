package com.devbrain.athome.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devbrain.athome.R;
import com.devbrain.athome.activity.OrderHistoryDetailsActivity;
import com.devbrain.athome.data.CartConstants;
import com.devbrain.athome.data.OrderInfo;

import java.util.Collections;
import java.util.List;

/**
 * Created by Honey on 6/10/2016.
 */
public class OrderHistoryRecyclerAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerAdapter.ViewHolder> {
    List<OrderInfo> listFileInfos = Collections.EMPTY_LIST;
    private LayoutInflater inflater;
    Activity activity;

    public OrderHistoryRecyclerAdapter(Context context, List<OrderInfo> listFileInfos) {
        inflater = LayoutInflater.from(context);
        this.listFileInfos = listFileInfos;
        this.activity = (Activity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.order_item_list, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final OrderInfo orderInfo = listFileInfos.get(position);
//        holder.user_name.setText(orderInfo.getUserName() + "");
//        holder.user_number.setText(orderInfo.getUserMobile() + "");
        holder.order_id.setText("Order No: " + orderInfo.getOrderId());
        holder.product_count.setText("Total: " + orderInfo.getOrderItemsCount() + " Items");
        holder.order_date.setText("" + orderInfo.getOrderDate());

        holder.order_status.setText(orderInfo.getOrderStatus());
        holder.order_amount.setText("Total: Rs " + orderInfo.getTotalPay() );

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartConstants.setClickedOrderInfo(orderInfo);
                activity.startActivity(new Intent(activity, OrderHistoryDetailsActivity.class));
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        //        TextView user_name;
//        TextView user_number;
        CardView cardView;
        TextView order_id;
        TextView order_amount;
        TextView product_count;
        TextView order_status;
        TextView order_date;

        public ViewHolder(View vi) {
            super(vi);
//            user_name = (TextView) vi.findViewById(R.id.order_name);
//            user_number = (TextView) vi.findViewById(R.id.order_number);
            cardView = (CardView) vi.findViewById(R.id.card_view);
            order_id = (TextView) vi.findViewById(R.id.order_id);
            order_amount = (TextView) vi.findViewById(R.id.order_amount);
            product_count = (TextView) vi.findViewById(R.id.order_count);
            order_status = (TextView) vi.findViewById(R.id.order_status);
            order_date = (TextView) vi.findViewById(R.id.order_date);
        }

    }

    @Override
    public int getItemCount() {
        return listFileInfos.size();
    }


}
