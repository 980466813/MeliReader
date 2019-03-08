package com.lning.melireader.ui.main.user.detail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.UserContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.presenter.UserPresenter;
import com.lning.melireader.ui.adapter.UserTabAdapter;
import com.lning.melireader.ui.main.user.info.UserInfoFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Ning on 2019/2/24.
 */

public class UserFragment extends BaseFragment<UserPresenter>
        implements UserContact.View, OnLoadMoreListener {

    @BindView(R.id.user_collapsing_tl)
    CollapsingToolbarLayout userCollapsingTl;

    @BindView(R.id.user_icon_iv)
    AppCompatImageView userIconIv;

    @BindView(R.id.user_summary_tv)
    AppCompatTextView userSummaryTv;

    @BindView(R.id.user_sub_cb)
    AppCompatCheckBox userSubCb;

    @BindView(R.id.user_edit_tv)
    AppCompatTextView userEditTv;

    @BindView(R.id.user_tab_tl)
    TabLayout userTabTl;

    @BindView(R.id.user_attention_tv)
    AppCompatTextView userAttentionTv;

    @BindView(R.id.user_fans_tv)
    AppCompatTextView userFansTv;

    @BindView(R.id.user_pager_vp)
    ViewPager userPagerVp;

    @BindView(R.id.view_base_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private UserTabAdapter mAdapter;

    private String userProfile;
    private String userId;
    private String userName;
    private String ownerId;
    private LinkedList<Item> mItems;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @OnClick(R.id.user_arrow_iv)
    public void onSummaryClick() {
        int maxLine = userSummaryTv.getMaxLines();
        userSummaryTv.setMaxLines(maxLine > 1 ? 1 : 4);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        initBasicData();
        mPresenter.getUserCommonInfo(userId, ownerId);
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        userId = bundle.getString(AppConstant.USER_ID, "");
        userName = bundle.getString(AppConstant.USER_NAME, "");
        userProfile = bundle.getString(AppConstant.USER_PROFILE, "");
        ownerId = mPresenter.getModel().getLocalUserId();
        userCollapsingTl.setTitle(userName);
        userSubCb.setVisibility(ownerId.equals(userId) ? View.GONE : View.VISIBLE);
        userEditTv.setVisibility(ownerId.equals(userId) ? View.VISIBLE : View.GONE);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(true);
        GlideUtils.loadUserHead(userIconIv, String.format(AppConstant.IMAGE_URL, userProfile));
    }

    @OnClick(R.id.user_sub_cb)
    public void onSubChanged() {
        mPresenter.operateAttention(userSubCb.isChecked(), userId);
    }

    @Override
    public void onOperateAttentionSuccess(boolean attention) {
        setSubscriptCbStatus(attention);
        if (attention) {
            showMessage(getString(R.string.tips_attention_success));
        } else {
            showMessage(getString(R.string.tips_cancel_attention_success));
        }
    }

    public void setSubscriptCbStatus(boolean isSubscripted) {
        userSubCb.setChecked(isSubscripted);
        userSubCb.setText(getString(isSubscripted ? R.string.tips_already_subscripted : R.string.tips_not_subscript));
    }

    @OnClick(R.id.user_edit_tv)
    public void onUserEditClick() {
        start(UserInfoFragment.newInstance(userId));
    }

    public static UserFragment newInstance(String userId, String userName, String userProfile) {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.USER_ID, userId);
        bundle.putString(AppConstant.USER_NAME, userName);
        bundle.putString(AppConstant.USER_PROFILE, userProfile);
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.toolbar_left_btn)
    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }

    public void showUserCommonInfo(UserVo userVo) {
        Long rid = userVo.getRid();
        userSummaryTv.setText(userVo.getSignature() != null ? userVo.getSignature() : "这个人太懒了，连简介都不写");
        setSubscriptCbStatus(userVo.isSubscripted());
        userAttentionTv.setText(getString(R.string.tips_user_attention_template, userVo.getAttentionCount()));
        userFansTv.setText(getString(R.string.tips_user_fans_template, userVo.getFansCount()));
        mItems = new LinkedList<>();
        boolean isChannel = rid.equals(AppConstant.CHANNEL);
        if (isChannel) {
            mItems.add(new Item("主页", AppConstant.TYPE_NEWS, JsonUtils.objectToJson(userVo.getItemListVo())));
            mItems.add(new Item("动态", AppConstant.TYPE, null));
        } else {
            mItems.add(new Item("动态", AppConstant.TYPE_COMMENT, JsonUtils.objectToJson(userVo.getItemListVo())));
        }
        mAdapter = new UserTabAdapter(getChildFragmentManager(), mItems, userId, ownerId);
        userPagerVp.setOffscreenPageLimit(mItems.size());
        userPagerVp.setAdapter(mAdapter);
        userTabTl.setupWithViewPager(userPagerVp, true);
        userTabTl.setTabTextColors(getResources().getColor(R.color.md_grey_666), getResources().getColor(R.color.md_blue_500));
        userTabTl.setSelectedTabIndicatorColor(getResources().getColor(R.color.md_blue_500));
    }


    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder().appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build().inject(this);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (mAdapter != null) {
            ((OnLoadMoreListener) mAdapter.getCurFragment()).onLoadMore(refreshLayout);
        }
    }

    public class Item {
        public String title;
        public String type;
        public String defaultValue;

        public Item() {
        }

        public Item(String title, String type, String defaultValue) {
            this.title = title;
            this.type = type;
            this.defaultValue = defaultValue;
        }
    }
}
