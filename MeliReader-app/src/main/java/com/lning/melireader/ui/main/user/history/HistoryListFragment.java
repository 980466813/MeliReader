package com.lning.melireader.ui.main.user.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.event.HistoryEvent;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.HistoryListContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.presenter.HistoryListPresenter;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.main.video.detail.VideoDetailFragment;
import com.lning.melireader.ui.provider.HistoryListItemProvider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Ning on 2019/2/13.
 */

public class HistoryListFragment extends RootFragment<HistoryListPresenter>
        implements HistoryListContact.View, OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Inject
    MultiTypeAdapter mMultiTypeAdapter;

    @Inject
    Items mItems;

    private RefreshLayout mRefreshLayout;
    private String type;
    private int position;

    public static HistoryListFragment newInstance(String type, int position) {
        HistoryListFragment fragment = new HistoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.TYPE, type);
        bundle.putInt(AppConstant.POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_base_list;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        initMultiTypeAdapter();
        initBasicData();
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        type = bundle.getString(AppConstant.TYPE, "");
        position = bundle.getInt(AppConstant.POSITION, 0);
        mPresenter.initHistoryList(type);
    }


    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    private void initMultiTypeAdapter() {
        mMultiTypeAdapter.register(HistoryVo.class, new HistoryListItemProvider(listener));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMultiTypeAdapter.notifyDataSetChanged();
    }


    OnItemClickListener listener = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, Object object, int position) {
            NewsListVo newsListVo = (NewsListVo) object;
            if (AppConstant.TYPE_VIDEO.equals(newsListVo.getCtype())) {
                ((SupportFragment) getParentFragment()).start(VideoDetailFragment.newInstance(newsListVo, 0));
            } else {
                ((SupportFragment) getParentFragment()).start(NewsDetailFragment.newInstance(newsListVo));
            }
        }
    };

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
    public void showHistoryList(List<HistoryVo> historyVos, boolean isRefresh) {
        if (isRefresh) {
            mItems.clear();
        }
        mItems.addAll(historyVos);
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        if (mPresenter != null) {
            mPresenter.setCurRefreshError(true);
            mPresenter.onLoadMoreHistoryList(type, false);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreHistoryList(type, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteHistoryVo(HistoryEvent event) {
        if (position == event.getCurPosition()) {
            LogUtils.d("当前页 : " + position + " ;      点击删除 : " + event.getCurPosition());
//            mPresenter.
        }
    }
}
