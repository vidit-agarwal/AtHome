package com.devbrain.athome.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrain.athome.rest.NetworkTransaction;
import com.devbrain.athome.R;
import com.devbrain.athome.adapter.OrderProdRecyclerAdapter;
import com.devbrain.athome.data.OrdProdItem;
import com.devbrain.athome.modal.Utility;
import com.devbrain.athome.parser.OrdProdJSONParser;
import com.devbrain.athome.rest.NetworkTransactionListener;
import com.devbrain.athome.rest.RestAPIURL;
import com.devbrain.athome.rest.TransactionType;
import com.devbrain.athome.views.CustomProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Honey Agarwal on 26-Apr-17.
 */

public class PaymentActivity extends AppCompatActivity {

    Button btn_yes, btn_no;
    JSONObject jsonObjOrderInfo;
    TextView totalAmountView;
    TextView deliverAddressView;
    CustomProgressDialog customProgressBar;
    NestedScrollView nestedScrollView;
    RecyclerView orderItemRecyclerView;
    ArrayList<OrdProdItem> ordProdItems = new ArrayList<>();
    OrderProdRecyclerAdapter orderProdRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn_no = (Button) findViewById(R.id.btn_no_confirm);
        btn_yes = (Button) findViewById(R.id.btn_yes_confirm);
        totalAmountView = (TextView) findViewById(R.id.total_amount);
        deliverAddressView = (TextView) findViewById(R.id.delivery_address);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        orderItemRecyclerView = (RecyclerView) findViewById(R.id.order_item_recycler_view);


        String jsonString = getIntent().getStringExtra("order_json");
        Utility.printLog("jsonString : " + jsonString);
        try {
            jsonObjOrderInfo = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObjOrderInfo.getJSONArray("orders");
            OrdProdJSONParser ordProdJSONParser = new OrdProdJSONParser(jsonArray);
            ordProdItems = ordProdJSONParser.getProductArrayList();

            totalAmountView.setText("Rs " + jsonObjOrderInfo.getString("totpay"));
            deliverAddressView.setText(jsonObjOrderInfo.getString("address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        orderProdRecyclerAdapter = new OrderProdRecyclerAdapter(this, ordProdItems);
        orderItemRecyclerView.setAdapter(orderProdRecyclerAdapter);
        orderItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderItemRecyclerView.setNestedScrollingEnabled(false);
        nestedScrollView.setNestedScrollingEnabled(false);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrderDetails(PaymentActivity.this, jsonObjOrderInfo);
                setResult(RESULT_OK);
                finish();
//                finish();
            }
        });

    }

    private void sendOrderDetails(final Context context, JSONObject jObjDeviceInfo) {
        customProgressBar = new CustomProgressDialog(this);
        if (!customProgressBar.isShowing()) {
            customProgressBar.show();
        }

        NetworkTransaction.getInstance(context).ProcessRequest(TransactionType.POST, RestAPIURL.CREATE_ORDER_URL, jObjDeviceInfo, new NetworkTransactionListener<String>() {
            @Override
            public void onSuccess(String object, Object obj) {
                Utility.printLog("onSuccess: " + object);
//                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
                finish();

//                sharedPrefEditor.putString(CategoryJSONParser.JSON_CATEGORIES, object);
//                sharedPrefEditor.commit();
            }

            @Override
            public void onFail(String object, Object obj) {
                Utility.printLog("onFail: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
                setResult(RESULT_CANCELED);
            }

            @Override
            public void onExist(String object, Object obj) {
                Utility.printLog("onExist: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkError(String object, Object obj) {
                Utility.printLog("onNetworkError: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

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
