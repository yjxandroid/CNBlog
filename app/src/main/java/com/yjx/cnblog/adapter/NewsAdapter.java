package com.yjx.cnblog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yjx.cnblog.R;
import com.yjx.cnblog.bean.InfoBean;
import com.yjx.cnblog.listener.onAdapterTouch;
import com.yjx.cnblog.utils.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * User: YJX
 * Date: 2015-05-01
 * Time: 18:34
 * 博客列表适配器
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<InfoBean> mLists;
    private Context context;
    private onAdapterTouch listen;

    public onAdapterTouch getListen() {
        return listen;
    }

    public void setListen(onAdapterTouch listen) {
        this.listen = listen;
    }

    public NewsAdapter(Context context, List<InfoBean> mLists) {
        this.mLists = mLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_title.setText(mLists.get(position).getTitle());
        holder.tv_detail.setText(mLists.get(position).getSummary());
        holder.tv_time.setText(TimeUtils.parseTime(mLists.get(position).getPublishedtime())+" 发布");
        holder.tv_name.setText(mLists.get(position).getSourceName());
        holder.iv_header.setImageResource(R.drawable.ic_launcher);
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listen != null) {
                    listen.onAdapterClick(holder.ll_item, position);
                }
            }
        });
        holder.tv_comments.setText(mLists.get(position).getComments() + "评论");
        holder.tv_read.setText(mLists.get(position).getViews() + "阅读");
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(value = R.id.iv_header)
        ImageView iv_header;
        @InjectView(value = R.id.tv_name)
        TextView tv_name;
        @InjectView(value = R.id.tv_title)
        TextView tv_title;
        @InjectView(value = R.id.tv_detail)
        TextView tv_detail;
        @InjectView(value = R.id.tv_time)
        TextView tv_time;
        @InjectView(value = R.id.ll_item)
        LinearLayout ll_item;
        @InjectView(value = R.id.tv_read)
        TextView tv_read;

        @InjectView(value = R.id.tv_comments)
        TextView tv_comments;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
