package com.yjx.cnblog.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.yjx.cnblog.R;
import com.yjx.cnblog.base.BaseActivity;
import com.yjx.cnblog.db.SPHelper;

import butterknife.InjectView;

/**
 * User: YJX
 * Date: 2015-05-03
 * Time: 20:16
 * 设置页面
 */
public class SettingActivity extends BaseActivity {
    @InjectView(value = R.id.toolbar)
    Toolbar toolbar;
    @InjectView(value = R.id.fl)
    ScrollView fl;
    int screenWidht, screenHeight;
    @InjectView(value = R.id.ll_wifi)
    LinearLayout ll_wifi;

    @InjectView(value = R.id.ll_gg)
    LinearLayout ll_gg;
    @InjectView(value = R.id.ll_theme)
    LinearLayout ll_theme;

    @InjectView(value = R.id.ll_about)
    LinearLayout ll_about;

    @InjectView(value = R.id.ll_update)
    LinearLayout ll_update;

    @InjectView(value = R.id.ll_what)
    LinearLayout ll_what;

    @InjectView(value = R.id.ll_blog)
    LinearLayout ll_blog;
    @InjectView(value = R.id.cb_wifi)
    AppCompatCheckBox cb_wifi;
    @InjectView(value = R.id.cb_gg)
    AppCompatCheckBox cb_gg;

    @InjectView(value = R.id.cb_theme)
    AppCompatCheckBox cb_theme;


    boolean istheme = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        super.initData();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("设置");
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        screenWidht = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        cb_gg.setChecked(SPHelper.isShowImg(ctx));
        cb_wifi.setChecked(SPHelper.isWifiDown(ctx));
        cb_theme.setChecked(SPHelper.getTheme(ctx));
    }

    @Override
    protected void setListener() {
        super.setListener();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == android.R.id.home) {
                    jumpAct(MainActivity.class, true);
                    overridePendingTransition(0, 0);
                }
                return true;
            }
        });
        ll_about.setOnClickListener(this);
        ll_blog.setOnClickListener(this);
        ll_gg.setOnClickListener(this);
        ll_update.setOnClickListener(this);
        ll_what.setOnClickListener(this);
        ll_wifi.setOnClickListener(this);
        ll_theme.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        jumpAct(MainActivity.class, true);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_wifi:
                cb_wifi.setChecked(!cb_wifi.isChecked());
                SPHelper.setWifiDown(ctx, cb_wifi.isChecked());
                break;
            case R.id.ll_gg:
                cb_gg.setChecked(!cb_gg.isChecked());
                SPHelper.setShowImg(ctx, cb_gg.isChecked());
                break;
            case R.id.ll_about:
                jumpAct(AboutActivity.class, false);
                break;
            case R.id.ll_what:
                jumpAct(WhatActivity.class, false);
                break;
            case R.id.ll_theme:
                boolean cc = SPHelper.getTheme(ctx);
                cb_theme.setChecked(!cb_theme.isChecked());
                SPHelper.setTheme(ctx, !cc);
                finish();
                final Intent themeintent = getIntent();
                themeintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(themeintent);
                overridePendingTransition(0,0);
                break;
            case R.id.ll_blog:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.cnblogs.com/likeandroid/"));
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.ll_update:
                break;
        }
    }


}
