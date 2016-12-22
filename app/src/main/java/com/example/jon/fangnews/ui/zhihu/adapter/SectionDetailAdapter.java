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
import com.example.jon.fangnews.model.bean.ZhiHuSectionContentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jon on 2016/12/13.
 */

public class SectionDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ZhiHuSectionContentBean.StoryBean> mList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public SectionDetailAdapter(List<ZhiHuSectionContentBean.StoryBean> list, Context context, OnItemClickListener onItemClickListener) {
        this.mList = list;
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemView(LayoutInflater.from(mContext).inflate(R.layout.activity_zhihu_section_detail_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemView itemView = (ItemView)holder;
        if( mList.get(position).getImages() != null && mList.get(position).getImages().size()>0){
            ImageLoader.load(mContext,mList.get(position).getImages().get(0),itemView.mPic);

        }
        itemView.mName.setText(mList.get(position).getTitle());
        itemView.mTime.setText(mList.get(position).getDisplay_date());
        if(mList.get(position).isReaded()){
            itemView.mName.setTextColor(ActivityCompat.getColor(mContext,R.color.news_read));
            itemView.mTime.setTextColor(ActivityCompat.getColor(mContext,R.color.news_read));
        }
        itemView.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mOnItemClickListener.onClick(mList.get(position).getId(),position);
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


    public interface OnItemClickListener{
        void onClick(int id,int position);
    }
    class ItemView extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_zhihu_section_detail_item_pic)
        ImageView mPic;
        @BindView(R.id.tv_zhihu_section_detail_item_name)
        TextView mName;
        @BindView(R.id.tv_zhihu_section_detail_item_time)
        TextView mTime;

        View mView;

        public ItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mView = itemView;

        }
    }
}
