package com.lning.melireader.ui.main.user;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.UserCenterContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.presenter.UserCenterPresenter;
import com.lning.melireader.ui.login.LoginFragment;
import com.lning.melireader.ui.main.news.comment.NewsCommentFragment;
import com.lning.melireader.ui.main.news.recommend.NewsRecommendFragment;
import com.lning.melireader.ui.main.user.collection.CollectionListFragment;
import com.lning.melireader.ui.main.user.detail.UserFragment;
import com.lning.melireader.ui.main.user.history.HistoryFragment;
import com.lning.melireader.ui.main.user.info.UserInfoFragment;
import com.lning.melireader.ui.main.user.setting.SettingFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Ning on 2019/2/8.
 */

public class UserCenterFragment extends BaseFragment<UserCenterPresenter>
        implements UserCenterContact.View {

    @BindView(R.id.user_center_login_cl)
    ConstraintLayout userCenterLoginCl;

    @BindView(R.id.user_center_info_cl)
    ConstraintLayout userCenterInfoCl;

    @BindView(R.id.user_center_icon_iv)
    AppCompatImageView userCenterIconIv;

    @BindView(R.id.user_center_name_tv)
    AppCompatTextView userCenterNameTv;

    @BindView(R.id.user_center_attention_tv)
    AppCompatTextView userCenterAttentionTv;

    @BindView(R.id.user_center_fans_tv)
    AppCompatTextView userCenterFansTv;

    private UserVo mUserVo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_center;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        mPresenter.getUserBasicInfo();
    }

    @OnClick(value = {R.id.user_center_login_btn})
    public void onLoginClick() {
        ((SupportFragment) getParentFragment()).start(LoginFragment.newInstance(), SINGLETASK);
    }

    @OnClick(R.id.user_center_setting_rl)
    public void onSettingClick() {
        ((SupportFragment) getParentFragment()).start(SettingFragment.newInstance(
                mPresenter.getModel().getLocalLoginUserToken(), mUserVo == null ? "" : mUserVo.getNickname()), SINGLETASK);
    }

    @OnClick(R.id.user_center_manage_channel_rl)
    public void onManageChannelClick() {
        LogUtils.d("点击管理频道");
    }

    @OnClick(R.id.user_center_info_cl)
    public void onUserInfoClick() {
        LogUtils.d("点击用户容器");
        ((SupportFragment) getParentFragment()).start(UserFragment.newInstance(mUserVo.getId(), mUserVo.getNickname(), mUserVo.getProfile()));
    }

    @OnClick(value = {R.id.user_center_history_ll, R.id.user_center_message_ll, R.id.user_center_collection_ll})
    public void onThreeClick(View view) {
        switch (view.getId()) {
            case R.id.user_center_history_ll:
                ((SupportFragment) getParentFragment()).start(HistoryFragment.newInstance());
                break;
            case R.id.user_center_message_ll:
//                ((SupportFragment) getParentFragment()).start(NewsRecommendFragment.newInstance("qzNwaOiu,author_10186103,ginz8RMu,W5vgKNmRABYwiE,0CoLD59kHPa1wM,IE5KDx1F6o3liu", "news"));
                ((SupportFragment) getParentFragment()).start(UserInfoFragment.newInstance("1"));
                break;
            case R.id.user_center_collection_ll:
                ((SupportFragment) getParentFragment()).start(CollectionListFragment.newInstance(0, getString(R.string.tips_user_center_collection)));
                break;
        }
    }

    public static BaseFragment newInstance() {
        UserCenterFragment fragment = new UserCenterFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void showUserInfo(UserVo userVo) {
        if (userVo != null) {
            mUserVo = userVo;
            GlideUtils.loadUserHead(userCenterIconIv, String.format(AppConstant.IMAGE_URL, userVo.getProfile()));
            userCenterNameTv.setText(userVo.getNickname());
            userCenterFansTv.setText(getString(R.string.tips_user_fans_template, userVo.getFansCount()));
            userCenterAttentionTv.setText(getString(R.string.tips_user_attention_template, userVo.getAttentionCount()));
            showUserBasicLayout();
        } else {
            hideUserBasicLayout();
        }
    }

    private void showUserBasicLayout() {
        userCenterInfoCl.setVisibility(View.VISIBLE);
        userCenterLoginCl.setVisibility(View.GONE);
    }

    @Override
    public void hideUserBasicLayout() {
        userCenterInfoCl.setVisibility(View.GONE);
        userCenterLoginCl.setVisibility(View.VISIBLE);
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }
}
