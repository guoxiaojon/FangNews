package com.example.jon.fangnews.ui.wechat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.model.bean.WeChatBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jon on 2016/12/14.
 */

public class WeChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<WeChatBean> mList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mInflater;

    private static final int TYPE_DATA = 0x1;
    private static final int TYPE_BOTTOM = 0x2;

    private int loadingFlag = 0;

    public WeChatAdapter(List<WeChatBean> list, Context context, OnItemClickListener onItemClickListener) {
        this.mList = list;
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
        this.mInflater =LayoutInflater.from(context);
    }
    public void removeLoading(){
        loadingFlag = -1;
        notifyItemChanged(mList.size());
    }
    public void resetLoading(){
        loadingFlag = 0;

    }


    @Override
    public int getItemViewType(int position) {
        if(position >= mList.size()){
            return TYPE_BOTTOM;
        }else {
            return TYPE_DATA;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_DATA){
            return new DataView(mInflater.inflate(R.layout.fragment_wechat_item,parent,false));
        }else {
            return new BottomView(mInflater.inflate(R.layout.item_loadmore_progress,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof DataView){
            final DataView dataView = (DataView)holder;
            ImageLoader.load(mContext,mList.get(position).getPicUrl(),dataView.mPic);
            dataView.mTitle.setText(mList.get(position).getTitle());
            dataView.mDescription.setText(mList.get(position).getDescription());
            dataView.mTime.setText(mList.get(position).getCtime().substring(5));
            dataView.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(mList.get(position),dataView.mLLShareView);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(mList.size() == 0){
            return 0;
        }
        return mList.size()+ 1 + loadingFlag;
    }

    public interface OnItemClickListener{
        void onClick(WeChatBean weChatBean,View shareView);
    }

    class DataView extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_wechat_pic)
        ImageView mPic;
        @BindView(R.id.tv_wechat_title)
        TextView mTitle;
        @BindView(R.id.tv_wechat_description)
        TextView mDescription;
        @BindView(R.id.tv_wechat_time)
        TextView mTime;
        @BindView(R.id.ll_share_view)
        LinearLayout mLLShareView;

        View mView;

        public DataView(View itemView) {
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
