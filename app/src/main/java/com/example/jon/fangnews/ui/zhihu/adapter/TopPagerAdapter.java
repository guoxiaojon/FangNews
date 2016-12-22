package com.example.jon.fangnews.ui.zhihu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.model.bean.DailyListBean;
import com.example.jon.fangnews.ui.zhihu.activity.ZhiHuDetailActivity;

import java.util.List;

/**
 * Created by jon on 2016/12/8.
 */

public class TopPagerAdapter extends PagerAdapter {
    private List<DailyListBean.TopStoryBean> mList;

    private Context mContext;

    public TopPagerAdapter(List<DailyListBean.TopStoryBean> list, Context context){
        this.mContext = context;
        this.mList = list;

    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_top_pager,container,false);
        ImageView iv = (ImageView)view.findViewById(R.id.iv_top_image);
        TextView tv = (TextView)view.findViewById(R.id.tv_top_title);
        ImageLoader.load(mContext,mList.get(position).getImage(),iv);
        tv.setText(mList.get(position).getTitle());
        final int id = mList.get(position).getId();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ZhiHuDetailActivity.class);
                intent.putExtra("id",id);
                mContext.startActivity(intent);

            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
