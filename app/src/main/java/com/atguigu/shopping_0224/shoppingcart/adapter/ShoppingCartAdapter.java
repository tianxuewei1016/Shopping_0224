package com.atguigu.shopping_0224.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shopping_0224.R;
import com.atguigu.shopping_0224.home.bean.GoodsBean;
import com.atguigu.shopping_0224.shoppingcart.utils.CartStorage;
import com.atguigu.shopping_0224.shoppingcart.view.AddSubView;
import com.atguigu.shopping_0224.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：田学伟 on 2017/6/5 21:02
 * QQ：93226539
 * 作用：
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHoler> {
    private final Context mContext;
    private final List<GoodsBean> datas;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    private final CheckBox checkboxDeleteAll;

    public ShoppingCartAdapter(Context mContext, List<GoodsBean> list, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox checkboxDeleteAll) {
        this.mContext = mContext;
        this.datas = list;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.checkboxDeleteAll = checkboxDeleteAll;
        showTotalPrice();
    }

    public void showTotalPrice() {
        //显示总价格
        tvShopcartTotal.setText("合计:" + getTotalPrice());
    }

    /**
     * 返回总价格
     *
     * @return
     */
    private double getTotalPrice() {
        double totalPrice = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isChecked()) {
                    totalPrice += Double.parseDouble(goodsBean.getCover_price()) * goodsBean.getNumber();
                }
            }
        }
        return totalPrice;
    }

    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHoler(View.inflate(mContext, R.layout.item_shop_cart, null));
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHoler holder, int position) {
        //1.先得到数据
        final GoodsBean goodsBean = new GoodsBean();
        //2.绑定数据
        //图片
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(holder.ivGov);
        //设置名称
        holder.tvDescGov.setText(goodsBean.getName());
        //设置价格
        holder.tvPriceGov.setText("￥" + goodsBean.getCover_price());

        //设置数量
        holder.addSubView.setValue(goodsBean.getNumber());
        holder.addSubView.setMinValue(1);
        //设置库存-来自服务器
        holder.addSubView.setMaxValue(100);

        holder.addSubView.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
            @Override
            public void onNumberChanger(int value) {
                //1.回调数量
                goodsBean.setNumber(value);

                CartStorage.getInstance(mContext).updateData(goodsBean);

                showTotalPrice();

            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isChecked()) {
                    //只要有一个不勾选
                    checkboxAll.setChecked(false);
                    checkboxDeleteAll.setChecked(false);
                } else {
                    //勾选
                    number++;
                }
            }

            if (datas.size() == number) {
                checkboxAll.setChecked(true);
                checkboxDeleteAll.setChecked(true);
            }
        } else {
            //没有数据
            checkboxAll.setChecked(false);
            checkboxDeleteAll.setChecked(false);
        }
    }

    public void checkAll_none(boolean isChecked) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                //设置是否勾选状态
                goodsBean.setChecked(isChecked);
                checkboxAll.setChecked(isChecked);
                checkboxDeleteAll.setChecked(isChecked);

                //更新视图
                notifyItemChanged(i);
            }
        }
    }

    /**
     * 删除数据
     */
    public void deleteData() {
//        if (datas != null && datas.size() > 0) {
//            for (int i = 0; i < datas.size(); i++) {
//                GoodsBean goodsBean = datas.get(i);
//                if (goodsBean.isChecked()) {
//                    //1.内存中删除
//                    datas.remove(goodsBean);
//                    //2.本地也好保存
//                    CartStorage.getInstance(mContext).deleteData(goodsBean);
//                    //刷新适配器
//                    notifyDataSetChanged();
//                    i--;
//                }
//            }
//        }
        if(datas!=null&&datas.size()>0) {
            for (Iterator iterator = datas.iterator(); iterator.hasNext();){
                GoodsBean goodsBean = (GoodsBean) iterator.next();
                if(goodsBean.isChecked()) {
                    int position  = datas.indexOf(goodsBean);
                    //从内存中移除
                    iterator.remove();
                    //本地也要同步
                    CartStorage.getInstance(mContext).deleteData(goodsBean);
                    //刷新页面
                    notifyDataSetChanged();
                }
            }
        }
    }

    public class MyViewHoler extends RecyclerView.ViewHolder {
        @InjectView(R.id.cb_gov)
        CheckBox cbGov;
        @InjectView(R.id.iv_gov)
        ImageView ivGov;
        @InjectView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @InjectView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @InjectView(R.id.addSubView)
        AddSubView addSubView;

        public MyViewHoler(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClickListener(v, getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        public void onItemClickListener(View view, int position);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.itemClickListener = l;
    }
}
