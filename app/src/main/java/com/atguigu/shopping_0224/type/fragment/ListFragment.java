package com.atguigu.shopping_0224.type.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.atguigu.shopping_0224.R;
import com.atguigu.shopping_0224.base.BaseFragment;
import com.atguigu.shopping_0224.type.adapter.TypeLeftAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：田学伟 on 2017/6/8 19:28
 * QQ：93226539
 * 作用：
 */

public class ListFragment extends BaseFragment {

    @InjectView(R.id.lv_left)
    ListView lvLeft;
    @InjectView(R.id.rv_right)
    RecyclerView rvRight;

    //网络请求得到数据
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};
    private TypeLeftAdapter leftAdapter;
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_list, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        leftAdapter = new TypeLeftAdapter(mContext,titles);
        lvLeft.setAdapter(leftAdapter);

        //设置item的点击事件
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.传入被点击的位置
                leftAdapter.changeSelected(position);
                //2.适配器刷新
                leftAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
