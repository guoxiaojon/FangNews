package com.example.jon.fangnews.ui.gank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.model.bean.GankItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jon on 2016/12/15.
 */

public class GankOtherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int mType;
    private Context mContext;
    private List<GankItemBean> mList;
    private OnItemClickListener mOnItemClickListener;

    private static final int TYPE_BOTTOM = 0x1;
    private static final int TYPE_DATA = 0x2;

    private int mLoadingFlag = 0;

    public GankOtherAdapter(int type, Context context, List<GankItemBean> list, OnItemClickListener onItemClickListener) {
        this.mType = type;
        this.mContext = context;
        this.mList = list;
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public int getItemViewType(int position) {
        if(position >= mList.size()){
            return TYPE_BOTTOM;
        }else {
            return TYPE_DATA;
        }
    }

    public void removeLoading(){
        mLoadingFlag = -1;
        notifyItemChanged(mList.size());


    }

    public void resetLoading(){
        mLoadingFlag = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_DATA){
            return new ItemView(LayoutInflater.from(mContext).inflate(R.layout.fragment_gank_other_item,parent,false));
        }else {
            return new BottomView(LayoutInflater.from(mContext).inflate(R.layout.item_loadmore_progress,parent,false));

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof BottomView)
            return;
        final ItemView itemView = (ItemView)holder;
        int picId = R.mipmap.ic_android;
        switch (mType){
            case  Constants.TYPE_ANDROID:
               picId = R.mipmap.android;
               break;
            case Constants.TYPE_VIDEO:
               picId = R.mipmap.qiqiu;
               break;
            case Constants.TYPE_RECOMM:
                picId = R.mipmap.recomm;
                break;
            default:
                break;
        }

        Glide.with(mContext).load(picId).into(itemView.mPic);
        itemView.mTitle.setText(mList.get(position).getDesc());
        itemView.mAuthor.setText(mList.get(position).getWho());
        itemView.mTime.setText(mList.get(position).getPublishedAt().substring(0,10));
        itemView.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(mList.get(position),itemView.mShareView);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mList.size() == 0)
            return 0;
        return mList.size()+1+mLoadingFlag;
    }

    public interface OnItemClickListener{
        void onClick(GankItemBean bean,View shareView);
    }

    class ItemView extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_gank_other_item_pic)
        ImageView mPic;
        @BindView(R.id.tv_gank_other_item_title)
        TextView mTitle;
        @BindView(R.id.tv_gank_other_item_author)
        TextView mAuthor;
        @BindView(R.id.tv_gank_other_item_time)
        TextView mTime;
        @BindView(R.id.ll_gank_other_shareview)
        View mShareView;

        View mView;

        public ItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mView = itemView;
        }
    }

    class BottomView extends RecyclerView.ViewHolder{

        public BottomView(View itemView) {
            super(itemView);
        }
    }
}
