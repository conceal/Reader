package com.example.syz.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * communityActivity详情页面的碎片的Adapter
 */
public class CFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public CFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    public Fragment getItem(int index) {
        return mFragmentList.get(index);
    }

    public int getCount() {
        return mFragmentList.size();
    }
}
