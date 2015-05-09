package com.yjx.cnblog.activity;/**
 * Created by yjx on 15/5/9.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yjx.cnblog.Constant;
import com.yjx.cnblog.R;
import com.yjx.cnblog.adapter.FeedAdapter;
import com.yjx.cnblog.base.BaseActivity;
import com.yjx.cnblog.bean.FeedBean;
import com.yjx.cnblog.net.XMLRequest;
import com.yjx.cnblog.utils.XMLUtils;
import com.yjx.cnblog.view.SwipeLoadRefreshLayout;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import dmax.dialog.SpotsDialog;

/**
 * User: YJX
 * Date: 2015-05-09
 * Time: 22:19
 * 查看评论
 */
public class CommentsActivity extends BaseActivity {
    @InjectView(value = R.id.sl)
    SwipeLoadRefreshLayout sl;
    @InjectView(value = R.id.rv_feeds)
    RecyclerView rv_feeds;
    @InjectView(value = R.id.toolbar)
    Toolbar toolbar;
    List<FeedBean> feeds;
    FeedAdapter adapter;
    int page = 1;
    String blogid;
    int type;//1表示博客评论 2表示新闻评论

    android.app.AlertDialog dialog;
    int screenWidht, screenHeight;


    @Override
    public int getLayoutId() {
        return R.layout.activity_comments;
    }

    @Override
    protected void initData() {
        super.initData();
        blogid=getIntent().getStringExtra("BLOGID");
        type=getIntent().getIntExtra("TYPE",1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("评论");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView.LayoutManager manager = new
                LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        rv_feeds.setLayoutManager(manager);
        rv_feeds.setHasFixedSize(true);
        rv_feeds.setItemAnimator(new DefaultItemAnimator());
        feeds = new ArrayList<FeedBean>();
        adapter = new FeedAdapter(feeds, ctx);
        rv_feeds.setAdapter(adapter);
        WindowManager windowManager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        screenWidht = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        dialog=new SpotsDialog(ctx,R.style.Custom);
        dialog.show();
        getData();

    }

    @Override
    protected void setListener() {
        super.setListener();
        sl.setOnLoadListener(new SwipeLoadRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                page++;
                getData();
            }
        });

        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.getRequestQueue().cancelAll("getfeeds");
        dialog.cancel();
    }

    private void getData() {
        String url= type==1?Constant.URL + "post/" + blogid + "/comments/" + page + "/" + Constant.PAFESIZE:Constant.NEWSURL + "item/" + blogid + "/comments/" + page + "/" + Constant.PAFESIZE;
        XMLRequest request = new XMLRequest(Request.Method.GET, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                sl.setRefreshing(false);
                Toast.makeText(ctx, "获取数据失败", Toast.LENGTH_SHORT).show();
                page--;
                if (page <= 1) {
                    page = 1;
                }
            }
        }, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                dialog.cancel();

                if (page == 1) {
                    feeds.clear();
                    feeds.addAll(XMLUtils.getFeeds(response));
                    adapter.notifyDataSetChanged();
                    sl.setRefreshing(false);
                } else {
                    feeds.addAll(XMLUtils.getFeeds(response));
                    adapter.notifyDataSetChanged();
                    sl.setLoading(false);
                }

            }
        });
        request.setTag("getfeeds");
        client.addTask(request);
    }
}
