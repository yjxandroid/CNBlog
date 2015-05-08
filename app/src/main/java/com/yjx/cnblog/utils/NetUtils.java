package com.yjx.cnblog.utils;/**
 * Created by yjx on 15/5/7.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * User: YJX
 * Date: 2015-05-07
 * Time: 20:47
 */
public final class NetUtils {


    public static boolean isNet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infp = manager.getActiveNetworkInfo();
        if (infp == null) {
            return false;
        }
        return infp.isAvailable();
    }


    public static boolean isWifi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infp = manager.getActiveNetworkInfo();
        if (infp == null) {
            return false;
        }
        if (infp.getType()==ConnectivityManager.TYPE_WIFI){
            return true;
        }else{
            return false;
        }
    }
}
