package com.lning.melireader.ui.main.news.article;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.event.TabSelectedEvent;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.NewsListContact;
import com.lning.melireader.core.app.base.App;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.FavouriteChannel;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.presenter.NewsListPresenter;
import com.lning.melireader.ui.main.MainFragment;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.main.video.detail.VideoDetailFragment;
import com.lning.melireader.ui.provider.NewsListItemBigProvider;
import com.lning.melireader.ui.provider.NewsListItemMultiProvider;
import com.lning.melireader.ui.provider.NewsListItemSingleProvider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Ning on 2019/2/8.
 */

public class NewsListFragment extends RootFragment<NewsListPresenter>
        implements NewsListContact.View, OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Inject
    MultiTypeAdapter mMultiTypeAdapter;

    @Inject
    Items mItems;

    private FavouriteChannel favouriteChannel;
    private RefreshLayout mRefreshLayout;
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        super.init(savedInstanceState, mView);
        initBasicData();
        initMultiTypeAdapter();
        if (favouriteChannel.getChannel_name().equals("视频")) {
            mPresenter.getNewsListByDislikeIds(favouriteChannel.getChannel_id(), "video");
        } else {
            mPresenter.getNewsListByDislikeIds(favouriteChannel.getChannel_id(), AppConstant.TYPE_ALL);
        }
    }

    private void initMultiTypeAdapter() {
        mMultiTypeAdapter.register(NewsListVo.class).to(
                new NewsListItemSingleProvider(simpleOnItemClickListener()),
                new NewsListItemMultiProvider(simpleOnItemClickListener()),
                new NewsListItemBigProvider(simpleOnItemClickListener())
        ).withClassLinker(new ClassLinker<NewsListVo>() {
            @NonNull
            @Override
            public Class<? extends ItemViewBinder<NewsListVo, ?>> index(int position, @NonNull NewsListVo newsListVo) {
                String image = newsListVo.getImage();
                if (!TextUtils.isEmpty(image)) {
                    String[] images = image.split(",");
                    if (images.length >= 3) {
                        if (position % 2 == 0) {
                            return NewsListItemSingleProvider.class;
                        }
                        return NewsListItemMultiProvider.class;
                    }
                }
                if (position % 2 == 0) {
                    return NewsListItemSingleProvider.class;
                } else {
                    return NewsListItemBigProvider.class;
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    private OnItemClickListener simpleOnItemClickListener() {
        return new SimpleItemClickListenerAdapter() {
            @Override
            public void OnItemClick(View view, Object object, int position) {
                NewsListVo newsListVo = (NewsListVo) object;
                mPresenter.insertClickHistory(newsListVo);
            }
        };
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        favouriteChannel = bundle.getParcelable(AppConstant.FAVOURITE_CHANNEL);
        position = bundle.getInt(AppConstant.POSITION);
    }

    public static NewsListFragment newInstance(int position, FavouriteChannel favouriteChannel) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.POSITION, position);
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
    public void dismissDialog() {
        super.dismissDialog();
        if (mRefreshLayout != null && mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout != null && mRefreshLayout.getState() == RefreshState.Loading) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreNewsListByDislikeIds(favouriteChannel.getChannel_id(), AppConstant.TYPE_ALL, false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreNewsListByDislikeIds(favouriteChannel.getChannel_id(), AppConstant.TYPE_ALL, true);
    }

    @Override
    public void onGetNewsListSuccess(List<NewsListVo> newsListVoList, boolean isRefresh) {
        if (isRefresh) {
            mItems.clear();
        }
        mItems.addAll(newsListVoList);
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRetryClick() {
        mPresenter.getNewsListByDislikeIds(favouriteChannel.getChannel_id(), AppConstant.TYPE_ALL);
    }

    @Override
    public void onInsertHistoryNewsListVoSuccess(NewsListVo newsListVo) {
        if (newsListVo.getCtype().equals(AppConstant.TYPE_VIDEO)) {
            ((SupportFragment) (getParentFragment().getParentFragment())).start(VideoDetailFragment.newInstance(newsListVo, 0));
        } else {
            ((SupportFragment) (getParentFragment().getParentFragment())).start(NewsDetailFragment.newInstance(newsListVo));
        }
    }


}
