package com.atguigu.shopping_0224.type.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shopping_0224.base.BaseFragment;

/**
 * 作者：田学伟 on 2017/6/8 19:28
 * QQ：93226539
 * 作用：
 */

public class ListFragment extends BaseFragment {
    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("ListFragment");
    }
}
