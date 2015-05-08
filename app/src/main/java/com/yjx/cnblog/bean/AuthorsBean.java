package com.yjx.cnblog.bean;/**
 * Created by yjx on 15/5/3.
 */

import java.util.List;

/**
 * User: YJX
 * Date: 2015-05-03
 * Time: 00:05
 */
public class AuthorsBean  {

    private List<AuthorBean> data;

    public List<AuthorBean> getData() {
        return data;
    }

    public void setData(List<AuthorBean> datas) {
        this.data = datas;
    }
}
