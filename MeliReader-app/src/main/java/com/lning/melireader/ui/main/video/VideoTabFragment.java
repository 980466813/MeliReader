package com.lning.melireader.ui.main.video;

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
import com.lning.melireader.ui.adapter.VideoTabAdapter;
import com.lning.melireader.ui.main.video.list.VideoListFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ning on 2019/2/8.
 */

public class VideoTabFragment extends BaseFragment {
    @BindView(R.id.video_home_pager_vg)
    ViewPager videoHomePagerVg;

    @BindView(R.id.video_home_tab_tl)
    TabLayout videoHomeTabTl;

    @BindView(R.id.video_home_search_iv)
    AppCompatImageView videoHomeSearchIv;

    private VideoTabAdapter mAdapter;

    private FavouriteChannel[] favouriteChannels;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_home;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        Bundle bundle = getArguments();
        favouriteChannels = (FavouriteChannel[]) bundle.getParcelableArray(AppConstant.FAVOURITE_CHANNEL);
        mAdapter = new VideoTabAdapter(getChildFragmentManager(), favouriteChannels);
        videoHomePagerVg.setAdapter(mAdapter);
        videoHomeTabTl.setupWithViewPager(videoHomePagerVg, true);
        videoHomeTabTl.setTabTextColors(getResources().getColor(R.color.md_grey_666), getResources().getColor(R.color.md_blue_500));
        videoHomeTabTl.setSelectedTabIndicatorColor(getResources().getColor(R.color.md_blue_500));
    }

    public static BaseFragment newInstance(String favouriteChannels) {
        VideoTabFragment fragment = new VideoTabFragment();
        Bundle bundle = new Bundle();
        List<FavouriteChannel> chanels = JsonUtils.jsonToList(favouriteChannels, FavouriteChannel.class);
        FavouriteChannel[] array = new FavouriteChannel[chanels.size()];
        bundle.putParcelableArray(AppConstant.FAVOURITE_CHANNEL, chanels.toArray(array));
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.video_home_search_iv)
    public void onClick() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabSelected(TabSelectedEvent event) {
        LogUtils.d("onTabSelected:" + event.position);
    }


    @Override
    protected boolean useEventBus() {
        return true;
    }
}
