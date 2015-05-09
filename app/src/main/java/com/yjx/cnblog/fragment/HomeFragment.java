package com.yjx.cnblog.fragment;/**
 * Created by yjx on 15/5/1.
 */

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yjx.cnblog.Constant;
import com.yjx.cnblog.R;
import com.yjx.cnblog.activity.BlogDetailActivity;
import com.yjx.cnblog.activity.NewsDetailActivity;
import com.yjx.cnblog.adapter.BlogAdapter;
import com.yjx.cnblog.adapter.NewsAdapter;
import com.yjx.cnblog.base.BaseFragment;
import com.yjx.cnblog.bean.InfoBean;
import com.yjx.cnblog.listener.onAdapterTouch;
import com.yjx.cnblog.net.XMLRequest;
import com.yjx.cnblog.utils.XMLUtils;
import com.yjx.cnblog.view.SwipeLoadRefreshLayout;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * User: YJX
 * Date: 2015-05-01
 * Time: 13:29
 * 博客页面
 */
public class HomeFragment extends BaseFragment {
    private List<InfoBean> blogs;
    private List<InfoBean> newss;

    @InjectView(value = R.id.rv_blogs)
    RecyclerView rv_blogs;
    @InjectView(value = R.id.sl)
    SwipeLoadRefreshLayout sl;
    BlogAdapter adapter;
    NewsAdapter newsAdapter;
    int blogpage = 1;
    int newspage = 1;
    boolean isblog = true;
    HashMap<String, List> datas;//用来管理新闻和博客数据

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blog;
    }

    @Override
    protected void initData() {
        super.initData();
        datas = new HashMap<String, List>();
        sl.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);

        if (getArguments() != null && getArguments().containsKey("INFO")) {
            blogs = (ArrayList<InfoBean>) getArguments().getSerializable("INFO");
            if (blogs==null){
                blogs = new ArrayList<InfoBean>();
            }
        } else {
            blogs = new ArrayList<InfoBean>();
        }
        datas.put("blog", blogs);
        isblog = true;
        adapter = new BlogAdapter(frmctx, blogs);
        RecyclerView.LayoutManager manager = new
                LinearLayoutManager(frmctx, LinearLayoutManager.VERTICAL, false);
        rv_blogs.setLayoutManager(manager);
        rv_blogs.setHasFixedSize(true);
        rv_blogs.setItemAnimator(new DefaultItemAnimator());
        rv_blogs.setAdapter(adapter);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        newss = new ArrayList<InfoBean>();
        newsAdapter = new NewsAdapter(frmctx, newss);
        if (blogs.size()<=0){
            getBlogData();
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isblog) {
                    blogpage = 1;
                    getBlogData();
                } else {
                    newspage = 1;
                    getNewsData();
                }
            }
        });
        sl.setOnLoadListener(new SwipeLoadRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                if (isblog) {
                    blogpage++;
                    getBlogData();
                } else {
                    newspage++;
                    getNewsData();
                }
            }
        });
        adapter.setListen(new onAdapterTouch() {
            @Override
            public void onAdapterClick(View view, int pos) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, location[0], location[1], 0, 0);
                Intent intent = null;
                intent = new Intent(frmctx, BlogDetailActivity.class);
                intent.putExtra("INFO", blogs.get(pos));
                ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());

            }

            @Override
            public void onAdapterLongClick(View view, int pos) {

            }
        });
        newsAdapter.setListen(new onAdapterTouch() {
            @Override
            public void onAdapterClick(View view, int pos) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, location[0], location[1], 0, 0);
                Intent intent = null;
                intent = new Intent(frmctx, NewsDetailActivity.class);
                intent.putExtra("INFO", newss.get(pos));
                ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
            }

            @Override
            public void onAdapterLongClick(View view, int pos) {

            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        client.getRequestQueue().cancelAll("getblog");
        client.getRequestQueue().cancelAll("getnews");
        EventBus.getDefault().unregister(this);
    }

    /**
     * 从网络获取博客数据
     */
    private void getBlogData() {
        XMLRequest request = new XMLRequest(Request.Method.GET, Constant.URL + "sitehome/paged/" + blogpage + "/" + Constant.PAFESIZE, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sl.setRefreshing(false);
                Toast.makeText(frmctx, "获取数据失败", Toast.LENGTH_SHORT).show();
                blogpage--;
                if (blogpage <= 1) {
                    blogpage = 1;
                }
            }
        }, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                if (blogpage == 1) {
                    blogs.clear();
                    blogs.addAll(XMLUtils.getInfos(response));
                    adapter.notifyDataSetChanged();
                    sl.setRefreshing(false);
                } else {
                    blogs.addAll(XMLUtils.getInfos(response));
                    adapter.notifyDataSetChanged();
                    sl.setLoading(false);
                }
                datas.remove("blog");
                datas.put("blog", blogs);
            }
        });
        request.setTag("getblog");
        client.addTask(request);
    }

    /**
     * 从网络获取新闻数据
     */
    private void getNewsData() {
        XMLRequest request = new XMLRequest(Request.Method.GET, Constant.NEWSURL + "recent/paged/" + newspage + "/" + Constant.PAFESIZE, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sl.setRefreshing(false);
                Toast.makeText(frmctx, "获取数据失败", Toast.LENGTH_SHORT).show();
                newspage--;
                if (newspage <= 1) {
                    newspage = 1;
                }
            }
        }, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                if (newspage == 1) {
                    newss.clear();
                    newss.addAll(XMLUtils.getInfos(response));
                    newsAdapter.notifyDataSetChanged();
                    sl.setRefreshing(false);
                } else {
                    newss.addAll(XMLUtils.getInfos(response));
                    newsAdapter.notifyDataSetChanged();
                    sl.setLoading(false);
                }
                datas.remove("news");
                datas.put("news", newss);
            }
        });
        request.setTag("getnews");
        client.addTask(request);
    }


    /**
     * 用来切换RecyclerView中的数据
     *
     * @param is
     */
    public void onEventMainThread(String is) {
        if ("true".equals(is))
            this.isblog = true;
        else
            this.isblog = false;
        if (isblog) {
            if (datas.containsKey("blog")) {
                rv_blogs.setAdapter(adapter);
            } else {
                rv_blogs.setAdapter(adapter);
                blogpage = 1;
                getBlogData();
            }
        } else {
            if (datas.containsKey("news")) {
                rv_blogs.setAdapter(newsAdapter);
            } else {
                rv_blogs.setAdapter(newsAdapter);
                newspage = 1;
                getNewsData();
            }
        }
    }


}
