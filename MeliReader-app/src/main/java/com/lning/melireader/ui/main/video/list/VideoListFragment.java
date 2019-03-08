package com.lning.melireader.ui.main.video.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.VideoListContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.FavouriteChannel;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
import com.lning.melireader.core.repository.http.bean.VideoNewsListVo;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.presenter.VideoListPresenter;
import com.lning.melireader.ui.adapter.VideoListItemAdapter;
import com.lning.melireader.ui.main.user.detail.UserFragment;
import com.lning.melireader.ui.main.video.detail.VideoDetailFragment;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Ning on 2019/2/8.
 */

public class VideoListFragment extends RootFragment<VideoListPresenter>
        implements VideoListContact.View, XRecyclerView.LoadingListener {

    @BindView(R.id.view_main)
    XRecyclerView mRecyclerView;

    VideoListItemAdapter mAdapter;

    private List<VideoNewsListVo> mList;
    private LinearLayoutManager linearLayoutManager;
    private FavouriteChannel favouriteChannel;

    @Override
    protected int getLayoutId() {
        return R.layout.view_base_xlist;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        super.init(savedInstanceState, mView);
        initBasisData();
        initMultiTypeAdapter();
        mPresenter.getVideoNewsListByDislikeIds(favouriteChannel.getChannel_id(), AppConstant.TYPE_VIDEO);
    }

    private void initBasisData() {
        Bundle bundle = getArguments();
        favouriteChannel = bundle.getParcelable(AppConstant.FAVOURITE_CHANNEL);
        mList = new ArrayList<>();
    }

    private void initMultiTypeAdapter() {
        mAdapter = new VideoListItemAdapter(getSimpleActivity(), mList, simpleOnClickListener);
        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotateMultiple);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallGridPulse);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
//                    对应的播放列表
                    if (GSYVideoManager.instance().getPlayTag().equals(AppConstant.VIDEO_LIST_TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        if (!GSYVideoManager.isFullState(getActivity())) {
                            GSYVideoManager.releaseAllVideos();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    OnItemClickListener simpleOnClickListener = new SimpleItemClickListenerAdapter() {

        @Override
        public void OnItemCheckChange(View view, Object object, boolean isChecked) {
            if (object instanceof String) {
                // 关注与否
                mPresenter.operateAttention(isChecked, (String) object);
            }
        }

        @Override
        public void OnItemClick(View view, Object object) {
            if (object instanceof SearchChannelItem) {
                // 用户详情
                SearchChannelItem item = (SearchChannelItem) object;
                ((SupportFragment) getParentFragment().getParentFragment())
                        .start(UserFragment.newInstance(item.getChannelId(), item.getChannelName(), item.getChannelName()));
            } else if (object instanceof NewsListVo) {
                // 新闻详情
                mPresenter.insertHistory((NewsListVo) object);
            }
        }
    };

    @Override
    public boolean onBackPressedSupport() {
        if (GSYVideoManager.backFromWindowFull(getActivity())) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    public static VideoListFragment newInstance(FavouriteChannel favouriteChannel) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.FAVOURITE_CHANNEL, favouriteChannel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onRefresh() {
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreVideoNewsListByDislikeIds(favouriteChannel.getChannel_id(), AppConstant.TYPE_VIDEO, true);
    }

    @Override
    public void onLoadMore() {
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreVideoNewsListByDislikeIds(favouriteChannel.getChannel_id(), AppConstant.TYPE_VIDEO, false);
    }

    @Override
    public void onGetVideoNewsListSuccess(List<VideoNewsListVo> newsListVoList, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
            mRecyclerView.refreshComplete();
        } else {
            mRecyclerView.loadMoreComplete();
        }
        mList.addAll(newsListVoList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onInsertHistoryNewsListVoSuccess(NewsListVo newsListVo) {
        ((SupportFragment) (getParentFragment().getParentFragment())).start(VideoDetailFragment.newInstance(newsListVo, 0));
    }

    @Override
    public void onOperateAttentionSuccess(boolean attention) {
        if (attention) {
            showMessage(getString(R.string.tips_attention_success));
        } else {
            showMessage(getString(R.string.tips_cancel_attention_success));
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }


    @Override
    public void onDestroyView() {
        if (mRecyclerView != null) {
            mRecyclerView.destroy();
        }
        super.onDestroyView();
    }

    @Override
    protected void onRetryClick() {
        mPresenter.getVideoNewsListByDislikeIds(favouriteChannel.getChannel_id(), "video");
    }

    public void onGetListError() {
        if (mRecyclerView != null) {
            mRecyclerView.refreshComplete();
            mRecyclerView.loadMoreComplete();
        }
    }
}
