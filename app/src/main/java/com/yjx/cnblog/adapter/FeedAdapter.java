package com.yjx.cnblog.adapter;/**
 * Created by yjx on 15/5/9.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yjx.cnblog.R;
import com.yjx.cnblog.bean.FeedBean;
import com.yjx.cnblog.listener.onAdapterTouch;
import com.yjx.cnblog.utils.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * User: YJX
 * Date: 2015-05-09
 * Time: 22:38
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {
    private List<FeedBean> mLists;
    private Context context;
    private onAdapterTouch listen;

    public FeedAdapter(List<FeedBean> mLists, Context context) {
        this.mLists = mLists;
        this.context = context;
    }

    public onAdapterTouch getListen() {
        return listen;
    }

    public void setListen(onAdapterTouch listen) {
        this.listen = listen;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);

        return new FeedHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        FeedBean item = mLists.get(position);
        if (!TextUtils.isEmpty(item.getContent()))
            holder.tv_feed.setText(Html.fromHtml(item.getContent()));
        holder.tv_floor.setText("#" + (position + 1) + "楼");
        if (!TextUtils.isEmpty(item.getAuthor().getName()))
            holder.tv_name.setText(item.getAuthor().getName());
        if (!TextUtils.isEmpty(item.getPublishedtime()))
            holder.tv_time.setText(TimeUtils.parseTime(item.getPublishedtime()));
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public static class FeedHolder extends RecyclerView.ViewHolder {
        @InjectView(value = R.id.tv_floor)
        TextView tv_floor;
        @InjectView(value = R.id.tv_name)
        TextView tv_name;
        @InjectView(value = R.id.tv_time)
        TextView tv_time;
        @InjectView(value = R.id.tv_feed)
        TextView tv_feed;

        public FeedHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
