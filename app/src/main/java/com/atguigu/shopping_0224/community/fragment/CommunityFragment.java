package com.atguigu.shopping_0224.community.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.atguigu.shopping_0224.R;
import com.atguigu.shopping_0224.base.BaseFragment;
import com.atguigu.shopping_0224.community.adapter.CommunityViewPagerAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：田学伟 on 2017/6/2 19:42
 * QQ：93226539
 * 作用：发现Fragment
 */

public class CommunityFragment extends BaseFragment {

    @InjectView(R.id.ib_community_icon)
    ImageButton ibCommunityIcon;
    @InjectView(R.id.ib_community_message)
    ImageButton ibCommunityMessage;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.tablayout)
    TabLayout tablayout;

    private ArrayList<BaseFragment> fragments;
    private CommunityViewPagerAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_community, null);
        ButterKnife.inject(this, view);
        return view;
    }

    /**
     * 1.把数据绑定到控件上的时候，重新该方法
     * 2.联网请求，把得到的数据绑定到视图上
     */
    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "发现的数据被初始化了...");
        initFragment();
        //设置适配器
        adapter = new CommunityViewPagerAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        //关联ViewPager
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new NewPostFragment());
        fragments.add(new HotPostFragment());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.ib_community_icon, R.id.ib_community_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_community_icon:
                break;
            case R.id.ib_community_message:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }
}