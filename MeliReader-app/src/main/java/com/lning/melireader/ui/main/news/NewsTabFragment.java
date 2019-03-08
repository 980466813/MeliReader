package com.lning.melireader.ui.main.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.event.TabSelectedEvent;
import com.lning.melireader.core.repository.http.bean.FavouriteChannel;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.ui.adapter.NewsTabAdapter;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.search.SearchFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Ning on 2019/2/8.
 */

public class NewsTabFragment extends BaseFragment
        implements OnLoadMoreListener, OnRefreshListener {

    @BindView(R.id.home_pager_vg)
    ViewPager homePagerVg;

    @BindView(R.id.home_tab_tl)
    TabLayout homeTabTl;

    @BindView(R.id.home_refresh_srl)
    SmartRefreshLayout homeRefreshSrl;

    @BindView(R.id.home_user_icon_iv)
    AppCompatImageView homeUserIconIv;

    private NewsTabAdapter mAdapter;

    private FavouriteChannel[] favouriteChannels;


    public static NewsTabFragment newInstance(String favouriteChannels) {
        NewsTabFragment fragment = new NewsTabFragment();
        LogUtils.d(favouriteChannels);
        List<FavouriteChannel> chanels = JsonUtils.jsonToList(favouriteChannels, FavouriteChannel.class);
        FavouriteChannel[] array = new FavouriteChannel[chanels.size()];
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(AppConstant.FAVOURITE_CHANNEL, chanels.toArray(array));
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.home_search_rl)
    public void onSearchClick() {
        ((SupportFragment) getParentFragment()).start(SearchFragment.newInstance(""));
    }

    @OnClick(R.id.home_user_icon_iv)
    public void onClick() {
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        Bundle bundle = getArguments();
        favouriteChannels = (FavouriteChannel[]) bundle.getParcelableArray(AppConstant.FAVOURITE_CHANNEL);
        mAdapter = new NewsTabAdapter(getChildFragmentManager(), favouriteChannels);
        homePagerVg.setOffscreenPageLimit(favouriteChannels.length / 2);
        homePagerVg.setAdapter(mAdapter);
        homeTabTl.setupWithViewPager(homePagerVg, true);
        homeTabTl.setTabTextColors(getResources().getColor(R.color.md_grey_666), getResources().getColor(R.color.md_blue_500));
        homeTabTl.setSelectedTabIndicatorColor(getResources().getColor(R.color.md_blue_500));
        homeRefreshSrl.setOnLoadMoreListener(this);
        homeRefreshSrl.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        mAdapter.getCurrentItem().onLoadMore(refreshLayout);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mAdapter.getCurrentItem().onRefresh(refreshLayout);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabSelected(TabSelectedEvent event) {
        LogUtils.d("onTabSelected:" + event.position);
    }
}
