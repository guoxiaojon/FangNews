package com.example.jon.fangnews.ui.main.fragment;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.base.SimpleFragment;
import com.example.jon.fangnews.component.ACache;
import com.example.jon.fangnews.component.RxBus;
import com.example.jon.fangnews.model.bean.NightEvent;
import com.example.jon.fangnews.utils.SharePreferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jon on 2016/12/6.
 */

public class SettingFragment extends SimpleFragment implements CheckBox.OnCheckedChangeListener {
    @BindView(R.id.cb_cache)
    CheckBox mCBCahce;
    @BindView(R.id.cb_image)
    CheckBox mCBImage;
    @BindView(R.id.cb_night)
    CheckBox mCBNight;
    @BindView(R.id.tv_cache_size)
    TextView mTVCacheSize;
    @BindView(R.id.ll_setting_clear)
    LinearLayout mClearLayout;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initEventAndData() {

        mCBNight.setChecked(SharePreferenceUtil.getNightMode());
        mCBNight.setOnCheckedChangeListener(this);
        mCBCahce.setChecked(SharePreferenceUtil.getAutoCache());
        mCBCahce.setOnCheckedChangeListener(this);
        mCBImage.setChecked(SharePreferenceUtil.getNoImage());
        mCBImage.setOnCheckedChangeListener(this);

        mTVCacheSize.setText(ACache.getCacheSizeString());
    }

    @OnClick(R.id.ll_setting_clear)
    void clearCache(){
        ACache.deleteCache();
        mTVCacheSize.setText(ACache.getCacheSizeString());

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_night:
                NightEvent event = new NightEvent();
                event.setNight(b);
                RxBus.getDefault().post(event);
                SharePreferenceUtil.setNightMode(b);
                break;
            case R.id.cb_cache:
                SharePreferenceUtil.setAutoCache(b);
                break;
            case R.id.cb_image:
                SharePreferenceUtil.setNoImage(b);
                break;
            default:
                break;
        }

    }
}
