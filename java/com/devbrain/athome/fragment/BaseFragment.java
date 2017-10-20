package com.devbrain.athome.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment implements IFragment{
    protected Context context;
    protected Object fragmentData;

    public BaseFragment() {

    }

    @Override
    public void initFragment() {

    }

    @Override
    public void destroyFragment()
    {

    }

    public Object getFragmentData() {
        return fragmentData;
    }

    public void setFragmentData(Object fragmentData) {
        this.fragmentData = fragmentData;
    }



}
