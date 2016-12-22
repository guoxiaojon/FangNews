package com.example.jon.fangnews.ui.zhihu.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.base.BaseFragment;
import com.example.jon.fangnews.model.bean.DailyBeforeListBean;
import com.example.jon.fangnews.model.bean.DailyListBean;
import com.example.jon.fangnews.presenter.DailyPresenter;
import com.example.jon.fangnews.presenter.contract.DailyContract;
import com.example.jon.fangnews.ui.zhihu.activity.CalenderActivity;
import com.example.jon.fangnews.ui.zhihu.activity.ZhiHuDetailActivity;
import com.example.jon.fangnews.ui.zhihu.adapter.DailyAdapter;
import com.example.jon.fangnews.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Created by jon on 2016/12/5.
 */

public class DailyFragment extends BaseFragment <DailyPresenter> implements DailyContract.View {
    @BindView(R.id.fab_calender)
    FloatingActionButton mFalCalender;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.rv_daily_list)
    RecyclerView mRVDailyList;

    private DailyAdapter mAdapter;
    private List<DailyListBean.StoryBean> mList = new ArrayList<>();
    private String mCurrentDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("data","DailyFragemnt  onCreate");
    }

    @Override
    protected void initInject() {
        getDaggerFragmentComponent().inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initEventAndData() {

        mAdapter = new DailyAdapter(getActivity());
        mAdapter.setOnItemClickLisenter(new DailyAdapter.OnItemClickLisenter() {
            @Override
            public void onItemClick(int position, View shareview) {
                mPresenter.insertReadedToDB(mList.get(position).getId());
                if(mAdapter.getIsBefore()){
                    mAdapter.notifyItemChanged(position+1);
                }else {
                    mAdapter.notifyItemChanged(position+2);
                }
                Intent intent = new Intent(mContext, ZhiHuDetailActivity.class);
                intent.putExtra("id",mList.get(position).getId());
                intent.putExtra("isTransparent",true);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity,shareview,"shareView");
                    Log.d("data","转场动画");
                    mContext.startActivity(intent,options.toBundle());
                }else {
                    mContext.startActivity(intent);
                }



            }
        });
        mRVDailyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVDailyList.setAdapter(mAdapter);
        mPresenter.getDailyData();




    }

    @Override
    public void showContent(DailyListBean dailyListBean) {
        mCurrentDate = DateUtils.getTomorrowDate();
        mProgressBar.setVisibility(GONE);
        mList = dailyListBean.getStories();
        mAdapter.addDailyData(dailyListBean);
        mPresenter.stopInterval();
        mPresenter.startInterval();

    }

    @Override
    public void showMoreContent(String date,DailyBeforeListBean dailyBeforeListBean) {
        mCurrentDate = date;
        mProgressBar.setVisibility(GONE);
        mList = dailyBeforeListBean.getStories();
        mAdapter.addBeforeData(dailyBeforeListBean);
        mPresenter.stopInterval();


    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void doInterval(int currentCount) {
        mAdapter.changeTopPage(currentCount);

    }

    @Override
    public void showError(String msg) {
        mProgressBar.setVisibility(GONE);
        Log.d("data","出错");
        Snackbar.make(mRVDailyList,msg,Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.fab_calender)
    public void toChooseDate(){
        Intent intent = new Intent(getActivity(), CalenderActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity,mFalCalender,"calendarshareView");
            Log.d("data","转场动画");
            mContext.startActivity(intent,options.toBundle());
        }else {
            mContext.startActivity(intent);
        }





    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("data","Daily  onAttah");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("data","Daily  onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("data","Daily  onViewCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("data","Daily OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("data","Daily  onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("data","Daily  onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("data","Daily  onDEstoryView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("data","Daily  onDestory");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("data","Daily  onDetach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("data","Daily  onStart");
    }
}
