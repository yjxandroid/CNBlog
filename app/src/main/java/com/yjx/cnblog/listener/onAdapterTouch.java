package com.yjx.cnblog.listener;

import android.view.View;

/**
 * Created by yjx on 15/5/1.
 *  RecyclerView中的触摸事件
 */
public interface onAdapterTouch {
    void onAdapterClick(View view, int pos);//单击事件

    void onAdapterLongClick(View view, int pos);//长按事件

}
