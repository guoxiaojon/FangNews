package com.example.jon.fangnews.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.model.bean.ZhiHuCommentBean;
import com.example.jon.fangnews.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;

/**
 * Created by jon on 2016/12/11.
 */

public class CommentContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ZhiHuCommentBean.CommentBean> mList;
    private static final int MAX_LINES = 4;

    private static final int STATE_NULL = 0;
    private static final int STATE_NONE = 1;
    private static final int STATE_EXPAND = 2;
    private static final int STATE_SHRINK = 3;


    public CommentContentAdapter(List<ZhiHuCommentBean.CommentBean> list, Context context){
        this.mContext = context;
        this.mList = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentView(LayoutInflater.from(mContext).inflate(R.layout.fragment_zhihu_comment_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ZhiHuCommentBean.CommentBean commentBean = mList.get(position);
        final ContentView view = (ContentView)holder;
        ImageLoader.load(mContext,commentBean.getAvatar(),view.ivAuator);
        view.tvName.setText(commentBean.getAuthor());
        view.tvContent.setText(commentBean.getContent());
        view.tvTime.setText(DateUtils.formatTime2String(commentBean.getTime()));
        view.tvLike.setText(String.valueOf(commentBean.getLikes()));

        view.tvReplyTo.setVisibility(GONE);
        view.tvExpand.setVisibility(GONE);


        if(commentBean.getReply_to() != null && commentBean.getReply_to().getId() != 0){
            final String replyedname = commentBean.getReply_to().getAuthor();
            String replyedcontent = commentBean.getReply_to().getContent();
            SpannableString ss = new SpannableString("@"+replyedname+": "+replyedcontent);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.comment_at)),0,replyedname.length()+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            view.tvReplyTo.setVisibility(View.VISIBLE);
            view.tvReplyTo.setText(ss);

            if(commentBean.getExpand() == STATE_NULL){
                view.tvExpand.setOnClickListener(new OnClickExpand(position,view.tvReplyTo));
                view.tvReplyTo.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("data","@"+replyedname+" : "+view.tvReplyTo.getLineCount() + " : "+commentBean.getExpand());

                        if(view.tvReplyTo.getLineCount() == 0 && view.tvReplyTo.getText() != ""){
                            view.tvReplyTo.post(this);
                            return;
                        }

                        if( view.tvReplyTo.getLineCount() > MAX_LINES){
                            view.tvReplyTo.setMaxLines(MAX_LINES);
                            view.tvExpand.setText("展开");
                            view.tvExpand.setVisibility(View.VISIBLE);
                            commentBean.setExpand(STATE_SHRINK);

                        }else {
                            commentBean.setExpand(STATE_NONE);

                        }

                    }
                });


            }else if(commentBean.getExpand() == STATE_NONE){

            }else if(commentBean.getExpand() == STATE_EXPAND){
                view.tvExpand.setText("收起");
                view.tvExpand.setVisibility(View.VISIBLE);
                view.tvReplyTo.setMaxLines(Integer.MAX_VALUE);
            }else if(commentBean.getExpand()==STATE_SHRINK){
                view.tvExpand.setMaxLines(MAX_LINES);
                view.tvExpand.setText("展开");
                view.tvExpand.setVisibility(View.VISIBLE);
            }


        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ContentView extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_zhihu_comment_avator)
        CircleImageView ivAuator;
        @BindView(R.id.tv_zhihu_comment_detail_name)
        TextView tvName;
        @BindView(R.id.tv_zhihu_comment_detail_content)
        TextView tvContent;
        @BindView(R.id.tv_zhihu_comment_detail_replyto)
        TextView tvReplyTo;
        @BindView(R.id.tv_zhihu_comment_detail_bottom_time)
        TextView tvTime;
        @BindView(R.id.tv_zhihu_comment_detail_bottom_expand)
        TextView tvExpand;
        @BindView(R.id.tv_zhihu_comment_detail_bottom_like)
        TextView tvLike;

        public ContentView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class OnClickExpand implements View.OnClickListener{
        private int mPosition;
        private TextView mReplyView;
        public OnClickExpand(int position,TextView replyView){
            this.mPosition = position;
            this.mReplyView = replyView;
        }

        @Override
        public void onClick(View view) {
            ZhiHuCommentBean.CommentBean commentBean = mList.get(mPosition);
            if(commentBean.getExpand() == STATE_EXPAND){
                ((TextView)view).setText("展开");
                mReplyView.setMaxLines(MAX_LINES);
                commentBean.setExpand(STATE_SHRINK);


            }else {
                ((TextView)view).setText("收起");
                mReplyView.setMaxLines(Integer.MAX_VALUE);
                commentBean.setExpand(STATE_EXPAND);

            }

        }
    }
}
