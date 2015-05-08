package com.yjx.cnblog.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * User: YJX
 * Date: 2015-04-27
 * Time: 16:37
 * 对vollye简单的封装
 */
public class AsynHttpClient {
    private RequestQueue requestQueue;
    private static AsynHttpClient instance;
    private  AsynHttpClient(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }
    /**
     * 单列模式
     * @param context
     * @return
     */
    public static AsynHttpClient getInstance(Context context) {
        if (instance==null){
            synchronized (AsynHttpClient.class){
                if (instance==null){
                    instance=new AsynHttpClient(context);
                }
            }
        }
        return instance;
    }

    /**
     * 添加任务
     * @param task
     */
    public  void addTask(Request task){
        requestQueue.add(task);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
