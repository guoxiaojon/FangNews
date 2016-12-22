package com.example.jon.fangnews.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.base.BaseActivity;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.di.component.DaggerActivityComponent;
import com.example.jon.fangnews.di.module.ActivityModule;
import com.example.jon.fangnews.model.bean.WelcomeBean;
import com.example.jon.fangnews.presenter.WelcomePresenter;
import com.example.jon.fangnews.presenter.contract.WelcomeContract;
import com.example.jon.fangnews.ui.main.activity.MainActivity;

import butterknife.BindView;

/**
 * Created by jon on 2016/12/5.
 */

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View {

    @BindView(R.id.iv_welcome_bg)
    ImageView mIVWelcomeBg;
    @BindView(R.id.tv_welcome_author)
    TextView mTVWelcomeAuthor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initEventAndData() {
        mPresenter.getWelcomeData();

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void showContent(WelcomeBean welcomeBean) {
        ImageLoader.load(this,welcomeBean.getImg(),mIVWelcomeBg);
        mIVWelcomeBg.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();

        mTVWelcomeAuthor.setText(welcomeBean.getText());
    }

    @Override
    public void jumpToMainActivity() {
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    @Override
    public void showError(String msg) {
        //Snackbar.make(getWindow().getDecorView(),msg,Snackbar.LENGTH_LONG).show();
    }



    @Override
    public void useNightMode(boolean isNight) {}
    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(App.getAppComponent())
                .build().inject(this);

    }
}
