package com.yjx.cnblog.bean;/**
 * Created by yjx on 15/5/1.
 */

import java.io.Serializable;

/**
 * User: YJX
 * Date: 2015-05-01
 * Time: 13:38
 * 博客bean
 */
public class InfoBean implements Serializable {


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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBlogapp() {
        return blogapp;
    }

    public void setBlogapp(String blogapp) {
        this.blogapp = blogapp;
    }

    public int getDiggs() {
        return diggs;
    }

    public void setDiggs(int diggs) {
        this.diggs = diggs;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }


    private String id;
    private String title;
    private String summary;
    private String publishedtime;
    private String updatedtime;
    private String url;
    private String blogapp;
    private int diggs;
    private int views;
    private int comments;
    private AuthorBean author;
    private String topic;
    private String topicIcon;
    private String sourceName;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicIcon() {
        return topicIcon;
    }

    public void setTopicIcon(String topicIcon) {
        this.topicIcon = topicIcon;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
