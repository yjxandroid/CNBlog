package com.yjx.cnblog.adapter;/**
 * Created by yjx on 15/5/2.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * User: YJX
 * Date: 2015-05-02
 * Time: 00:19
 */
public class VPAdapter extends FragmentPagerAdapter {
    private String [] items;
    private List<Fragment> fragments;

    public VPAdapter(FragmentManager fm, String[] items, List<Fragment> fragments) {
        super(fm);
        this.items = items;
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
        return items[position];
    }
}
