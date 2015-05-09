package com.yjx.cnblog.activity;/**
 * Created by yjx on 15/5/3.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.yjx.cnblog.Constant;
import com.yjx.cnblog.R;
import com.yjx.cnblog.adapter.AuthorBlogAdapter;
import com.yjx.cnblog.base.BaseActivity;
import com.yjx.cnblog.bean.AuthorBean;
import com.yjx.cnblog.bean.InfoBean;
import com.yjx.cnblog.net.XMLRequest;
import com.yjx.cnblog.utils.XMLUtils;
import com.yjx.cnblog.view.CustomListView;
import com.yjx.cnblog.view.SwipeLoadRefreshLayout;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import dmax.dialog.SpotsDialog;

/**
 * User: YJX
 * Date: 2015-05-03
 * Time: 00:31
 * 作者信息页面
 */
public class AuthorActivity extends BaseActivity {
    @InjectView(value = R.id.iv_header)
    ImageView iv_header;
    @InjectView(value = R.id.tv_name)
    TextView tv_name;
    @InjectView(value = R.id.tv_suibi)
    TextView tv_suibi;
    @InjectView(value = R.id.tv_address)
    TextView tv_address;
    @InjectView(value = R.id.lv_blogs)
    CustomListView lv_blogs;
    @InjectView(value = R.id.toolbar)
    Toolbar toolbar;
    AuthorBean info;
    AlertDialog dialog;
    int page = 1;

    List<InfoBean> infos;

    AuthorBlogAdapter adapter;
    @InjectView(value = R.id.sl)
    SwipeLoadRefreshLayout sl;
    int screenHeight;

    @Override
    protected void initData() {
        super.initData();
        info = (AuthorBean) getIntent().getSerializableExtra("INFO");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(info.getName());
        tv_address.setText("http://www.cnblogs.com/" + info.getBlogapp());
        tv_address.setAutoLinkMask(Linkify.ALL);
        tv_name.setText(info.getName());
        if (!TextUtils.isEmpty(info.getAvatar())) {
            Picasso.with(ctx).load(info.getAvatar()).error(R.drawable.ic_launcher).into(iv_header);
        } else {
            iv_header.setImageResource(R.drawable.ic_launcher);
        }
        tv_suibi.setText("随笔: " + info.getPostcount());
        dialog = new SpotsDialog(ctx, R.style.Custom);
        infos = new ArrayList<InfoBean>();
        adapter = new AuthorBlogAdapter(infos, ctx);
        lv_blogs.setAdapter(adapter);

        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;

        dialog.show();
        getBlogs();

    }

    @Override
    protected void setListener() {
        super.setListener();
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getBlogs();
            }
        });
        sl.setOnLoadListener(new SwipeLoadRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                page++;
                getBlogs();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == android.R.id.home) {
                    sl.animate().translationY(screenHeight).setDuration(500).setListener(new AnimatorListenerAdapter() {
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
        lv_blogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, location[0], location[1], 0, 0);
                Intent intent = null;
                intent = new Intent(ctx, BlogDetailActivity.class);
                intent.putExtra("INFO", infos.get(position));
                ActivityCompat.startActivity(AuthorActivity.this, intent, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public void onBackPressed() {
        sl.animate().translationY(screenHeight).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
                overridePendingTransition(0, 0);
            }
        }).start();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_author;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.getRequestQueue().cancelAll("getblogs");
        dialog.cancel();
    }

    /**
     * 得到指定用户的博客
     */
    private void getBlogs() {
        //u/{BLOGAPP}/posts/{PAGEINDEX}/{PAGESIZE}
        XMLRequest request = new XMLRequest(Request.Method.GET, Constant.URL + "u/" + info.getBlogapp() + "/posts/" + page + "/" + Constant.PAFESIZE, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Toast.makeText(ctx, "数据获取失败", Toast.LENGTH_SHORT).show();
                page--;
                if (page < 1) {
                    page = 1;
                }
            }
        }, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                dialog.cancel();
                if (page == 1) {
                    infos.clear();
                    infos.addAll(XMLUtils.getInfos(response));
                    adapter.notifyDataSetChanged();
                    sl.setRefreshing(false);
                } else {
                    infos.addAll(XMLUtils.getInfos(response));
                    adapter.notifyDataSetChanged();
                    sl.setLoading(false);
                }


            }
        });
        request.setTag("getblogs");
        client.addTask(request);
    }


}
