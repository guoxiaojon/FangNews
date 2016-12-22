package com.example.jon.fangnews.ui.zhihu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.model.bean.ZhiHuThemeListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jon on 2016/12/12.
 */

public class ThemeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ZhiHuThemeListBean.Other> mThemeList;
    private OnItemClick mOnItemClick;

    public ThemeListAdapter(List<ZhiHuThemeListBean.Other> list,Context context,OnItemClick onItemClick){
        this.mThemeList = list;
        this.mContext = context;
        this.mOnItemClick =onItemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ThemeListDetail(LayoutInflater.from(mContext).inflate(R.layout.fragment_zhihu_theme_list_detail,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageLoader.load(mContext,mThemeList.get(position).getThumbnail(),((ThemeListDetail)holder).mIVBackground);
        ((ThemeListDetail)holder).mTVName.setText(mThemeList.get(position).getName());
        ((ThemeListDetail)holder).mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClick.onClick(mThemeList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mThemeList.size();
    }


    class ThemeListDetail extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_zhihu_theme_list_detail_bg)
        ImageView mIVBackground;
        @BindView(R.id.tv_zhihu_theme_list_detail_name)
        TextView mTVName;

        View mView;

        public ThemeListDetail(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mView = itemView;
        }
    }

    public interface OnItemClick{
        void onClick(int id);
    }
}
