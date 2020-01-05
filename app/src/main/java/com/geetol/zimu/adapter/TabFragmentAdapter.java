package com.geetol.zimu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class TabFragmentAdapter extends FragmentPagerAdapter {
    private FragmentActivity context;
    private List<Fragment> fragments;
    public TabFragmentAdapter(List<Fragment> fragments, FragmentManager fragmentManager, FragmentActivity context){
        super(fragmentManager);
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

}
