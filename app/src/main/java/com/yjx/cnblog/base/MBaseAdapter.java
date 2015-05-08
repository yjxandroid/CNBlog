package com.yjx.cnblog.base;/**
 * Created by yjx on 15/4/30.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * User: YJX
 * Date: 2015-04-30
 * Time: 20:26
 */
public abstract class MBaseAdapter<T> extends BaseAdapter {
    protected List<T> mLists;
    protected Context context;
    protected LayoutInflater inflater;

    public MBaseAdapter(List<T> mLists, Context context) {
        this.mLists = mLists;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mLists != null && mLists.size() > 0)
            return mLists.size();
        else
            return 0;
    }

    @Override
    public T getItem(int position) {
        if (mLists != null && mLists.size() > 0)
            return mLists.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getXView(position, convertView, parent);
    }

    public abstract View getXView(int position, View convertView, ViewGroup parent);

}
