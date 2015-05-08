package com.yjx.cnblog.view;/**
 * Created by yjx on 15/5/1.
 */

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * User: YJX
 * Date: 2015-05-01
 * Time: 22:57
 */
public class SwipeLoadRefreshLayout extends SwipeRefreshLayout {
    private RecyclerView recyclerView;
    //按下去的时候位置
    private int mLastY, mYDown;
    //加载更多的监听器
    private OnLoadListener mOnLoadListener;
    //最小出发距离
    private int mTouchSlop;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    public SwipeLoadRefreshLayout(Context context) {
        this(context, null);
    }

    public SwipeLoadRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // RecyclerView初始化
        if (recyclerView == null) {
            int childs = getChildCount();
            if (childs > 0) {
                for (int i = 0; i < childs; i++) {
                    View childView = getChildAt(i);
                    if (childView instanceof RecyclerView) {
                        recyclerView = (RecyclerView) childView;
                        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                if (canLoad()) {
//                                    loadData();
                                }
                            }

                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                            }
                        });
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                if (canLoad()) {
//添加View
                } else {

                }
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {
        if (recyclerView != null && recyclerView.getAdapter() != null) {
            return ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == (recyclerView.getAdapter().getItemCount() - 1);
        }
        return false;
    }

    public void setOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }

    /**
     * 加载更多的监听器
     *
     * @author mrsimple
     */
    public static interface OnLoadListener {
        public void onLoad();
    }

    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
        } else {
            mYDown = 0;
            mLastY = 0;
        }
    }
}
