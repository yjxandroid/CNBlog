package com.yjx.cnblog.fragment;/**
 * Created by yjx on 15/5/1.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yjx.cnblog.R;
import com.yjx.cnblog.adapter.VPAdapter;
import com.yjx.cnblog.base.BaseFragment;
import com.yjx.cnblog.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * User: YJX
 * Date: 2015-05-01
 * Time: 13:30
 * 发现页面
 */

public class ExploreFragment extends BaseFragment {
    @InjectView(value = R.id.slid_top)
    SlidingTabLayout slid_top;
    @InjectView(value = R.id.vp_content)
    ViewPager vp_content;
    VPAdapter adapter;
    List<Fragment> fragments;
    private String[] items = new String[]{"48H阅读", "热门博客", "热门新闻"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    protected void initData() {
        super.initData();
        slid_top.setSelectedIndicatorColors(Color.WHITE);
        slid_top.setDividerColors(Color.TRANSPARENT);

        fragments = new ArrayList<Fragment>();
        for (int i = 0; i < items.length; i++) {
            Fragment fragment = new ExploreChildFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("STATE", i + 1);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        adapter = new VPAdapter(getChildFragmentManager(), items, fragments);
        vp_content.setAdapter(adapter);
        slid_top.setViewPager(vp_content);
    }

    @Override
    public void onClick(View v) {

    }
}
