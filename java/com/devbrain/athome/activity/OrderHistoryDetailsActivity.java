package com.devbrain.athome.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.devbrain.athome.adapter.OrderProdRecyclerAdapter;
import com.devbrain.athome.data.OrderInfo;
import com.devbrain.athome.R;
import com.devbrain.athome.data.CartConstants;

public class OrderHistoryDetailsActivity extends AppCompatActivity {

    TextView order_id;
    TextView order_amount;
    TextView product_count;
    TextView order_status;
    TextView order_date;
    OrderInfo orderInfo;
    TextView nameView;
    TextView addressView;
    TextView mobileView;
    TextView emailView;
    TextView grandTotal;
    //    TextView paymentMode;
//    TextView estimatedDelivery;
    NestedScrollView nestedScrollView;
    RecyclerView orderItemRecyclerView;
    OrderProdRecyclerAdapter orderProdRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_order_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.order_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        orderInfo = CartConstants.getClickedOrderInfo();

        order_id = (TextView) findViewById(R.id.order_id);
        order_amount = (TextView) findViewById(R.id.order_amount);
        product_count = (TextView) findViewById(R.id.order_count);
        order_status = (TextView) findViewById(R.id.order_status);
        order_date = (TextView) findViewById(R.id.order_date);
        grandTotal = (TextView) findViewById(R.id.grand_total);
//        paymentMode = (TextView) findViewById(R.id.payment_Mode);
        nameView = (TextView) findViewById(R.id.name_view);
        addressView = (TextView) findViewById(R.id.address_view);
        mobileView = (TextView) findViewById(R.id.mobile_view);
        emailView = (TextView) findViewById(R.id.email_view);

//        estimatedDelivery = (TextView) findViewById(R.id.estimated_delivery);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        orderItemRecyclerView = (RecyclerView) findViewById(R.id.order_item_recycler_view);
        orderProdRecyclerAdapter = new OrderProdRecyclerAdapter(this, orderInfo.getOrdProdItems());
        orderItemRecyclerView.setAdapter(orderProdRecyclerAdapter);
        orderItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderItemRecyclerView.setNestedScrollingEnabled(false);
        nestedScrollView.setNestedScrollingEnabled(false);

        order_id.setText("Order No: " + orderInfo.getOrderId());
        product_count.setText("Total: " + orderInfo.getOrderItemsCount() + " Items");
        order_date.setText("" + orderInfo.getOrderDate());
        order_status.setText(orderInfo.getOrderStatus());
        order_amount.setText("Total: Rs " + orderInfo.getTotalPay());
        nameView.setText("" + orderInfo.getUserName());
        addressView.setText("" + orderInfo.getOrderAddress());
        mobileView.setText("" + orderInfo.getUserMobile());
        emailView.setText("" + orderInfo.getUserEmail());

        grandTotal.setText("Rs " + orderInfo.getTotalPay());
//        estimatedDelivery.setText("24 hrs");
//        paymentMode.setText("Cash");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
