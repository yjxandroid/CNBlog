package com.yjx.cnblog.net;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 杨佳星 on 2015/1/9.
 * 自定义请求
 */
public class JSONRequest<T> extends Request<T> {
    private final Response.Listener<T> mListener;
    private Class<T> mClass;
    public JSONRequest(int method, String url,  Class<T> mClass, Response.Listener<T> listener, Response.ErrorListener errorlistener) {
        super(method, url, errorlistener);
        this.mListener=listener;
        this.mClass=mClass;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        if(response.statusCode!=200){
              return Response.error(new VolleyError("操作失败"));
        }else {
            try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                return Response.success(JSON.parseObject(jsonString, mClass),
                        HttpHeaderParser.parseCacheHeaders(response,true));
            } catch (Exception e) {
                e.printStackTrace();
                return Response.error(new ParseError(e));
            }
        }
    }
    @Override
    protected void deliverResponse(T response) {
      mListener.onResponse(response);
    }

    /*
       设置请求头 Header
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> map=new HashMap<String,String>();
        map.put("Charset","UTF-8");
        return map;
    }

    /*
      设置超时
     */
    @Override
    public RetryPolicy getRetryPolicy() {
        RetryPolicy retryPolicy= new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return retryPolicy;
    }


}
