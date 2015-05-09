package com.yjx.cnblog.base;/**
 * Created by yjx on 15/4/28.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.yjx.cnblog.R;
import com.yjx.cnblog.db.SPHelper;
import com.yjx.cnblog.net.AsynHttpClient;

import butterknife.ButterKnife;

/**
 * User: YJX
 * Date: 2015-04-28
 * Time: 23:01
 * 基础activity
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Context ctx;
    protected AsynHttpClient client;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!SPHelper.getTheme(this)){
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.AppThemeNight);
        }
        setContentView(getLayoutId());
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            if (!SPHelper.getTheme(this)){
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }else{
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkNight));
            }
        }
        ctx = this;
        client=AsynHttpClient.getInstance(getApplicationContext());
        ButterKnife.inject(this);
        initData();
        setListener();

    }

    /**
     * 得到布局文件
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 设置监听器
     */
    protected void setListener() {

    }

    /**
     * activity之间的跳转
     * @param clazz 目标activity
     * @param isfinish 是否关闭
     */
    protected void jumpAct(Class clazz, boolean isfinish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (isfinish) {
            this.finish();
        } else {

        }
    }

    /**
     * Fragment之间的切换
     * @param from 当前
     * @param to 目标
     * @param id
     * @param tag
     */
    protected void jumpFrm(Fragment from,Fragment to,int id,String tag){
        if (to==null){
            return;
        }
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if (from==null){
            transaction.add(id,to,tag);
        }else{
            transaction.hide(from);
            if (to.isAdded()){
                 transaction.show(to);
            }else{
                transaction.add(id,to,tag);
            }
        }
        transaction.commitAllowingStateLoss();
    }



}
