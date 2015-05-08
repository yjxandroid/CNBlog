package com.yjx.cnblog.activity;/**
 * Created by yjx on 15/5/8.
 */

import android.view.View;

import com.squareup.picasso.Picasso;
import com.yjx.cnblog.R;
import com.yjx.cnblog.base.BaseActivity;

import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;



/**
 * User: YJX
 * Date: 2015-05-08
 * Time: 20:27
 * 展示图片页面
 */
public class ShowImgActivity extends BaseActivity {
    @InjectView(value = R.id.iv_img)
    PhotoView iv_img;
    String url;
    @Override
    public int getLayoutId() {
        return R.layout.activity_showimg;
    }

    @Override
    protected void initData() {
        super.initData();
        url=getIntent().getStringExtra("imageurl");
        Picasso.with(ctx).load(url).into(iv_img);
    }

    @Override
    public void onClick(View v) {

    }
}
