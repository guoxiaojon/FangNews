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
import com.example.jon.fangnews.model.bean.ZhiHuThemeContentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by jon on 2016/12/13.
 */

public class ThemeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ZhiHuThemeContentBean.StoryBean> mStories;
    private ThemeDetailAdapter.OnItemClickListener mOnItemClickLisenter;
    public ThemeDetailAdapter(Context context,List<ZhiHuThemeContentBean.StoryBean> stories ,ThemeDetailAdapter.OnItemClickListener  onItemClickLisenter){
        this.mContext = context;
        this.mOnItemClickLisenter = onItemClickLisenter;
        mStories = stories;
    }

    public void setReaded(int position){
        mStories.get(position).setReaded(true);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemView(LayoutInflater.from(mContext).inflate(R.layout.activity_zhihu_theme_detail_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemView itemView = (ItemView)holder;
        itemView.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLisenter.OnClick(mStories.get(position).getId(),position);
            }
        });
        mStories.get(position);
        mStories.get(position).getImages();
        if( mStories.get(position).getImages() != null){
            ImageLoader.load(mContext , mStories.get(position).getImages().get(0),itemView.mPic);
        }


        itemView.mName.setText(mStories.get(position).getTitle());
        if(mStories.get(position).isReaded()){
            itemView.mName.setTextColor(ActivityCompat.getColor(mContext,R.color.news_read));
        }


    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    class ItemView extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_zhihu_theme_detail_item_pic)
        ImageView mPic;
        @BindView(R.id.tv_zhihu_theme_detail_item_name)
        TextView mName;

        View mView;

        public ItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mView = itemView;
        }
    }

    public interface OnItemClickListener{
        void OnClick(int id,int position);
    }
}
