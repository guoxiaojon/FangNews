package com.example.jon.fangnews.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.model.bean.DailyBeforeListBean;
import com.example.jon.fangnews.model.bean.DailyListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jon on 2016/12/8.
 */

public class DailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<DailyListBean.StoryBean> mStories = new ArrayList<>();
    private List<DailyListBean.TopStoryBean> mTopStories = new ArrayList<>();
    private TopPagerAdapter mAdapter;
    private ViewPager mTopViewPager;

    private boolean isBefore = false;
    private String mCurrentDate = "今日新闻";

    private static final int TYPE_TOP = 0x1;
    private static final int TYPE_DATE = 0x2;
    private static final int TYPE_CONTENT = 0x3;

    private LayoutInflater mInflater;

    private OnItemClickLisenter lisenter;
    public DailyAdapter(Context context){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void addDailyData(DailyListBean dailyList){
        mStories = dailyList.getStories();
        mTopStories = dailyList.getTop_stories();
        isBefore = false;
        mCurrentDate = "今日新闻";
        Log.d("data","走到这");
        notifyDataSetChanged();

    }

    public boolean getIsBefore(){
        return isBefore;
    }
    public void addBeforeData(DailyBeforeListBean dailyBeforeList){
        mStories = dailyBeforeList.getStories();
        mCurrentDate = dailyBeforeList.getDate();
        isBefore = true;
        notifyDataSetChanged();

    }

    @Override
    public int getItemViewType(int position) {
        if(isBefore){
            if(position == 0) {
                return TYPE_DATE;
            }
            else {
                return TYPE_CONTENT;
            }

        }else {
            if(position == 0){
                return TYPE_TOP;
            }else if(position == 1){
                return TYPE_DATE;
            }else {
                return TYPE_CONTENT;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TOP:
                return new TopViewHolder(mInflater.inflate(R.layout.item_top,parent,false));
            case TYPE_DATE:
                return new DateViewHolder(mInflater.inflate(R.layout.item_date,parent,false));
            case TYPE_CONTENT:
                return new ContentViewHolder(mInflater.inflate(R.layout.item_daily,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)){
                case TYPE_DATE:
                    ((DateViewHolder)holder).textView.setText(mCurrentDate);
                    return;
                case TYPE_TOP:
                    mAdapter = new TopPagerAdapter(mTopStories,mContext);
                    ((TopViewHolder)holder).viewPager.setAdapter(mAdapter);
                    mTopViewPager = ((TopViewHolder)holder).viewPager;
                    return;
                case TYPE_CONTENT:
                    final int contentPosition;
                    if(isBefore){
                        contentPosition = position-1;
                    }else {
                        contentPosition = position-2;
                    }

                    DailyListBean.StoryBean story = mStories.get(contentPosition);
                    ImageLoader.load(mContext
                            ,story.getImages().get(0)
                            ,((ContentViewHolder)holder).imageView);
                    ((ContentViewHolder)holder).textView.setText(story.getTitle());
                    if(story.getReaded()){
                        ((ContentViewHolder)holder).textView.setTextColor(ContextCompat.getColor(mContext,R.color.news_read));
                    }else {
                        ((ContentViewHolder)holder).textView.setTextColor(ContextCompat.getColor(mContext,R.color.news_unread));
                    }
                    ((ContentViewHolder)holder).view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lisenter.onItemClick(contentPosition,((ContentViewHolder)holder).imageView);
                        }
                    });

                    return;
                default:
                    return;
            }


    }

    public void changeTopPage(int currentCount){
        if(!isBefore && mTopViewPager != null){
            mTopViewPager.setCurrentItem(currentCount);
        }

    }

    @Override
    public int getItemCount() {
        if(isBefore){
            return mStories.size()+1;
        }else {
            return mStories.size()+2;
        }

    }

    public static class TopViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.vp_top)
        ViewPager viewPager;
        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public static class DateViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_date)
        TextView textView;
        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_daily_item_image)
        ImageView imageView;
        @BindView(R.id.tv_daily_item_title)
        TextView textView;
        View view;
        public ContentViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this,itemView);
        }
    }
    public void setOnItemClickLisenter(OnItemClickLisenter lisenter){
        this.lisenter = lisenter;
    }

    public interface OnItemClickLisenter{
        void onItemClick(int position,View view);
    }
}
