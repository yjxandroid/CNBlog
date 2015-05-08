package com.yjx.cnblog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yjx.cnblog.R;
import com.yjx.cnblog.base.MBaseAdapter;
import com.yjx.cnblog.bean.InfoBean;
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
public class AuthorBlogAdapter extends MBaseAdapter<InfoBean>{


    public AuthorBlogAdapter(List<InfoBean> mLists, Context context) {
        super(mLists, context);
    }

    @Override
    public View getXView(int position, View convertView, ViewGroup parent) {
        AuthorBlogAdapter.ViewHolder holder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_authorblog, parent, false);
            holder=new AuthorBlogAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (AuthorBlogAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(mLists.get(position).getTitle());
        holder.tv_detail.setText(mLists.get(position).getSummary());
        holder.tv_time.setText(TimeUtils.parseTime(mLists.get(position).getPublishedtime()));
        return convertView;
    }

    public static class ViewHolder {
        @InjectView(value = R.id.tv_title)
        TextView tv_title;
        @InjectView(value = R.id.tv_detail)
        TextView tv_detail;
        @InjectView(value = R.id.tv_time)
        TextView tv_time;
        @InjectView(value = R.id.ll_item)
        LinearLayout ll_item;

        public ViewHolder(View itemView) {
            ButterKnife.inject(this, itemView);
        }
    }
}
