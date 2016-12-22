package com.example.jon.fangnews.model.db;

import android.content.Context;

import com.example.jon.fangnews.model.bean.RealmLikeBean;
import com.example.jon.fangnews.model.bean.ZhiHuReadStateBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by jon on 2016/12/8.
 */

public class RealmHelper {

    private static final String DB_NAME = "zhihuReadState.realm";

    private Realm mRealm;

    public RealmHelper(Context context){
        mRealm = Realm.getInstance(new RealmConfiguration.Builder(context)
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build());

    }
    public void insertReadedId(int id){
        ZhiHuReadStateBean readStateBean = new ZhiHuReadStateBean();
        readStateBean.setId(id);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(readStateBean);
        mRealm.commitTransaction();
    }

    public boolean queryIsReaded(int id){
        RealmResults<ZhiHuReadStateBean> results = mRealm.where(ZhiHuReadStateBean.class).findAll();
        for(ZhiHuReadStateBean bean: results){
            if(id == bean.getId())
                return true;
        }
        return false;
    }



    public boolean queryLikeBean(String id){
        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAll();
        for(RealmLikeBean bean : results){
            if(bean.getId().equals(id))
                return true;
        }

        return false;
    }
    public void insertLikeBean(RealmLikeBean likeBean){
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(likeBean);
        mRealm.commitTransaction();
    }
    public void deteleLikeBean(String id){
        RealmLikeBean likeBean = mRealm.where(RealmLikeBean.class).equalTo("id",id).findFirst();
        mRealm.beginTransaction();
        likeBean.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * 过去收藏列表，时间戳排序
     * */
    public List<RealmLikeBean> getLikeList(){
        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAllSorted("time");
        return mRealm.copyFromRealm(results);
    }

    public void changeLikeTime(String id,long originTime,boolean isPlus){
        RealmLikeBean result = mRealm.where(RealmLikeBean.class).equalTo("id",id).findFirst();
        mRealm.beginTransaction();
        if(isPlus){
            result.setTime(++originTime);
        }else {
            result.setTime(--originTime);
        }
        mRealm.commitTransaction();

    }

}
