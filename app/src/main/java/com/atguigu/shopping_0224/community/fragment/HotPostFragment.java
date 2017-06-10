package com.atguigu.shopping_0224.community.fragment;

import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shopping_0224.R;
import com.atguigu.shopping_0224.base.BaseFragment;
import com.atguigu.shopping_0224.community.adapter.HotPostListViewAdapter;
import com.atguigu.shopping_0224.community.bean.HotPostBean;
import com.atguigu.shopping_0224.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * 作者：田学伟 on 2017/6/9 21:07
 * QQ：93226539
 * 作用：
 */

public class HotPostFragment extends BaseFragment {


    @InjectView(R.id.lv_hot_post)
    ListView lvHotPost;
    private HotPostListViewAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_hot_post, null);
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
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(Constants.HOT_POST_URL)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
            }
        });
    }

    private void processData(String json) {
        HotPostBean bean = JSON.parseObject(json, HotPostBean.class);

        List<HotPostBean.ResultEntity> result = bean.getResult();
        if (result != null && result.size() > 0) {

            //设置适配器
            adapter = new HotPostListViewAdapter(mContext, result);
            lvHotPost.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}