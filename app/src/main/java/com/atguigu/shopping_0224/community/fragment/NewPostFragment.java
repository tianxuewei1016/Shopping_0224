package com.atguigu.shopping_0224.community.fragment;

import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shopping_0224.R;
import com.atguigu.shopping_0224.base.BaseFragment;
import com.atguigu.shopping_0224.community.bean.NewPostBean;
import com.atguigu.shopping_0224.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * 作者：田学伟 on 2017/6/9 21:08
 * QQ：93226539
 * 作用：
 */

public class NewPostFragment extends BaseFragment {


    @InjectView(R.id.lv_new_post)
    ListView lvNewPost;

    @Override
    public View initView() {
        View rootView = View.inflate(mContext, R.layout.fragment_news_post, null);
        ButterKnife.inject(this, rootView);
        return rootView;
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
        OkHttpUtils.get().url(Constants.NEW_POST_URL)
                .id(100).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
//                Log.e("TAG", "联网失败==" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
//                Log.e("TAG", "新帖联网成功==");
                processData(response);
            }
        });
    }

    private void processData(String json) {
        NewPostBean bean = JSON.parseObject(json, NewPostBean.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
