package com.example.jon.fangnews.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.model.bean.ZhiHuHotListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jon on 2016/12/14.
 */

public class HotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ZhiHuHotListBean.RecentBean> mList;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public HotAdapter(List<ZhiHuHotListBean.RecentBean> list, OnItemClickListener onItemClickListener, Context context) {
        this.mList = list;
        this.mOnItemClickListener = onItemClickListener;
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemView(LayoutInflater.from(mContext).inflate(R.layout.fragment_zhihu_hot_item,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemView itemView = (ItemView)holder;
        ImageLoader.load(mContext,mList.get(position).getThumbnail(),itemView.mPic);
        itemView.mName.setText(mList.get(position).getTitle());
        if(mList.get(position).isReaded()){
            itemView.mName.setTextColor(ActivityCompat.getColor(mContext,R.color.news_read));
        }
        itemView.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(mList.get(position).getNew_id(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setReaded(int position){
        mList.get(position).setReaded(true);
    }

    class ItemView extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_zhihu_hot_pic)
        ImageView mPic;
        @BindView(R.id.tv_zhihu_hot_name)
        TextView mName;

        View mView;

        public ItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mView = itemView;
        }
    }

    public interface OnItemClickListener{
        void onClick(int id,int position);
    }



}
