package com.yjx.cnblog.activity;/**
 * Created by yjx on 15/5/1.
 */

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.orhanobut.logger.Logger;
import com.yjx.cnblog.Constant;
import com.yjx.cnblog.R;
import com.yjx.cnblog.base.BaseActivity;
import com.yjx.cnblog.bean.InfoBean;
import com.yjx.cnblog.net.XMLRequest;
import com.yjx.cnblog.utils.XMLUtils;

import org.xmlpull.v1.XmlPullParser;

import java.io.Serializable;
import java.util.List;

/**
 * User: YJX
 * Date: 2015-05-01
 * Time: 13:44
 * 欢迎页
 */
public class SplashActivity extends BaseActivity {
    private Handler handler;
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        super.initData();
        handler=new Handler();
        XMLRequest request = new XMLRequest(Request.Method.GET, Constant.URL + "sitehome/paged/1/" + Constant.PAFESIZE, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.e("请求失败");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        jumpAct(MainActivity.class, true);
                    }
                },1500);

            }
        }, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                final List<InfoBean> infos = XMLUtils.getInfos(response);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ctx, MainActivity.class);
                        intent.putExtra("INFO",(Serializable)infos);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                    }
                },1500);



            }
        });
        request.setTag("splash");
        client.addTask(request);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.getRequestQueue().cancelAll("splash");
    }
}
