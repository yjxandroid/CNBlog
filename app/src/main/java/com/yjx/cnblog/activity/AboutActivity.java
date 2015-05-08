package com.yjx.cnblog.activity;/**
 * Created by yjx on 15/5/4.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.yjx.cnblog.R;
import com.yjx.cnblog.base.BaseActivity;

import butterknife.InjectView;

/**
 * User: YJX
 * Date: 2015-05-04
 * Time: 20:24
 * 关于页面
 */
public class AboutActivity extends BaseActivity {
    @InjectView(value = R.id.toolbar)
    Toolbar toolbar;
    @InjectView(value = R.id.ll)
    LinearLayout ll;
    int screenWidht, screenHeight;

    @Override
    protected void initData() {
        super.initData();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("关于");
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        screenWidht = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ll.animate().translationY(screenHeight).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }).start();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        ll.animate().translationY(screenHeight).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
                overridePendingTransition(0, 0);
            }
        }).start();
    }
}
