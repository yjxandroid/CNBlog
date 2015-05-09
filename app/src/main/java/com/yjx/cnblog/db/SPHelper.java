package com.yjx.cnblog.db;/**
 * Created by yjx on 15/5/9.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.yjx.cnblog.Constant;

/**
 * User: YJX
 * Date: 2015-05-09
 * Time: 20:57
 * SP
 */
public final class SPHelper {

    /**
     * 得到2g/3g下是否可以下载图片
     *
     * @param context
     * @return
     */
    public static boolean isShowImg(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.LOGTAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isShowImg", true);
    }

    /**
     * 设置2g/3g下是否可以下载图片
     *
     * @param context
     * @return
     */
    public static void setShowImg(Context context, boolean is) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.LOGTAG, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isShowImg", is).commit();
    }

    /**
     * 得到wifi下是否可以自动缓存
     *
     * @param context
     * @return
     */
    public static boolean isWifiDown(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.LOGTAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isWifiDown", true);
    }

    /**
     * 设置wifi下是否可以自动缓存
     *
     * @param context
     * @return
     */
    public static void setWifiDown(Context context, boolean is) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.LOGTAG, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isWifiDown", is).commit();
    }

    /**
     * 设置主题
     *
     * @param context
     * @return true 表示明亮主题 false 表示安主题
     */
    public static void setTheme(Context context, boolean is) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.LOGTAG, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("theme", is).commit();
    }


    /**
     * 得到主题
     *
     * @param context
     * @return
     */
    public static boolean getTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.LOGTAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("theme", false);
    }

}
