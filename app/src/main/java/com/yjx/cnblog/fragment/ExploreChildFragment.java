package com.yjx.cnblog.fragment;/**
 * Created by yjx on 15/5/3.
 */

import android.content.Intent;
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
import java.util.List;

import butterknife.InjectView;

/**
 * User: YJX
 * Date: 2015-05-03
 * Time: 12:13
 */
public class ExploreChildFragment extends BaseFragment {
    @InjectView(value = R.id.rv_blogs)
    RecyclerView rv_blogs;
    @InjectView(value = R.id.sl)
    SwipeLoadRefreshLayout sl;
    int state;//
    public static final int READ48 = 1;//48小时阅读
    public static final int HOTBLOG = 2;//热门博客
    public static final int HOTNEWS = 3;//热门新闻
    String read48 = "48HoursTopViewPosts/";//48小时阅读请求地址
    String hotblog = "TenDaysTopDiggPosts/";//推荐博客
    String hotnews = "hot/";//热门新闻地址
    int hotblogpage = 1;
    int hotnewspage = 1;
    BlogAdapter adapter48;
    BlogAdapter hotadapter;
    NewsAdapter newsadapter;

    List<InfoBean> info48s, hotbloginfos, hotnewsinfos;


    @Override
    protected void initData() {
        super.initData();
        RecyclerView.LayoutManager manager = new
                LinearLayoutManager(frmctx, LinearLayoutManager.VERTICAL, false);
        rv_blogs.setLayoutManager(manager);
        rv_blogs.setHasFixedSize(true);
        rv_blogs.setItemAnimator(new DefaultItemAnimator());

        if (getArguments() != null) {
            state = getArguments().getInt("STATE");
        } else {
            state = READ48;
        }
        switch (state) {
            case READ48:
                info48s = new ArrayList<InfoBean>();
                adapter48 = new BlogAdapter(frmctx, info48s);
                rv_blogs.setAdapter(adapter48);
                get48();
                break;
            case HOTBLOG:
                hotbloginfos = new ArrayList<InfoBean>();
                hotadapter = new BlogAdapter(frmctx, hotbloginfos);
                rv_blogs.setAdapter(hotadapter);
                getHotBlog();
                break;
            case HOTNEWS:
                hotnewsinfos = new ArrayList<InfoBean>();
                newsadapter = new NewsAdapter(frmctx, hotnewsinfos);
                rv_blogs.setAdapter(newsadapter);
                getHotNews();
                break;
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        sl.setOnLoadListener(new SwipeLoadRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                switch (state) {
                    case READ48:
                        break;
                    case HOTBLOG:
                        break;
                    case HOTNEWS:
                        break;
                }
            }
        });
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (state) {
                    case READ48:
                        get48();
                        break;
                    case HOTBLOG:
                        hotblogpage = 1;
                        getHotBlog();
                        break;
                    case HOTNEWS:
                        getHotNews();
                        break;
                }
            }
        });

        if (adapter48 != null) {
            adapter48.setListen(new onAdapterTouch() {
                @Override
                public void onAdapterClick(View view, int pos) {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, location[0], location[1], 0, 0);
                    Intent intent = null;
                    intent = new Intent(frmctx, BlogDetailActivity.class);
                    intent.putExtra("INFO", info48s.get(pos));
                    ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
                }

                @Override
                public void onAdapterLongClick(View view, int pos) {

                }
            });
        }

        if (hotadapter != null) {
            hotadapter.setListen(new onAdapterTouch() {
                @Override
                public void onAdapterClick(View view, int pos) {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, location[0], location[1], 0, 0);
                    Intent intent = null;
                    intent = new Intent(frmctx, BlogDetailActivity.class);
                    intent.putExtra("INFO", hotbloginfos.get(pos));
                    ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
                }

                @Override
                public void onAdapterLongClick(View view, int pos) {

                }
            });
        }
        if (newsadapter != null) {
            newsadapter.setListen(new onAdapterTouch() {
                @Override
                public void onAdapterClick(View view, int pos) {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, location[0], location[1], 0, 0);
                    Intent intent = null;
                    intent = new Intent(frmctx, NewsDetailActivity.class);
                    intent.putExtra("INFO", hotnewsinfos.get(pos));
                    ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
                }

                @Override
                public void onAdapterLongClick(View view, int pos) {

                }
            });
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_explorechild;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        switch (state) {
            case READ48:
                client.getRequestQueue().cancelAll("get48blog");
                break;
            case HOTBLOG:
                client.getRequestQueue().cancelAll("getHotBlog");
                break;
            case HOTNEWS:
                client.getRequestQueue().cancelAll("getHotNews");
                break;
        }
    }

    /**
     * 得到48小时阅读
     */
    private void get48() {
        XMLRequest request = new XMLRequest(Request.Method.GET, Constant.URL + read48 + "/" + (Constant.PAFESIZE*3), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sl.setRefreshing(false);
                Toast.makeText(frmctx, "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        }, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                info48s.clear();
                info48s.addAll(XMLUtils.getInfos(response));
                adapter48.notifyDataSetChanged();
                sl.setRefreshing(false);
            }
        });
        request.setTag("get48blog");
        client.addTask(request);
    }

    /**
     * 得到推荐博客
     */
    private void getHotBlog() {
        XMLRequest request = new XMLRequest(Request.Method.GET, Constant.URL + hotblog + (Constant.PAFESIZE * 3), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sl.setRefreshing(false);
                Toast.makeText(frmctx, "获取数据失败", Toast.LENGTH_SHORT).show();
                hotblogpage--;
                if (hotblogpage <= 1) {
                    hotblogpage = 1;
                }
            }
        }, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                if (hotblogpage == 1) {
                    hotbloginfos.clear();
                    hotbloginfos.addAll(XMLUtils.getInfos(response));
                    hotadapter.notifyDataSetChanged();
                    sl.setRefreshing(false);
                } else {
                    hotbloginfos.addAll(XMLUtils.getInfos(response));
                    hotadapter.notifyDataSetChanged();
                    sl.setLoading(false);
                }
            }
        });
        request.setTag("getHotBlog");
        client.addTask(request);
    }

    /**
     * 得到热门新闻
     */
    private void getHotNews() {
        XMLRequest request = new XMLRequest(Request.Method.GET, Constant.NEWSURL + hotnews + "/" + (Constant.PAFESIZE * 3), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sl.setRefreshing(false);
                Toast.makeText(frmctx, "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        }, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                hotnewsinfos.clear();
                hotnewsinfos.addAll(XMLUtils.getInfos(response));
                newsadapter.notifyDataSetChanged();
                sl.setRefreshing(false);
            }
        });
        request.setTag("getHotNews");
        client.addTask(request);
    }
}

