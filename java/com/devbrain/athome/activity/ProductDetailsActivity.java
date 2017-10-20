package com.devbrain.athome.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devbrain.athome.data.ProductInfo;
import com.devbrain.athome.modal.Utility;
import com.devbrain.athome.R;
import com.devbrain.athome.data.CartConstants;
//import com.nostra13.universalimageloader.core.ImageLoader;

public class ProductDetailsActivity extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match

    ProductInfo productInfo;
    TextView product_name;
    TextView product_min_order;
    TextView product_price;
    ImageView product_image;
    TextView discount_price;
    TextView product_count;
    TextView product_unit;
    TextView product_description;
    Button incrementButton;
    Button decrementButton;
    Button addToCartButton;
    LinearLayout countLayout;
    RelativeLayout mainLayout;
    private int THOUSAND = 1000;
//    ImageLoader imageLoader;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        imageLoader = MGrocery.getInstance().getImageLoader();

        // Inflate the layout for this fragment
        productInfo = CartConstants.getClickedProductInfo();
        product_name = (TextView) findViewById(R.id.product_name);
        product_min_order = (TextView) findViewById(R.id.product_min_order);
        product_price = (TextView) findViewById(R.id.product_price);
        product_image = (ImageView) findViewById(R.id.product_image);
        discount_price = (TextView) findViewById(R.id.discount_price);
        product_count = (TextView) findViewById(R.id.product_count);
        product_unit = (TextView) findViewById(R.id.product_unit);
        product_description = (TextView) findViewById(R.id.product_description);
        incrementButton = (Button) findViewById(R.id.inc_count_btn);
        decrementButton = (Button) findViewById(R.id.dec_count_btn);
        addToCartButton = (Button) findViewById(R.id.addToCartButton);
        countLayout = (LinearLayout) findViewById(R.id.count_layout);
        mainLayout = (RelativeLayout) findViewById(R.id.lytMain);

//        int width = getResources().getDisplayMetrics().widthPixels;
//        product_image.getLayoutParams().height = width*3/4;
//        product_image.requestLayout();
        showProductDetails();
    }


    private void showProductDetails() {

        final String prodUnit = productInfo.getProdUnit();
//        Utility.setImageFromUrl(productInfo.getProdImageUrl(), product_image);

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
        product_min_order.setText("min: " + prodMinOrder + " " + prodMinUnit);

//        imageLoader.displayImage(productInfo.getProdImageUrl(), product_image);
        Glide.with(this).load(productInfo.getProdImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.product_bg).into(product_image);
        product_name.setText(productInfo.getProdName());
        product_description.setText(productInfo.getProdDesc());
        if (productInfo.getProdDiscount() > 0) {
            discount_price.setText("Rs " + productInfo.getProdPrice() + " /" + prodUnit);
            discount_price.setPaintFlags(product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            product_price.setText("Rs " + productInfo.getDiscountedPrice() + "/" + prodUnit);
        } else {
            discount_price.setVisibility(View.GONE);
            product_price.setText("Rs " + productInfo.getProdPrice() + " /" + prodUnit);
        }

        product_count.setText(String.valueOf(orderCount));
        product_unit.setText(orderUnit);

        if (orderCount == 0) {
            addToCartButton.setVisibility(View.VISIBLE);
            countLayout.setVisibility(View.GONE);
        } else {
            addToCartButton.setVisibility(View.GONE);
            countLayout.setVisibility(View.VISIBLE);
        }

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = productInfo.getProdMinOrder();
                String unit = productInfo.getProdMinUnit();
                if (count >= THOUSAND && !unit.equals(prodUnit)) {
                    count = (int) Utility.lowToHigh(count);
                    unit = prodUnit;
                }
                CartConstants.addItemToCart(productInfo, count, unit);
                addToCartButton.setVisibility(View.GONE);
                countLayout.setVisibility(View.VISIBLE);
                product_count.setText(String.valueOf(count));
                product_unit.setText(unit);

            }
        });
        incrementButton.setOnClickListener(new View.OnClickListener() {
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
                product_count.setText(String.valueOf(count));
                product_unit.setText(unit);
            }
        });
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = CartConstants.getProductCount(productInfo.getProdId());
                String unit = CartConstants.getProductUnit(productInfo.getProdId());
                if (unit.equals(prodMinUnit)) {
                    count = count - prodMinOrder;
                    if (count < prodMinOrder) {
                        CartConstants.removeItemFromCart(productInfo);
                        addToCartButton.setVisibility(View.VISIBLE);
                        countLayout.setVisibility(View.GONE);
                    } else {
                        CartConstants.updateItem(productInfo, count, unit);
                        product_count.setText(String.valueOf(count));
                        product_unit.setText(prodMinUnit);
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
                    product_count.setText(String.valueOf(count));
                    product_unit.setText(unit);
                }

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
