package com.devbrain.athome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.devbrain.athome.data.SubCategoryItem;
import com.devbrain.athome.R;

import java.util.ArrayList;

/**
 * An array adapter that knows how to render views when given CustomData classes
 */
public class HorizontalListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    ArrayList<SubCategoryItem> subCategoryItems;
    Context mContext;

    public HorizontalListAdapter(Context context, ArrayList<SubCategoryItem> subCategoryItems) {

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.subCategoryItems = subCategoryItems;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return subCategoryItems.size();
    }

    @Override
    public Object getItem(int i) {
        return subCategoryItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return subCategoryItems.get(i).getSubCategoryId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        SubCategoryItem subCategoryItem= (SubCategoryItem) getItem(position);
        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.custom_data_view, parent, false);

            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons
            holder = new Holder();
            holder.button = (Button) convertView.findViewById(R.id.subCategory_view);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text
        holder.button.setText(subCategoryItem.getSubCategoryName());

        // Set the color

        return convertView;
    }

    /**
     * View holder for the views we need access to
     */
    private static class Holder {
        public Button button;
    }
}
