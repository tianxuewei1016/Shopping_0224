package com.atguigu.shopping_0224.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.atguigu.shopping_0224.R;
import com.atguigu.shopping_0224.home.bean.HomeBean;
import com.atguigu.shopping_0224.utils.Constants;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：田学伟 on 2017/6/4 13:07
 * QQ：93226539
 * 作用：分类型的RecyclerView
 */

public class HomeAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final HomeBean.ResultEntity result;
/**
 * 六种类型
 */
    /**
     * 横幅广告-要从0开
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    /**
     * 用他加载布局
     */
    private final LayoutInflater inflater;
    /**
     * 当前类型
     */
    public int currentType = BANNER;

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            currentType = BANNER;
        } else if (position == CHANNEL) {
            currentType = CHANNEL;
        } else if (position == ACT) {
            currentType = ACT;
        } else if (position == SECKILL) {
            currentType = SECKILL;
        } else if (position == RECOMMEND) {
            currentType = RECOMMEND;
        } else if (position == HOT) {
            currentType = HOT;
        }
        return currentType;
    }

    /**
     * 用他加载布局
     */
    public HomeAdapter(Context mContext, HomeBean.ResultEntity result) {
        this.mContext = mContext;
        this.result = result;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, inflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext, inflater.inflate(R.layout.channel_item, null));
        } else if (viewType == ACT) {
        } else if (viewType == SECKILL) {
        } else if (viewType == RECOMMEND) {
        } else if (viewType == HOT) {
        }
        return null;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder viewHolder = (BannerViewHolder) holder;
            //绑定数据
            viewHolder.setData(result.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder viewHolder = (ChannelViewHolder) holder;
            viewHolder.setData(result.getChannel_info());
        }
    }

    @Override
    public int getItemCount() {
        //所有的类型写完后改成6
        return 2;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(List<HomeBean.ResultEntity.BannerInfoEntity> banner_info) {
            //1.得到数据
            //2.设置Banner的数据
            List<String> images = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                images.add(Constants.BASE_URL_IMAGE + banner_info.get(i).getImage());
            }
            //banner的简单实用
            banner.setImages(images)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
                            Glide.with(context)
                                    .load(path)
                                    .crossFade()
                                    .into(imageView);
                        }
                    }).start();
            //设置样式
            banner.setBannerAnimation(BackgroundToForegroundTransformer.class);
            //3.设置Banner的点击事件
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    int realPosition = position - 1;
                    Toast.makeText(mContext, "realPosition==" + realPosition, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @InjectView(R.id.gv_channel)
        GridView gvChannel;
        ChannelAdapter channelAdapter;

        public ChannelViewHolder(Context mContext, View inflate) {
            super(inflate);
            ButterKnife.inject(this, inflate);
            this.mContext = mContext;
        }

        public void setData(List<HomeBean.ResultEntity.ChannelInfoEntity > channel_info) {
            //设置GridView的适配器
            channelAdapter = new ChannelAdapter(mContext, channel_info);
            gvChannel.setAdapter(channelAdapter);
            //设置item的点击事件
            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
