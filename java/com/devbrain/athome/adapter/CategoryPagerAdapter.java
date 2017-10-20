package com.devbrain.athome.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devbrain.athome.R;
import com.devbrain.athome.activity.HomeMainActivity;
import com.devbrain.athome.data.CategoryItem;
import com.devbrain.athome.fragment.CategoryFragment;

import java.util.ArrayList;

//import com.nostra13.universalimageloader.core.ImageLoader;

public class CategoryPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<CategoryItem> categoryItems;
    CategoryFragment categoryFragment;
//    ImageLoader imageLoader;

    public CategoryPagerAdapter(Context context, ArrayList<CategoryItem> categoryItems) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categoryItems = categoryItems;
//        imageLoader = MGrocery.getInstance().getImageLoader();
    }

    @Override
    public int getCount() {
        return categoryItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.category_slider_item_layout, container, false);
        final CategoryItem categoryItem = categoryItems.get(position);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.category_image);
//        Utility.setImageFromUrl(categoryItem.getCategoryImagePath(), imageView);
//        imageLoader.displayImage(categoryItem.getCategoryImagePath(), imageView);
//        TextView textView=(TextView)itemView.findViewById(R.id.category_name);
//        textView.setText(categoryItem.getCategoryName());
        Glide.with(mContext).load(categoryItem.getCategoryImagePath()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.banner_bg).into(imageView);
        container.addView(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CategoryFragment categoryFragment = new CategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(CategoryItem.CAT_ID, categoryItem.getCategoryId());
                categoryFragment.setArguments(bundle);
                String title = mContext.getResources().getString(R.string.app_name);
                HomeMainActivity.addFragmentToBackStack(categoryFragment, title);
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    public void setCategoryFragment(CategoryFragment categoryFragment) {
        this.categoryFragment = categoryFragment;
    }
}