package com.yjx.cnblog.activity;/**
 * Created by yjx on 15/5/2.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yjx.cnblog.Constant;
import com.yjx.cnblog.R;
import com.yjx.cnblog.adapter.AuthorAdapter;
import com.yjx.cnblog.base.BaseActivity;
import com.yjx.cnblog.bean.AuthorBean;
import com.yjx.cnblog.bean.AuthorsBean;
import com.yjx.cnblog.listener.onAdapterTouch;
import com.yjx.cnblog.net.JSONRequest;
import com.yjx.cnblog.utils.APPUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import dmax.dialog.SpotsDialog;

/**
 * User: YJX
 * Date: 2015-05-02
 * Time: 23:23
 * 搜索页面
 */
public class SearchActivity extends BaseActivity {
    @InjectView(value = R.id.rv_author)
    RecyclerView rv_author;
    @InjectView(value = R.id.search_view)
    SearchView search_view;
    AuthorAdapter adapter;
    List<AuthorBean> authors;
    int screenHeight;
    AlertDialog dialog;

    Handler handler;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }


    @Override
    protected void initData() {
        super.initData();

        WindowManager windowManager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;

        authors = new ArrayList<AuthorBean>();
        adapter = new AuthorAdapter(ctx, authors);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        rv_author.setLayoutManager(manager);
        rv_author.setHasFixedSize(true);
        rv_author.setItemAnimator(new DefaultItemAnimator());
        rv_author.setAdapter(adapter);
        dialog = new SpotsDialog(ctx, R.style.Search);

        handler = new Handler();
    }

    @Override
    protected void setListener() {
        super.setListener();
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    APPUtils.hideSoftInput(ctx, SearchActivity.this);
                    dialog.show();
                    search(query);

                } else
                    Toast.makeText(ctx, "请输入关键字", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        adapter.setListen(new onAdapterTouch() {
            @Override
            public void onAdapterClick(View view, int pos) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, location[0], location[1], 0, 0);
                Intent intent = null;
                intent = new Intent(ctx, AuthorActivity.class);
                intent.putExtra("INFO", authors.get(pos));
                ActivityCompat.startActivity(SearchActivity.this, intent, optionsCompat.toBundle());
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
    public void onBackPressed() {
        rv_author.animate().translationY(screenHeight).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
                overridePendingTransition(0, 0);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.getRequestQueue().cancelAll("search");
        dialog.cancel();
    }

    private void search(final String keys) {
        final StringBuilder sb = new StringBuilder(Constant.SEARCHUSER);
        sb.append('?');
        Map<String,String> params=new HashMap<String,String>();
        params.put("op","AuthorSearch");
        params.put("key",keys);
        for(Map.Entry<String, String> entry : params.entrySet()) {
            try {
                sb.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "utf-8")).append('&');
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        sb.deleteCharAt(sb.length()-1);



        final JSONRequest<AuthorsBean> request = new JSONRequest<AuthorsBean>(Request.Method.GET, sb.toString(), AuthorsBean.class, new Response.Listener<AuthorsBean>() {
            @Override
            public void onResponse(AuthorsBean response) {
                dialog.cancel();
                if (response.getData() != null) {

                    authors.clear();
                    authors.addAll(response.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ctx, "没有该用户请重新输入！！", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Toast.makeText(ctx, "搜索失败", Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag("search");
        client.addTask(request);



    }


}
