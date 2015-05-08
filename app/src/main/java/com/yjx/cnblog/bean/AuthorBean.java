package com.yjx.cnblog.bean;/**
 * Created by yjx on 15/5/1.
 */

import java.io.Serializable;

/**
 * User: YJX
 * Date: 2015-05-01
 * Time: 13:42
 * 个人信息
 */
public class AuthorBean implements Serializable {
    private String name;
    private String uri;
    private String avatar;
    private String blogapp;
    private String postcount;
    private String updated;

    public String getBlogapp() {
        return blogapp;
    }

    public void setBlogapp(String blogapp) {
        this.blogapp = blogapp;
    }

    public String getPostcount() {
        return postcount;
    }

    public void setPostcount(String postcount) {
        this.postcount = postcount;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
