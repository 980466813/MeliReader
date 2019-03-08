package com.lning.melireader.ui.main.user.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.jakewharton.rxbinding2.view.RxView;
import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.event.HistoryEvent;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.ui.adapter.HistoryTabAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/12.
 */

public class HistoryFragment extends BaseFragment
        implements OnLoadMoreListener, OnRefreshListener {

    public enum Item {
        AllFragment("综合", "all", HistoryListFragment.class),
        ArticleFragment("文章", "news", HistoryListFragment.class),
        VideoFragment("视频", "video", HistoryListFragment.class);

        public Class<? extends Fragment> cls;
        public String title;
        public String type;

        Item(String title, String type, Class<? extends Fragment> cls) {
            this.title = title;
            this.type = type;
            this.cls = cls;
        }
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_left_btn)
    AppCompatImageButton toolbarLeftBtn;

    @BindView(R.id.toolbar_tab_tl)
    TabLayout toolbarTabTl;

    @BindView(R.id.toolbar_right_btn)
    AppCompatTextView toolbarRightBtn;

    @BindView(R.id.history_container_vp)
    ViewPager historyContainerVp;

    @BindView(R.id.view_base_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private String token;

    private HistoryTabAdapter mAdapter;

    @Override
    protected void init(@Nullable Bundle savedInstanceState, View rootView) {
        initBasicData();
        initTabLayout();
        initListener();
    }

    private void initListener() {
        RxView.clicks(toolbarLeftBtn).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                onNavigationClickListener(toolbarLeftBtn);
                LogUtils.d("后退");
            }
        });
        RxView.clicks(toolbarRightBtn).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                onToolbarRightClickListener(toolbarRightBtn);
                LogUtils.d("删除");
            }
        });
        mRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        token = bundle.getString(AppConstant.TOKEN, "");
    }

    private void initTabLayout() {
        toolbarRightBtn.setText(getString(R.string.action_delete));
        mAdapter = new HistoryTabAdapter(getChildFragmentManager(), Item.values());
        historyContainerVp.setAdapter(mAdapter);
        historyContainerVp.setOffscreenPageLimit(Item.values().length);
        toolbarTabTl.setupWithViewPager(historyContainerVp, true);
        mRefreshLayout.setEnableLoadMore(true);
    }

    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }

    @Override
    protected void onToolbarRightClickListener(View view) {
        EventBus.getDefault().post(new HistoryEvent(historyContainerVp.getCurrentItem()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mAdapter.getCurrentItem().onRefresh(refreshLayout);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        mAdapter.getCurrentItem().onLoadMore(refreshLayout);
    }
}
