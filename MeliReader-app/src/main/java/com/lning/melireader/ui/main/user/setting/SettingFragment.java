package com.lning.melireader.ui.main.user.setting;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.SettingContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.DialogUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.PhoneUtils;
import com.lning.melireader.presenter.SettingPresenter;
import com.lning.melireader.ui.login.LoginFragment;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Ning on 2019/2/17.
 */

public class SettingFragment extends BaseFragment<SettingPresenter>
        implements SettingContact.View {

    @BindView(R.id.setting_text_tv)
    AppCompatTextView settingTextTv;

    @BindView(R.id.setting_clear_tv)
    AppCompatTextView settingClearTv;

    @BindView(R.id.setting_wifi_tips_sc)
    SwitchCompat settingWifiTipsSc;

    @BindView(R.id.setting_update_tv)
    AppCompatTextView settingUpdateTv;

    @BindView(R.id.setting_user_tv)
    AppCompatTextView settingUserTv;

    @BindView(R.id.setting_user_status_tv)
    AppCompatTextView settingUserStatusTv;

    private String token;
    private String nickname;
    private String textSize;
    private String cacheSize;
    private Boolean wifiTip;
    private String appVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        initBasicData();
        initToolbar(view, getString(R.string.tips_setting_title), R.mipmap.ic_navigation_back);
        onGetUserInfoSuccess();
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        token = bundle.getString(AppConstant.TOKEN, "");
        nickname = bundle.getString(AppConstant.NICKNAME, getString(R.string.tips_setting_login));
    }

    @OnCheckedChanged(R.id.setting_wifi_tips_sc)
    public void onWifiTipsScCheckChange(boolean checked) {
        LogUtils.d(checked + "");
        mPresenter.getModel().updateLocalWifiTip(checked);
    }

    @OnClick(R.id.setting_user_cl)
    public void onSettingUserClick() {
        if (!TextUtils.isEmpty(token)) {
            DialogUtils.showConfirmDialog(getContext(), "退出登录", "你确定想从这个账号退出吗？",
                    new SimpleItemClickListenerAdapter() {
                        @Override
                        public void OnItemClick(View view, int position) {
                        }
                    });
        } else {
            start(LoginFragment.newInstance());
        }
    }

    @OnClick(R.id.setting_text_cl)
    public void onSettingTextClick() {
        DialogUtils.showTextSortDialog(getContext(), new SimpleItemClickListenerAdapter() {
            @Override
            public void OnItemClick(View view, int position) {
                LogUtils.d(position + "");
                mPresenter.getModel().updateLocalTextSize(position);
                onGetUserInfoSuccess();
            }
        });
    }

    @OnClick(R.id.setting_clear_cl)
    public void onSettingClearClick() {
        clearCache();
    }

    @OnClick(R.id.setting_about_cl)
    public void onSettingAboutClick() {

    }

    @OnClick(R.id.setting_update_cl)
    public void onSettingUpdateClick() {
        mPresenter.onVersionControlClick();
    }

    private void clearCache() {
        DialogUtils.showConfirmDialog(getContext(), "提示信息", "是否需要清除缓存？", new SimpleItemClickListenerAdapter() {
            @Override
            public void OnItemClick(View view, int position) {
                if (position == 0) {
                    PhoneUtils.clearAllCache(getContext());
                    onGetUserInfoSuccess();
                }
            }
        });
    }

    private void onGetUserInfoSuccess() {
        try {
            settingUserTv.setText(!TextUtils.isEmpty(nickname) ? nickname : getString(R.string.tips_setting_user));
            settingUserStatusTv.setText(!TextUtils.isEmpty(token) ? getString(R.string.tips_setting_logout) : getString(R.string.tips_setting_login));
            settingTextTv.setText(AppConstant.TEXTSIZE[mPresenter.getModel().getLocalTextSize()]);
            settingClearTv.setText(PhoneUtils.getCacheSize(getContext().getCacheDir(), getContext().getExternalCacheDir()));
            settingWifiTipsSc.setChecked(mPresenter.getModel().getLocalWifiTip());
            settingUpdateTv.setText(PhoneUtils.getAppVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build().inject(this);
    }

    public static SettingFragment newInstance(String token, String nickname) {
        SettingFragment fragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.TOKEN, token);
        bundle.putString(AppConstant.NICKNAME, nickname);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onLogoutSuccess() {

    }
}
