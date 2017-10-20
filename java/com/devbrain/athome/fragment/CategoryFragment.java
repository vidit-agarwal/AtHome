package com.devbrain.athome.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devbrain.athome.adapter.CategoryFragmentAdapter;
import com.devbrain.athome.data.CategoryItem;
import com.devbrain.athome.R;
import com.devbrain.athome.data.CartConstants;

import java.util.ArrayList;

/**
 * Created by Mukesh Jha on 3/6/2017.
 */
public class CategoryFragment extends Fragment {
    View rootView = null;
    TabLayout tabLayout = null;
    ViewPager viewPager = null;
    int categoryID = 1;
    int selectedTab = 0;
    CategoryFragmentAdapter categoryFragmentAdapter;
    String TAG="CategoryFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category_content, container, false);
        Log.d(TAG,"onCreateView");
//        Show banner ads
        /*AdView mAdView = (AdView)rootView.findViewById(R.id.adView);
        AdMobFactory.getAdMobFactory(getActivity()).showBannerAds(mAdView);*/
        Bundle bundle = getArguments();
        categoryID = bundle.getInt(CategoryItem.CAT_ID);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        setupViewPager(CartConstants.getCategoryItemArrayList());

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(onTabSelectedListener);


        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onDetach() {
//        viewPager.removeAllViews();
//        tabLayout.removeAllViews();
        super.onDetach();
        Log.d(TAG,"onDetach");
    }


    TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
//            MainActivity.setTitle(tab.getText().toString());
            viewPager.setCurrentItem(tab.getPosition());
        }


        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void setupViewPager(ArrayList<CategoryItem> categoryItemList) {
        Log.d(TAG,"setupViewPager");
        categoryFragmentAdapter = new CategoryFragmentAdapter(getChildFragmentManager());
        int i = 0;
        for (CategoryItem item : categoryItemList) {
            Bundle bundle = new Bundle();
            bundle.putInt(CategoryItem.CAT_ID, item.getCategoryId());
            ProductListFragment fragment = new ProductListFragment();
            fragment.setArguments(bundle);
            categoryFragmentAdapter.addFrag(fragment, item.getCategoryName());
            if (item.getCategoryId() == categoryID) {
                selectedTab = i;
            }
            i++;
        }
        viewPager.setAdapter(categoryFragmentAdapter);
        viewPager.setCurrentItem(selectedTab);
        viewPager.setOffscreenPageLimit(i);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }


}
