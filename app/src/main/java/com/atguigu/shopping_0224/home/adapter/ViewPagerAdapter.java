package com.atguigu.shopping_0224.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atguigu.shopping_0224.home.bean.HomeBean;
import com.atguigu.shopping_0224.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 作者：田学伟 on 2017/6/4 15:23
 * QQ：93226539
 * 作用：活动的适配器
 */

public class ViewPagerAdapter extends PagerAdapter{
    private final Context mContext;
    private final List<HomeBean.ResultEntity.ActInfoEntity> datas;

    public ViewPagerAdapter(Context mContext, List<HomeBean.ResultEntity.ActInfoEntity> act_info) {
        this.mContext = mContext;
        this.datas = act_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext)
                .load(Constants.BASE_URL_IMAGE+datas.get(position).getIcon_url())
                .into(imageView);
        //添加到ViewPager容器中
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
