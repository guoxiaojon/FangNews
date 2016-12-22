package com.example.jon.fangnews.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.base.BaseFragment;
import com.example.jon.fangnews.model.bean.RealmLikeBean;
import com.example.jon.fangnews.presenter.MainLikePresenter;
import com.example.jon.fangnews.presenter.contract.MainLikeContract;
import com.example.jon.fangnews.ui.main.adapter.MainLikeAdapter;
import com.example.jon.fangnews.widget.DefaultItemTouchHelperCalllback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jon on 2016/12/6.
 */

public class LikeFragment extends BaseFragment<MainLikePresenter> implements MainLikeContract.View {
    @BindView(R.id.rv_main_like)
    RecyclerView mRVLikeList;

    List<RealmLikeBean> mList;
    MainLikeAdapter mAdapter;
    DefaultItemTouchHelperCalllback mCalllback;
    ItemTouchHelper mItemTouchHelper;


    @Override
    protected void initInject() {
        getDaggerFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_like;
    }

    @Override
    protected void initEventAndData() {
        mList = new ArrayList<>();
        mAdapter = new MainLikeAdapter(mList,mActivity);
        mRVLikeList.setAdapter(mAdapter);
        mRVLikeList.setLayoutManager(new LinearLayoutManager(mContext));
        mCalllback = new DefaultItemTouchHelperCalllback(new DefaultItemTouchHelperCalllback.OnItemTouchListener() {
            @Override
            public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                boolean isPuls = false;
                if(viewHolder.getAdapterPosition() < target.getAdapterPosition()){
                    isPuls = true;
                }
                //数据库操作
                mPresenter.changeLikedTime(mList.get(viewHolder.getAdapterPosition()).getId(),mList.get(target.getAdapterPosition()).getTime(),isPuls);

                Collections.swap(mList,viewHolder.getAdapterPosition(),target.getAdapterPosition());
                mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mPresenter.deleteLikedBean(mList.get(viewHolder.getAdapterPosition()).getId());
                mList.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        mItemTouchHelper = new ItemTouchHelper(mCalllback);
        mItemTouchHelper.attachToRecyclerView(mRVLikeList);

        mPresenter.getLikedBeanList();

    }

    @Override
    public void showContent(List<RealmLikeBean> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String msg) {

    }
}
