package com.yjx.cnblog.base;/**
 * Created by yjx on 15/5/1.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yjx.cnblog.net.AsynHttpClient;

import butterknife.ButterKnife;

/**
 * User: YJX
 * Date: 2015-05-01
 * Time: 13:31
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected View rootView;
    protected Context frmctx;
    protected AsynHttpClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.frmctx = getActivity();
        this.client=AsynHttpClient.getInstance(frmctx.getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.inject(this,rootView);
        initData();
        setListener();
        return rootView;
    }

    protected abstract int getLayoutId();

    /**
     * 设置监听器
     */
    protected void setListener(){

    }

    /**
      初始化数据
     */
    protected void initData(){

    }

}
