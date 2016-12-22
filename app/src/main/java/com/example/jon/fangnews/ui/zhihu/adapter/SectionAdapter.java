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
import com.example.jon.fangnews.model.bean.ZhiHuSectionListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jon on 2016/12/13.
 */

public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ZhiHuSectionListBean.DataBean> mList;
    private OnItemClickListener mOnItemClickListener;

    public SectionAdapter(Context context, List<ZhiHuSectionListBean.DataBean> list, SectionAdapter.OnItemClickListener onItemClickListener){
        this.mContext = context;
        this.mList = list;
        this.mOnItemClickListener = onItemClickListener;
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemView(LayoutInflater.from(mContext).inflate(R.layout.fragment_zhihu_section_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemView itemView = (ItemView)holder;
        itemView.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(mList.get(position).getId());
            }
        });
        ImageLoader.load(mContext,mList.get(position).getThumbnail(),itemView.mPic);
        itemView.mName.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener{
        void onClick(int id);
    }

    class ItemView extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_zhihu_section_list_pic)
        ImageView mPic;
        @BindView(R.id.tv_zhihu_section_list_name)
        TextView mName;

        View mView;
        public ItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mView = itemView;
        }
    }

}
