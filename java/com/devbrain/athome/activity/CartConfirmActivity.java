package com.devbrain.athome.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrain.athome.data.ProductInfo;
import com.devbrain.athome.interfaces.TotalAmountUpdateListener;
import com.devbrain.athome.modal.Device;
import com.devbrain.athome.R;
import com.devbrain.athome.adapter.CartProductAdapter;
import com.devbrain.athome.data.CartConstants;
import com.devbrain.athome.data.OrdProdItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.devbrain.athome.data.CartConstants.getCartItemsArrayList;


/**
 * Created by Honey on 7/13/2016.
 */
public class CartConfirmActivity extends AppCompatActivity {

    RecyclerView productListView;
    Button btn_yes, btn_no;
    double totalAmount;
    TextView totalAmountTV;
    TextView messageTextView;
    String address;
    RelativeLayout mainLayout;
    CartProductAdapter productRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.review_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        productListView = (RecyclerView) findViewById(R.id.lv_confirm_container);
        btn_no = (Button) findViewById(R.id.btn_no_confirm);
        btn_yes = (Button) findViewById(R.id.btn_yes_confirm);
        totalAmountTV = (TextView) findViewById(R.id.total_amount_tv);
        messageTextView = (TextView) findViewById(R.id.message_textView);
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);

        if (getCartItemsArrayList().size() == 0) {
            messageTextView.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.INVISIBLE);
            messageTextView.setText("No item is added to Cart");
        } else {
            if (productRecyclerAdapter == null) {
                productRecyclerAdapter = new CartProductAdapter(this, getCartItemsArrayList());
                productRecyclerAdapter.setTotalAmountUpdateListener(totalAmountUpdateListener);
            }

            productListView.setAdapter(productRecyclerAdapter);
            productListView.setLayoutManager(new LinearLayoutManager(productListView.getContext()));
            productListView.setAdapter(productRecyclerAdapter);
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
                    if (totalAmount >= 250) {
                        Intent intent = new Intent(CartConfirmActivity.this, RegistrationActivity.class);
                        startActivityForResult(intent, CartConstants.REGISTER_USER_REQUEST_CODE);
                    } else {
                        Toast.makeText(CartConfirmActivity.this, "Min Order Amount is 250", Toast.LENGTH_LONG).show();
                    }
                }
            });

            for (ProductInfo productInfo : getCartItemsArrayList()) {
//            double count = CartConstants.getProductCount(productInfo.getProdId());
//            totalAmount = totalAmount + getTotalPrice(productInfo.getProdPrice(), count);
                totalAmount = totalAmount + CartConstants.getProductPrice(productInfo.getProdId());
            }
            totalAmountTV.setText("Rs " + totalAmount);
        }
    }

//    public double getTotalPrice(int price, double totalCount) {
//        double totalPrice = price * totalCount;
//        return totalPrice;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        */

        return super.onCreateOptionsMenu(menu);
    }


    TotalAmountUpdateListener totalAmountUpdateListener = new TotalAmountUpdateListener() {

        @Override
        public void updateTotalAmount() {
            totalAmount = 0;
            for (ProductInfo productInfo : getCartItemsArrayList()) {
//                double count = CartConstants.getProductCount(productInfo.getProdId());
//                totalAmount = totalAmount + getTotalPrice(productInfo.getProdPrice(), count);
                totalAmount = totalAmount + CartConstants.getProductPrice(productInfo.getProdId());
            }
            totalAmountTV.setText("Rs " + totalAmount);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CartConstants.REGISTER_USER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                address = data.getStringExtra("address");
                Intent intent = new Intent(CartConfirmActivity.this, PaymentActivity.class);
                intent.putExtra("order_json", createJsonObject(CartConfirmActivity.this));
                startActivityForResult(intent, CartConstants.PAYMENT_REQUEST_CODE);
            }
        } else if (requestCode == CartConstants.PAYMENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(CartConfirmActivity.this, OrderConfirmedActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private String createJsonObject(final Context context) {

        JSONObject jObjDeviceInfo = Device.getInstance(context).getDeviceIdentityJSON();

        try {
            jObjDeviceInfo.put("address", address);
            jObjDeviceInfo.put("totpay", totalAmount);

            JSONArray orderArray = new JSONArray();
            for (ProductInfo productInfo : CartConstants.getCartItemsArrayList()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(OrdProdItem.PROD_ID, productInfo.getProdId());
                jsonObject.put(OrdProdItem.PROD_NAME, productInfo.getProdName());
                jsonObject.put(OrdProdItem.PROD_DISCOUNT, productInfo.getProdDiscount());
                jsonObject.put(OrdProdItem.PROD_PRICE, CartConstants.getProductPrice(productInfo.getProdId()));
                jsonObject.put(OrdProdItem.PROD_QUANTITY, CartConstants.getProductCount(productInfo.getProdId()));
                jsonObject.put(OrdProdItem.PROD_ORDER_UNIT, CartConstants.getProductUnit(productInfo.getProdId()));
                orderArray.put(jsonObject);
            }
            jObjDeviceInfo.put("orders", orderArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObjDeviceInfo.toString();
    }
}
