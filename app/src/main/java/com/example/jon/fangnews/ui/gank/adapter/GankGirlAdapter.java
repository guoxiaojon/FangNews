package com.example.jon.fangnews.ui.gank.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.model.bean.GankItemBean;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jon on 2016/12/16.
 */

public class GankGirlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GankItemBean> mList;
    private  OnItemClickListener mOnItemClickListener;
    private Context mContext;

    private int[] staggHeigh = {20,35,16,26,30};

    public GankGirlAdapter(List<GankItemBean> list, OnItemClickListener onItemClickListener, Context context) {
        this.mList = list;
        this.mOnItemClickListener = onItemClickListener;
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemView(LayoutInflater.from(mContext).inflate(R.layout.fragment_gank_girl_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ItemView)holder).mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(mList.get(position),((ItemView)holder).mPic);
            }
        });
        if(mList.get(position).getHeigh()<=0){
            Glide.with(mContext).load(mList.get(position).getUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    int with = resource.getWidth();
                    int heigh = resource.getHeight();
                    int realHeigh = (int) (App.SCREEN_WIDTH/2 * (float)heigh/(float)with);
                    mList.get(position).setHeigh(realHeigh);
                    ((ItemView)holder).mPic.getLayoutParams().height = realHeigh;
                    ((ItemView)holder).mPic.setImageBitmap(resource);
                }
            });
        }else {
            ((ItemView)holder).mPic.getLayoutParams().height = mList.get(position).getHeigh();
            ImageLoader.load(mContext,mList.get(position).getUrl(),((ItemView)holder).mPic);
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public int getItemViewType(int position) {

        return Math.round((float) App.SCREEN_WIDTH / (float) mList.get(position).getHeigh() * 10f);

    }

    public interface OnItemClickListener{
        void onClick(GankItemBean bean, View shareView);
    }

    class ItemView extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_gank_girl_item)
        ImageView mPic;
        View mView;

        public ItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mView = itemView;
            Logger.e("itemView"+itemView);
        }
    }
}
