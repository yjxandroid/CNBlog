package com.yjx.cnblog.utils;/**
 * Created by yjx on 15/4/30.
 */

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * User: YJX
 * Date: 2015-04-30
 * Time: 23:07
 * APP信息相关工具类
 */
public final class APPUtils {

    /**
     * 得到APP的数字版本号
     * @param context 上下文
     * @param packname 包名
     * @return
     */
    public static int getVersionCode(Context context,String packname){
        PackageManager packageManager=context.getPackageManager();
        try {
            PackageInfo packageInfo=packageManager.getPackageInfo(packname,0);
            return  packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 得到APP的版本号
     * @param context 上下文
     * @param packname 包名
     * @return
     */
    public static String getVersionName(Context context,String packname){
        PackageManager packageManager=context.getPackageManager();
        try {
            PackageInfo packageInfo=packageManager.getPackageInfo(packname,0);
            return  packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }







    public static void hideSoftInput(Context context,Activity activity) {
        InputMethodManager inputMethodManager;
        inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View v = activity.getCurrentFocus();
            if (v == null) {
                return;
            }

            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
//            activity.clearFocus();
        }
    }
}
