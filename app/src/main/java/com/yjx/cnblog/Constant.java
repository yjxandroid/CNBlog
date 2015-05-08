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

    //博客详情请求
    public static final String BLOGDETAILURL="http://cnblogs.davismy.com/Handler.ashx?op=GetBlogContent&blog_id=";

    //新闻详情
    public static final String NEWSURLDETAIL="http://cnblogs.davismy.com/Handler.ashx?op=GetNewContent&news_id=";

    public static final String SEARCHUSER="http://cnblogs.davismy.com/Handler.ashx?op=AuthorSearch&key=";//搜索作者



    public static final String PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCp0wHYbg/NOPO3nzMD3dndwS0MccuMeXCHgVlGOoYyFwLdS24Im2e7YyhB0wrUsyYf0/nhzCzBK8ZC9eCWqd0aHbdgOQT6CuFQBMjbyGYvlVYU2ZP7kG9Ft6YV6oc9ambuO7nPZh+bvXH0zDKfi02prknrScAKC0XhadTHT3Al0QIDAQAB";//博客园登录公钥

    public static final int PAFESIZE=10;//默认每页请求的个数
    public static final String LOGTAG="CNBLOG";//日志TAG
    public static final String DBNAME="cnblog_db";//数据库名称
}
