package com.yjx.cnblog.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.yjx.cnblog.R;
import com.yjx.cnblog.base.BaseActivity;
import com.yjx.cnblog.fragment.SetttingFragment;

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
    FrameLayout fl;
    int screenWidht, screenHeight;

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
        getFragmentManager().beginTransaction().replace(R.id.fl,new SetttingFragment(),"设置").commit();
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        screenWidht = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    @Override
    protected void setListener() {
        super.setListener();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==android.R.id.home){
                    fl.animate().translationY(screenHeight).setDuration(500).setListener(new AnimatorListenerAdapter() {
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
        });
    }

    @Override
    public void onBackPressed() {
        fl.animate().translationY(screenHeight).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
                overridePendingTransition(0, 0);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

    }
}
