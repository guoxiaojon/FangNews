package com.example.jon.fangnews.ui.gank.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.base.SimpleActivity;
import com.example.jon.fangnews.di.component.DaggerActivityComponent;
import com.example.jon.fangnews.di.module.ActivityModule;
import com.example.jon.fangnews.model.bean.RealmLikeBean;
import com.example.jon.fangnews.model.db.RealmHelper;
import com.example.jon.fangnews.utils.ShareUtil;
import com.example.jon.fangnews.utils.SystemUtil;

import javax.inject.Inject;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by jon on 2016/12/16.
 */

public class GankGirlDetailActivity extends SimpleActivity {
    @BindView(R.id.iv_gank_girl_detail)
    ImageView mPhoto;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    PhotoViewAttacher mAttacher;
    Bitmap mBitmap;

    MenuItem mLikedItem;
    @Inject
    RealmHelper mRealmHelper;

    private String mId;
    private String mUrl;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_gank_girl;
    }

    @Override
    protected void initEventAndDate() {
        DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
        setToolBar(mToolbar,"妹纸");
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mUrl = intent.getStringExtra("url");
        Glide.with(mContext).load(mUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mBitmap = resource;
                if(mPhoto == null){
                    return;
                }

                mPhoto.setImageBitmap(mBitmap);
                mAttacher = new PhotoViewAttacher(mPhoto);
            }
        });

    }
    private void setLiked(boolean liked){
        if(liked){
            mLikedItem.setIcon(R.mipmap.ic_toolbar_like_p);
        }else {
            mLikedItem.setIcon(R.mipmap.ic_toolbar_like_n);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_gank_girl,menu);
        mLikedItem = menu.findItem(R.id.action_girl_like);
        setLiked(mRealmHelper.queryLikeBean(mId));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_girl_like:
                if(mRealmHelper.queryLikeBean(mId)){
                    setLiked(false);
                    mRealmHelper.deteleLikeBean(mId);
                }else {
                    setLiked(true);
                    RealmLikeBean likeBean  = new RealmLikeBean();
                    likeBean.setImage(mUrl);
                    likeBean.setTitle("妹纸~");
                    likeBean.setTime(System.currentTimeMillis());
                    likeBean.setId(mId);
                    likeBean.setType(Constants.TYPE_GIRL);
                    mRealmHelper.insertLikeBean(likeBean);
                }
                break;
            case R.id.action_girl_share:
                ShareUtil.shareImage(mContext, SystemUtil.saveBitmapToFile(mContext,mUrl,mBitmap,getWindow().getDecorView()),"分享妹纸图片~");
                break;
            case R.id.action_girl_save:
                SystemUtil.saveBitmapToFile(mContext,mUrl,mBitmap,getWindow().getDecorView());
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSupportBacKPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else{
            finish();
        }
    }
}
