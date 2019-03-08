package com.lning.melireader.ui.main.attention;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.AttentionCenterContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.presenter.AttentionCenterPresenter;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.main.user.detail.UserFragment;
import com.lning.melireader.ui.main.video.detail.VideoDetailFragment;
import com.lning.melireader.ui.provider.AttentionTabMultiItemProvider;
import com.lning.melireader.ui.provider.AttentionTabSingleItemProvider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Ning on 2019/2/27.
 */

public class AttentionTabFragment extends RootFragment<AttentionCenterPresenter>
        implements AttentionCenterContact.View, OnRefreshListener, OnLoadMoreListener {

    @Inject
    MultiTypeAdapter mMultiTypeAdapter;

    @Inject
    Items mItems;

    @BindView(R.id.view_base_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Override
    protected void init(@Nullable Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        initBasicData();
        initMultiTypeAdapter();
        mPresenter.getAttentionNewsListByUserId(AppConstant.TYPE_ALL);
    }

    private void initMultiTypeAdapter() {
        mMultiTypeAdapter.register(NewsListVo.class).to(
                new AttentionTabSingleItemProvider(simpleOnItemClickListener),
                new AttentionTabMultiItemProvider(simpleOnItemClickListener)
        ).withClassLinker(new ClassLinker<NewsListVo>() {
            @NonNull
            @Override
            public Class<? extends ItemViewBinder<NewsListVo, ?>> index(int position, @NonNull NewsListVo newsListVo) {
                String image = newsListVo.getImage();
                if (AppConstant.TYPE_VIDEO.equals(newsListVo.getCtype())) {
                    return AttentionTabSingleItemProvider.class;
                }
                if (!TextUtils.isEmpty(image)) {
                    String[] images = image.split(",");
                    if (images.length >= 2) {
                        if (position % 2 == 0) {
                            return AttentionTabMultiItemProvider.class;
                        } else {
                            return AttentionTabSingleItemProvider.class;
                        }
                    }
                }
                return AttentionTabSingleItemProvider.class;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    SimpleItemClickListenerAdapter simpleOnItemClickListener = new SimpleItemClickListenerAdapter() {

        @Override
        public void OnItemCheckChange(View view, Object object, boolean isChecked) {
            if (object instanceof String) {
                // 关注与否
                mPresenter.operateAttention(isChecked, (String) object);
            }
        }

        @Override
        public void OnItemClick(View view, Object object, int position) {
            if (object instanceof SearchChannelItem) {
                // 用户详情
                SearchChannelItem item = (SearchChannelItem) object;
                ((SupportFragment) getParentFragment())
                        .start(UserFragment.newInstance(item.getChannelId(), item.getChannelName(), item.getChannelName()));
            } else if (object instanceof NewsListVo) {
                // 新闻详情
                mPresenter.insertHistory((NewsListVo) object);
            }
        }
    };

    private void initBasicData() {
        Bundle bundle = getArguments();
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreAttentionNewsListByUserId(AppConstant.TYPE_ALL, false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreAttentionNewsListByUserId(AppConstant.TYPE_ALL, true);
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
    public void onInsertHistoryNewsListVoSuccess(NewsListVo newsListVo) {
        if (newsListVo.getCtype().equals(AppConstant.TYPE_VIDEO)) {
            ((SupportFragment) (getParentFragment())).start(VideoDetailFragment.newInstance(newsListVo, 0));
        } else {
            ((SupportFragment) (getParentFragment())).start(NewsDetailFragment.newInstance(newsListVo));
        }
    }

    @Override
    public void onOperateAttentionSuccess(boolean attention) {
        if (attention) {
            showMessage(getString(R.string.tips_attention_success));
        } else {
            showMessage(getString(R.string.tips_cancel_attention_success));
        }
        mMultiTypeAdapter.notifyDataSetChanged();
    }


    public static AttentionTabFragment newInstance() {
        AttentionTabFragment fragment = new AttentionTabFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attention_tab;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

}
