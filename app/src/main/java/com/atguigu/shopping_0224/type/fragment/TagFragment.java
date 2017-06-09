package com.atguigu.shopping_0224.type.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shopping_0224.R;
import com.atguigu.shopping_0224.base.BaseFragment;
import com.atguigu.shopping_0224.type.adapter.TagGridViewAdapter;
import com.atguigu.shopping_0224.type.bean.TagBean;
import com.atguigu.shopping_0224.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * 作者：田学伟 on 2017/6/8 19:29
 * QQ：93226539
 * 作用：
 */

public class TagFragment extends BaseFragment {

    @InjectView(R.id.gv_tag)
    GridView gvTag;
    private List<TagBean.ResultEntity> result;
    private TagGridViewAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get().url(Constants.TAG_URL)
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

    /**
     * 解析数据
     *
     * @param json
     */
    private void processData(String json) {
        TagBean tagBean = JSON.parseObject(json, TagBean.class);
        result = tagBean.getResult();
        if (result != null && result.size() > 0) {
            adapter = new TagGridViewAdapter(mContext, result);
            gvTag.setAdapter(adapter);

            //设置item的点击事件
            gvTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TagBean.ResultEntity resultEntity = result.get(position);
                    Toast.makeText(mContext, "" + resultEntity.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
