package com.example.jon.fangnews.ui.main.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.model.bean.RealmLikeBean;
import com.example.jon.fangnews.ui.gank.activity.GankGirlDetailActivity;
import com.example.jon.fangnews.ui.wechat.activity.WGDetailActivity;
import com.example.jon.fangnews.ui.zhihu.activity.ZhiHuDetailActivity;
import com.example.jon.fangnews.widget.DefaultItemTouchHelperCalllback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jon on 2016/12/16.
 */

public class MainLikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RealmLikeBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;


    private static final int TYPE_PIC = 0x1;
    private static final int TYPE_TEXT = 0x2;

    public MainLikeAdapter(List<RealmLikeBean> list, Context context) {
        this.mList = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_PIC){
            return new PicItem(mInflater.inflate(R.layout.fragment_main_like__item_pic,parent,false));
        }else{
            return new TextItem(mInflater.inflate(R.layout.fragment_main_like_item_text,parent,false));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).getType() == Constants.TYPE_GIRL){
            return TYPE_PIC;
        }else {
            return TYPE_TEXT;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof PicItem){
            ImageLoader.load(mContext,mList.get(position).getImage(),((PicItem)holder).mPic);
            ((PicItem)holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoGirlDetail(mList.get(position),((PicItem)holder).mView);
                }
            });
        }else {
            final TextItem textItem = (TextItem)holder;
            if(mList.get(position).getType() == Constants.TYPE_ZHIHU){
                //知乎
                textItem.mTitle.setText(mList.get(position).getTitle());
                textItem.mType.setText(getTypeString(mList.get(position).getType()));
                ImageLoader.load(mContext,mList.get(position).getImage(),textItem.mPic);
                textItem.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gotoZhiHUDetail(mList.get(position));
                    }
                });

            }else if(mList.get(position).getType()== Constants.TYPE_WECHAT){
                //微信
                textItem.mTitle.setText(mList.get(position).getTitle());
                textItem.mType.setText(getTypeString(mList.get(position).getType()));
                ImageLoader.load(mContext,mList.get(position).getId(),textItem.mPic);
                textItem.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gotoWAndGDetail(mList.get(position),textItem.mView);
                    }
                });


            }else{
                //干货
                textItem.mTitle.setText(mList.get(position).getTitle());
                textItem.mType.setText(getTypeString(mList.get(position).getType()));
                Glide.with(mContext).load(getGankPicId(mList.get(position).getType())).into(textItem.mPic);
                textItem.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gotoWAndGDetail(mList.get(position),textItem.mView);
                    }
                });

            }
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private int getGankPicId(int gankType){
        if(gankType == Constants.TYPE_ANDROID){
            return R.mipmap.android;
        }else if(gankType == Constants.TYPE_VIDEO){
            return R.mipmap.qiqiu;

        }else {
            return R.mipmap.recomm;
        }
    }

    private String getTypeString(int type){
        switch (type){
            case Constants.TYPE_ZHIHU:
                return "知乎";
            case Constants.TYPE_WECHAT:
                return "微信";
            default:
                return "干货集中营";
        }
    }

    class TextItem extends RecyclerView.ViewHolder implements DefaultItemTouchHelperCalllback.ItemTouchViewHolder{
        @BindView(R.id.iv_main_like_item_text_pic)
        ImageView mPic;
        @BindView(R.id.tv_main_like_item_text_title)
        TextView mTitle;
        @BindView(R.id.tv_main_like_item_text_type)
        TextView mType;

        View mView;

        public TextItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mView =itemView;
        }

        @Override
        public void onItemSelected() {

            mView.setBackgroundColor(ActivityCompat.getColor(mContext,R.color.item_selected));

        }

        @Override
        public void onItemClear() {

            mView.setBackgroundColor(ActivityCompat.getColor(mContext,R.color.item_clear));


        }
    }

    class PicItem extends RecyclerView.ViewHolder implements DefaultItemTouchHelperCalllback.ItemTouchViewHolder{
        @BindView(R.id.iv_main_like_item_pic)
        ImageView mPic;

        View mView;


        public PicItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mView =itemView;
        }

        @Override
        public void onItemSelected() {
            mView.setBackgroundColor(ActivityCompat.getColor(mContext,R.color.item_selected));
        }

        @Override
        public void onItemClear() {

            mView.setBackgroundColor(ActivityCompat.getColor(mContext,R.color.item_clear));

        }
    }

    private void gotoZhiHUDetail(RealmLikeBean bean){
        Intent intent = new Intent(mContext, ZhiHuDetailActivity.class);
        intent.putExtra("id",Integer.valueOf(bean.getId()));
        mContext.startActivity(intent);

    }
    private void gotoWAndGDetail(RealmLikeBean bean,View shareView){

            Intent intent = new Intent(mContext, WGDetailActivity.class);
            intent.putExtra("title",bean.getTitle());
            intent.putExtra("type", bean.getType());
            intent.putExtra("url",bean.getImage());
            intent.putExtra("id",bean.getId());
            ActivityOptions options = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                options = ActivityOptions.makeSceneTransitionAnimation((Activity)mContext,shareView,"shareView");
                mContext.startActivity(intent,options.toBundle());
            }else {
                mContext.startActivity(intent);
            }


    }
    private void gotoGirlDetail(RealmLikeBean bean,View shareView){
        Intent intent = new Intent(mContext, GankGirlDetailActivity.class);
        intent.putExtra("id",bean.getId());
        intent.putExtra("url",bean.getImage());
        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation((Activity)mContext,shareView,"shareView");
            mContext.startActivity(intent,options.toBundle());
        }else {
            mContext.startActivity(intent);
        }


    }
}
