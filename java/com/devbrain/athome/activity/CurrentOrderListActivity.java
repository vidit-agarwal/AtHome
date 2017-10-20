package com.devbrain.athome.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.devbrain.athome.rest.NetworkTransaction;
import com.devbrain.athome.R;
import com.devbrain.athome.adapter.CurrentOrderRecyclerAdapter;
import com.devbrain.athome.data.OrderInfo;
import com.devbrain.athome.modal.Device;
import com.devbrain.athome.modal.Utility;
import com.devbrain.athome.parser.OrderJSONParser;
import com.devbrain.athome.rest.NetworkTransactionListener;
import com.devbrain.athome.rest.RestAPIURL;
import com.devbrain.athome.rest.TransactionType;
import com.devbrain.athome.views.CustomProgressDialog;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Honey Agarwal on 27-Apr-17.
 */

public class CurrentOrderListActivity extends AppCompatActivity {

    CurrentOrderRecyclerAdapter orderRecyclerAdapter;
    ArrayList<OrderInfo> orderInfoArrayList;
    RecyclerView recyclerView;
    TextView messageTextView;
    CustomProgressDialog customProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.review_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        orderInfoArrayList = new ArrayList<>();
        orderRecyclerAdapter = new CurrentOrderRecyclerAdapter(this, orderInfoArrayList);
        recyclerView = (RecyclerView) findViewById(R.id.order_listview);
        messageTextView = (TextView) findViewById(R.id.message_textView);
        recyclerView.setAdapter(orderRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getOrderList(this);
    }

    public void getOrderList(final Activity activity) {

        customProgressBar = new CustomProgressDialog(this);
        if (!customProgressBar.isShowing()) {
            customProgressBar.show();
        }

        JSONObject jObjDeviceInfo = Device.getInstance(activity).getDeviceIdentityJSON();

        NetworkTransaction.getInstance(activity).ProcessRequest(TransactionType.POST, RestAPIURL.CURRENT_ORDER_URI, jObjDeviceInfo, new NetworkTransactionListener<String>() {
            @Override
            public void onSuccess(String object, Object obj) {
                Utility.printLog("onSuccess: " + object);
                customProgressBar.dismiss();
                messageTextView.setVisibility(View.GONE);
//                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
                OrderJSONParser orderJSONParser = new OrderJSONParser(object);
                orderInfoArrayList.addAll(orderJSONParser.getOrderArrayList());
                orderRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String object, Object obj) {
                Utility.printLog("onFail: " + object);
                customProgressBar.dismiss();
                messageTextView.setVisibility(View.VISIBLE);
                messageTextView.setText(object);
//                Toast.makeText(activity, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onExist(String object, Object obj) {
                Utility.printLog("onExist: " + object);
                customProgressBar.dismiss();
                messageTextView.setVisibility(View.VISIBLE);
                messageTextView.setText(object);
//                Toast.makeText(activity, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkError(String object, Object obj) {
                Utility.printLog("onNetworkError: " + object);
                customProgressBar.dismiss();
                messageTextView.setVisibility(View.VISIBLE);
                messageTextView.setText(object);
//                Toast.makeText(activity, object, Toast.LENGTH_LONG).show();

            }
        });

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
