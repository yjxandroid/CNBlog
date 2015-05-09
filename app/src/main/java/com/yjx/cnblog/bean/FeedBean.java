package com.yjx.cnblog.bean;/**
 * Created by yjx on 15/5/9.
 */

import java.io.Serializable;

/**
 * User: YJX
 * Date: 2015-05-09
 * Time: 22:30
 */
public class FeedBean implements Serializable {
    private String id;
    private String title;
    private String publishedtime;
    private String updatedtime;
    private AuthorBean author;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedtime() {
        return publishedtime;
    }

    public void setPublishedtime(String publishedtime) {
        this.publishedtime = publishedtime;
    }

    public String getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(String updatedtime) {
        this.updatedtime = updatedtime;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
