package com.yjx.cnblog;/**
 * Created by yjx on 15/4/28.
 */

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;


/**
 * User: YJX
 * Date: 2015-04-28
 * Time: 22:54
 */
public class CNBlogApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化日志
        Logger
                .init(Constant.LOGTAG)
                .setMethodCount(3)
                .hideThreadInfo()
                .setLogLevel(LogLevel.NONE);
    }
}
