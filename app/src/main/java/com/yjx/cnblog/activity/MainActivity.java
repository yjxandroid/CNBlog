package com.yjx.cnblog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjx.cnblog.R;
import com.yjx.cnblog.base.BaseActivity;
import com.yjx.cnblog.fragment.ExploreFragment;
import com.yjx.cnblog.fragment.HomeFragment;
import com.yjx.cnblog.utils.APPUtils;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * User: YJX
 * Date: 2015-04-28
 * Time: 23:15
 * 主体页面
 */
public class MainActivity extends BaseActivity {
    @InjectView(value = R.id.toolbar)
    Toolbar toolbar;
    @InjectView(value = R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @InjectView(value = R.id.tv_version)
    TextView tv_version;
    @InjectView(value = R.id.rl_menu)
    RelativeLayout rl_menu;

    @InjectView(value = R.id.tv_home)
    TextView tv_home;
    @InjectView(value = R.id.tv_blog)
    TextView tv_blog;
    @InjectView(value = R.id.tv_news)
    TextView tv_news;
    @InjectView(value = R.id.tv_explore)
    TextView tv_explore;
    @InjectView(value = R.id.tv_collect)
    TextView tv_collect;

    @InjectView(value = R.id.tv_setting)
    TextView tv_setting;

    ActionBarDrawerToggle actionBarDrawerToggle;
    HomeFragment blogFragment;
    Fragment curfragment;
    ExploreFragment exploreFragment;
    int state = 1;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        initToolbar();
        tv_version.setText("版本 " + APPUtils.getVersionName(ctx, getPackageName()));
        blogFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("INFO", getIntent().getSerializableExtra("INFO"));
        blogFragment.setArguments(bundle);
        curfragment = blogFragment;
        jumpFrm(null, blogFragment, R.id.frm_content, "首页");
    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_blog.setOnClickListener(this);
        tv_collect.setOnClickListener(this);
        tv_explore.setOnClickListener(this);
        tv_home.setOnClickListener(this);
        tv_news.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ab_search) {
            jumpAct(SearchActivity.class, false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(rl_menu)) {
            drawerlayout.closeDrawer(rl_menu);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home:
                if (tv_news.getVisibility() == View.GONE) {
                    tv_news.setVisibility(View.VISIBLE);
                    tv_blog.setVisibility(View.VISIBLE);
                } else {
                    tv_news.setVisibility(View.GONE);
                    tv_blog.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_blog:
                //博客页面
                if (blogFragment == null) {
                    blogFragment = new HomeFragment();
                }
                if (curfragment instanceof HomeFragment) {

                } else {
                    jumpFrm(curfragment, blogFragment, R.id.frm_content, "首页");
                }
                curfragment = blogFragment;
                state = 1;
                drawerlayout.closeDrawer(rl_menu);
                EventBus.getDefault().post("true");
                getSupportActionBar().setTitle(R.string.home);
                break;
            case R.id.tv_news:
                //新闻页面
                if (blogFragment == null) {
                    blogFragment = new HomeFragment();
                }
                if (curfragment instanceof HomeFragment) {

                } else {
                    jumpFrm(curfragment, blogFragment, R.id.frm_content, "首页");
                }
                curfragment = blogFragment;
                state = 2;
                drawerlayout.closeDrawer(rl_menu);
                EventBus.getDefault().post("false");
                getSupportActionBar().setTitle(R.string.news);
                break;
            case R.id.tv_explore:
                //发现
                state = 3;
                drawerlayout.closeDrawer(rl_menu);
                if (exploreFragment == null) {
                    exploreFragment = new ExploreFragment();
                }
                if (curfragment instanceof ExploreFragment) {

                } else {
                    jumpFrm(curfragment, exploreFragment, R.id.frm_content, "发现");
                }
                curfragment = exploreFragment;
                getSupportActionBar().setTitle(R.string.explore);
                break;
            case R.id.tv_collect:
                drawerlayout.closeDrawer(rl_menu);
                //收藏页面
                break;
            case R.id.tv_setting:
                //设置页面
                drawerlayout.closeDrawer(rl_menu);
                Intent intent = new Intent(this, SettingActivity.class);
                startActivityForResult(intent, 200);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.app_name, R.string.home) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                switch (state) {
                    case 1:
                        getSupportActionBar().setTitle(R.string.home);
                        break;
                    case 2:
                        getSupportActionBar().setTitle(R.string.news);
                        break;
                    case 3:
                        getSupportActionBar().setTitle(R.string.explore);
                        break;
                }
                tv_blog.setVisibility(View.GONE);
                tv_news.setVisibility(View.GONE);
            }
        };
        actionBarDrawerToggle.syncState();
        drawerlayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setTitle(R.string.home);
    }


}
