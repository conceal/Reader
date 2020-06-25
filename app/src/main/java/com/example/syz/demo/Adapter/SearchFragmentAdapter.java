package com.example.syz.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class SearchFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public SearchFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    public Fragment getItem(int index) {
        return mFragmentList.get(index);
    }

    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void setmFragmentList(List<Fragment> mFragmentList) {
        this.mFragmentList = mFragmentList;
    }
}
