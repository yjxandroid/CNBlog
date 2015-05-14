package com.yjx.cnblog.activity;/**
 * Created by yjx on 15/5/10.
 */

import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.orhanobut.logger.Logger;
import com.yjx.cnblog.R;
import com.yjx.cnblog.base.BaseActivity;
import com.yjx.cnblog.utils.StringUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.InjectView;

/**
 * User: YJX
 * Date: 2015-05-10
 * Time: 17:56
 * 登陆页面
 */
public class LoginActivity extends BaseActivity {
    private final static String ENCRYPT = "javascript:encryptLoginInfo('%s','%s')";
    private final static String PAGE = "http://passport.cnblogs.com/user/signin?ReturnUrl=http%3A%2F%2Fwww.cnblogs.com%2F";
    @InjectView(value = R.id.et_name)
    AppCompatEditText et_name;
    @InjectView(value = R.id.et_password)
    AppCompatEditText et_password;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        super.initData();
        login();
    }

    @Override
    public void onClick(View v) {

    }


    private void login() {
        WebView webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new Android(), "android");
        webview.loadUrl("file:///android_asset/login.html");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Logger.d("HTML URL=" + url);
                if ("file:///android_asset/login.html".equals(url)) {
                    view.loadUrl(String.format(ENCRYPT, et_name.getText().toString(), et_password.getText().toString()));
                }
            }
        });
    }

    public class Android {
        @JavascriptInterface
        public void onEncrypted(String name, String password) {
            Logger.d("Encrpyted", "name=" + name + ", pwd=" + password);
            get(name, password);
        }
    }


    /**
     * 得到VerificationToken
     *
     * @param html
     * @return
     */
    private String getVerificationToken(String html) {
        Document doc = Jsoup.parse(html);
        String url = doc.select("#c_login_logincaptcha_CaptchaImage").attr("src");
        if (!TextUtils.isEmpty(url)) {
            Logger.d("skip auto login as captcha is needed");
            return "";
        }
        Element script = doc.select("script").get(2);
        Pattern p = Pattern.compile("(?is)'VerificationToken': '(.+?)'");
        Matcher m = p.matcher(script.html());
        String VerificationToken;
        if (m.find()) {
            VerificationToken = m.group(1);
            return VerificationToken;
        } else {
            return "";
        }
    }

    private String getCode(String html) {
        Document doc = Jsoup.parse(html);
        String url = doc.select("#LoginCaptcha_CaptchaImage").attr("src");
        if (TextUtils.isDigitsOnly(url)) {
            return url;
        }
        //BotDetect.Init('LoginCaptcha', '6c5b12a021d8460fa8bc87cdf96f0d80', 'captcha_code_input', true, true, true, true, 1200, 7200, 0, true);
        Matcher matcher = Pattern.compile("(?is)BotDetect.Init\\('LoginCaptcha', '(.+?)',").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }


    private void get(final String name, final String password) {
        StringRequest request = new StringRequest(Request.Method.GET, PAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String token = getVerificationToken(response);
                Log.e("token", token);
                Log.e("CODE", getCode(response));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DefaultHttpClient httpClient = new DefaultHttpClient();
                            HttpPost localHttpPost = new HttpPost("http://passport.cnblogs.com/user/signin");
                            localHttpPost.addHeader("VerificationToken", token);
                            localHttpPost.addHeader("dataType", "json");
                            localHttpPost.addHeader("Content-Type", "application/json; charset=utf-8");
                            localHttpPost.addHeader("Host","passport.cnblogs.com");
                            localHttpPost.addHeader("Origin", "http://passport.cnblogs.com");
                            localHttpPost.addHeader("Referer", "http://passport.cnblogs.com/user/signin");
                            localHttpPost.addHeader("X-Requested-With","XMLHttpRequest");
                            JSONObject data = new JSONObject();
                            data.put("input1", name);
                            data.put("input2", password);
                            data.put("remember", "true");
                            localHttpPost.setEntity(new StringEntity(data.toString(), "utf-8"));
                            HttpResponse response = httpClient.execute(localHttpPost);
                            String content = StringUtils.readIn(response.getEntity().getContent());
                            Log.e("INFO", content);


                            List<Cookie> cookies = httpClient.getCookieStore().getCookies();
                            int size = cookies.size();
                            String serverid = "";
                            for (int i = 0; i < size; i++) {
                                Cookie cookie = cookies.get(i);
                                if ("SERVERID".equals(cookie.getName())) {
                                    serverid = cookie.getValue();
                                    break;
                                }
                            }
                            String cnblogcookie = "";
                            for (int i = 0; i < size; i++) {
                                Cookie cookie = cookies.get(i);
                                if (".CNBlogsCookie".equals(cookie.getName())) {
                                    cnblogcookie = cookie.getValue();
                                    break;
                                }
                            }
//                            HttpGet get = new HttpGet(Constant.USRTINFO);
//                            get.addHeader("SERVERID", serverid);
//                            get.addHeader(".CNBlogsCookie", cnblogcookie);
//                            response=httpClient.execute(get);
//                            Log.e("INFO", EntityUtils.toString(response.getEntity(),EntityUtils.getContentCharSet(response.getEntity())));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                ).start();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setTag("gethtml");
        client.addTask(request);
    }

}
