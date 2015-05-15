package com.yjx.cnblog;

/**
 * User: YJX
 * Date: 2015-04-28
 * Time: 22:55
 * 常量
 */
public class Constant {
    public static final String URL="http://wcf.open.cnblogs.com/blog/";//网络请求接口
    public static final String NEWSURL="http://wcf.open.cnblogs.com/news/";//新闻接口
    public static final String LOGINURL="http://passport.cnblogs.com/user/signin";//登陆接口
    public static final String USRTINFO="http://passport.cnblogs.com/user/LoginInfo?callback=jQueryCallback";//得到用户信息

    //博客详情请求
    public static final String BLOGDETAILURL="http://cnblogs.davismy.com/Handler.ashx?op=GetBlogContent&blog_id=";

    //新闻详情
    public static final String NEWSURLDETAIL="http://cnblogs.davismy.com/Handler.ashx?op=GetNewContent&news_id=";

    public static final String SEARCHUSER="http://cnblogs.davismy.com/Handler.ashx";//搜索作者

    public static final int PAFESIZE=10;//默认每页请求的个数
    public static final String LOGTAG="CNBLOG";//日志TAG
    public static final String DBNAME="cnblog_db";//数据库名称

}
