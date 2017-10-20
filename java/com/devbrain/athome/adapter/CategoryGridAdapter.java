package com.devbrain.athome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devbrain.athome.data.CategoryItem;
import com.devbrain.athome.R;

import java.util.ArrayList;

//import com.nostra13.universalimageloader.core.ImageLoader;

public class CategoryGridAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<CategoryItem> categoryItemArrayList;
    LayoutInflater inflater;
//    ImageLoader imageLoader;

    public CategoryGridAdapter(Context context, ArrayList<CategoryItem> categoryItemArrayList) {
        this.mContext = context;
        this.categoryItemArrayList = categoryItemArrayList;
        this.inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        imageLoader = MGrocery.getInstance().getImageLoader();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return categoryItemArrayList.size();
    }

    @Override
    public CategoryItem getItem(int position) {
        // TODO Auto-generated method stub
        return categoryItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View itemView;
        ViewHolder viewHolder;
        final CategoryItem categoryItem = categoryItemArrayList.get(position);

        if (convertView == null) {

            itemView = inflater.inflate(R.layout.category_grid_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) itemView.findViewById(R.id.category_image);
            viewHolder.nameView = (TextView) itemView.findViewById(R.id.category_name);

        } else {
            itemView = (View) convertView;
            viewHolder = (ViewHolder) itemView.getTag();
        }

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CategoryFragment subCategoryFragment = new CategoryFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt(CategoryItem.CAT_ID, categoryItem.getCategoryId());
//                subCategoryFragment.setArguments(bundle);
//                String title = mContext.getResources().getString(R.string.app_name);
//                HomeMainActivity.addFragmentToBackStack(subCategoryFragment, title);
//            }
//        });
        viewHolder.nameView.setText(categoryItem.getCategoryName());
//        Utility.setImageFromUrl(categoryItem.getCategoryImagePath(), viewHolder.imageView);
//        imageLoader.displayImage(categoryItem.getCategoryImagePath(), viewHolder.imageView);
        Glide.with(mContext).load(categoryItem.getCategoryImagePath()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.product_bg).into(viewHolder.imageView);
        itemView.setTag(viewHolder);
        return itemView;
    }

    class ViewHolder {
        TextView nameView;
        ImageView imageView;
    }
}