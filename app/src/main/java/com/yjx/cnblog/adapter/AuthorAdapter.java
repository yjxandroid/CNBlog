package com.yjx.cnblog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yjx.cnblog.R;
import com.yjx.cnblog.bean.AuthorBean;
import com.yjx.cnblog.listener.onAdapterTouch;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * User: YJX
 * Date: 2015-05-01
 * Time: 18:34
 * 博客列表适配器
 */
public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.ViewHolder> {
    private List<AuthorBean> mLists;
    private Context context;
    private onAdapterTouch listen;

    public onAdapterTouch getListen() {
        return listen;
    }

    public void setListen(onAdapterTouch listen) {
        this.listen = listen;
    }

    public AuthorAdapter(Context context, List<AuthorBean> mLists) {
        this.mLists = mLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_author, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_time.setText(mLists.get(position).getUpdated());
        holder.tv_name.setText(mLists.get(position).getName());
        if (!TextUtils.isEmpty(mLists.get(position).getAvatar()))
            Picasso.with(context).load(mLists.get(position).getAvatar()).error(R.drawable.ic_launcher).into(holder.iv_header);
        else
            holder.iv_header.setImageResource(R.drawable.ic_launcher);
        holder.tv_counts.setText("( " + mLists.get(position).getPostcount() + " ) 博文");
        holder.rl_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listen != null) {
                    listen.onAdapterClick(holder.rl_menu, position);
                }
            }
        });
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
        @InjectView(value = R.id.tv_time)
        TextView tv_time;
        @InjectView(value = R.id.rl_menu)
        RelativeLayout rl_menu;
        @InjectView(value = R.id.tv_counts)
        TextView tv_counts;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
