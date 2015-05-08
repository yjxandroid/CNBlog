package com.yjx.cnblog.view;/**
 * Created by yjx on 15/5/2.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * User: YJX
 * Date: 2015-05-02
 * Time: 16:26
 */
public class CustomScrollView extends ScrollView {
    private onCusScrollChanged onCusScrollChanged;

    public CustomScrollView.onCusScrollChanged getOnCusScrollChanged() {
        return onCusScrollChanged;
    }

    public void setOnCusScrollChanged(CustomScrollView.onCusScrollChanged onCusScrollChanged) {
        this.onCusScrollChanged = onCusScrollChanged;
    }

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onCusScrollChanged!=null){
            onCusScrollChanged.onCusScrollChanged(l, t, oldl, oldt);
        }
    }

    public interface  onCusScrollChanged{
        void onCusScrollChanged(int l, int t, int oldl, int oldt);
    }
}
