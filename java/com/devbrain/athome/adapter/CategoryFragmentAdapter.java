package com.devbrain.athome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mukesh Jha on 7/16/2016.
 */
public class CategoryFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();

    public CategoryFragmentAdapter(FragmentManager manager) {
        super(manager);
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void removeAllFragments(FragmentManager manager) {
        for (Fragment fragment : mFragmentList) {
            manager.beginTransaction().remove(fragment).commit();
        }
    }
}
