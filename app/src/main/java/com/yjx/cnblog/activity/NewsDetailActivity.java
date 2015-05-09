package com.yjx.cnblog.activity;/**
 * Created by yjx on 15/5/2.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import com.yjx.cnblog.Constant;
import com.yjx.cnblog.R;
import com.yjx.cnblog.base.BaseActivity;
import com.yjx.cnblog.bean.InfoBean;
import com.yjx.cnblog.db.SPHelper;
import com.yjx.cnblog.utils.HTMLUtils;
import com.yjx.cnblog.utils.NetUtils;
import com.yjx.cnblog.utils.StringUtils;
import com.yjx.cnblog.utils.TimeUtils;
import com.yjx.cnblog.view.CustomScrollView;

import org.json.JSONObject;

import butterknife.InjectView;
import dmax.dialog.SpotsDialog;

/**
 * User: YJX
 * Date: 2015-05-02
 * Time: 21:31
 */
public class NewsDetailActivity extends BaseActivity {
    @InjectView(value = R.id.toolbar)
    Toolbar toolbar;
    @InjectView(value = R.id.wv_blogdetail)
    WebView wv_blogdetail;
    @InjectView(value = R.id.sl)
    CustomScrollView sl;
    @InjectView(value = R.id.tv_author)
    TextView tv_author;
    @InjectView(value = R.id.tv_title)
    TextView tv_title;
    @InjectView(value = R.id.ll_bottommenu)
    LinearLayout ll_bottommenu;
    @InjectView(value = R.id.ll_comment)
    LinearLayout ll_comment;
    int screenWidht, screenHeight;
    int bottomheight;
    final int MAX_DIS = 15;
    boolean isshow = true;

    InfoBean info;//新闻基础信息
    AlertDialog dialog;
    DB snappydb;

    @Override
    public int getLayoutId() {
        return R.layout.activity_newsdetail;
    }

    @Override
    protected void initData() {
        super.initData();
        info = (InfoBean) getIntent().getSerializableExtra("INFO");
        tv_author.setText(info.getSourceName() + " 发表于" + TimeUtils.parseTime(info.getPublishedtime()));
        tv_title.setText(info.getTitle());
        initToolbar();
        this.wv_blogdetail.getSettings().setDefaultTextEncodingName("utf-8");
        WebSettings localWebSettings = this.wv_blogdetail.getSettings();
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setDefaultFontSize(10);
        localWebSettings.setCacheMode(-1);
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        screenWidht = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        ll_bottommenu.post(new Runnable() {
            @Override
            public void run() {
                bottomheight = ll_bottommenu.getMeasuredHeight();
            }
        });
        try {
            snappydb = DBFactory.open(ctx, "books");
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        if (NetUtils.isNet(ctx)) {
            dialog = new SpotsDialog(this, R.style.Custom);
            dialog.setTitle("数据加载中...");
            dialog.show();
            getNewsDetail();
        } else {
            try {
                wv_blogdetail.loadDataWithBaseURL("file:///android_asset/", snappydb.get(info.getId()), "text/html", "utf-8", null);
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        sl.setOnCusScrollChanged(new CustomScrollView.onCusScrollChanged() {
            @Override
            public void onCusScrollChanged(int l, int t, int oldl, int oldt) {
                if (t - oldt > MAX_DIS && isshow) {
                    isshow = false;
                    ll_bottommenu.animate().translationY(bottomheight).setDuration(200).start();
                }
                if (t - oldt < -MAX_DIS && !isshow) {
                    isshow = true;
                    ll_bottommenu.animate().translationY(0).setDuration(200).start();
                }
            }
        });
        wv_blogdetail.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    dialog.cancel();
                }
            }
        });
        wv_blogdetail.addJavascriptInterface(new JavaScriptInterface(this), "cnblog");
        ll_comment.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ll_bottommenu.animate().translationY(bottomheight).setDuration(200).start();
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
    protected void onDestroy() {
        super.onDestroy();
        client.getRequestQueue().cancelAll("getnewsdetail");
        dialog.cancel();
        if (snappydb != null) {
            try {
                snappydb.close();
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        ll_bottommenu.animate().translationY(bottomheight).setDuration(200).start();
        sl.animate().translationY(screenHeight).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
                overridePendingTransition(0, 0);
            }
        }).start();

    }

    /**
     * 得到新闻详情
     */
    private void getNewsDetail() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.NEWSURLDETAIL + info.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.has("data")) {
                        String main = StringUtils.readIn(getAssets().open("content.html"));
                        String content = object.getString("data");
//                        content = content.replace("<br />", "\n").replace("<br/>", "\n").replace("&nbsp;&nbsp;", "\t").replace("&nbsp;", " ").replace("&#39;", "\\").replace("&quot;", "\\").replace("&gt;", ">").replace("&lt;", "<").replace("&amp;", "&");
                          content = content.replace("background-color: #F5F5F5;", "background-color: #4e4e4e;").replace("color: #000000;", "color: #8590A2;").replace("color: #0000ff;", "color: #1799ff;").replace("color: #008000;", "color: #00b200;").replace("color: #800000;", "color: #ca0000;");
//                        content= HTMLUtils.replaceFont(content);
                          if (!SPHelper.getTheme(ctx)){
                              main = main.replace("{csses}", "style");
                          }else{
                              main = main.replace("{csses}", "style-night");
                          }
                        if (SPHelper.isShowImg(ctx)) {
                            if (NetUtils.isWifi(ctx)) {
                                content = main.replace("{html}", content);
                                wv_blogdetail.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "utf-8", null);
                                snappydb.del(info.getId());
                                snappydb.put(info.getId(), content);
                            } else {
                                content = HTMLUtils.replaceImgTag(content);
                                content = HTMLUtils.FormatImgTag(content);
                                content = main.replace("{html}", content);
                                wv_blogdetail.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "utf-8", null);
                                snappydb.del(info.getId());
                                snappydb.put(info.getId(), content);
                            }
                        } else {
                            content = main.replace("{html}", content);
                            wv_blogdetail.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "utf-8", null);
                            snappydb.del(info.getId());
                            snappydb.put(info.getId(), content);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Toast.makeText(ctx, "请求数据失败", Toast.LENGTH_SHORT).show();

            }
        });

        request.setTag("getnewsdetail");
        client.addTask(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_comment:
                Intent intent = new Intent(ctx, CommentsActivity.class);
                intent.putExtra("BLOGID", info.getId());
                intent.putExtra("TYPE", 2);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
        }
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("新闻正文");
    }

    public class JavaScriptInterface {
        Context mContext;

        public JavaScriptInterface(Context paramContext) {
            this.mContext = paramContext;
        }

        @JavascriptInterface
        public void openURL(String url) {
            Intent localIntent = new Intent();
            localIntent.setAction("android.intent.action.VIEW");
            localIntent.setData(Uri.parse(url));
            mContext.startActivity(localIntent);
        }

        @JavascriptInterface
        public void showImg(String url) {
            Intent localIntent = new Intent();
            localIntent.setClass(this.mContext, ShowImgActivity.class);
            localIntent.putExtra("imageurl", url);
            this.mContext.startActivity(localIntent);
            overridePendingTransition(0,0);
        }
    }
}
