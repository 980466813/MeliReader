package com.lning.melireader.ui.search.list;

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
import com.lning.melireader.contact.SearchResultListContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
import com.lning.melireader.core.repository.http.bean.SearchItem;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.presenter.SearchResultListPresenter;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.main.user.detail.UserFragment;
import com.lning.melireader.ui.main.video.detail.VideoDetailFragment;
import com.lning.melireader.ui.provider.NewsListItemBigProvider;
import com.lning.melireader.ui.provider.NewsListItemMultiProvider;
import com.lning.melireader.ui.provider.NewsListItemSingleProvider;
import com.lning.melireader.ui.provider.SearchChannelItemProvider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Ning on 2019/2/24.
 */

public class SearchResultListFragment extends RootFragment<SearchResultListPresenter>
        implements SearchResultListContact.View, OnRefreshListener, OnLoadMoreListener {

    @Inject
    Items mItems;

    @Inject
    MultiTypeAdapter mMultiTypeAdapter;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    private String keyword;
    private String ctype;
    private RefreshLayout mRefreshLayout;

    @Override
    protected void init(@Nullable Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        initBasicData();
        initMultiTypeAdapter();
        mPresenter.initSearchKeyword(keyword, ctype);
    }

    private void initMultiTypeAdapter() {
        if (AppConstant.TYPE_CHANNEL.equals(ctype)) {
            mMultiTypeAdapter.register(SearchChannelItem.class, new SearchChannelItemProvider(simpleOnItemClickListener));
        } else {
            mMultiTypeAdapter.register(NewsListVo.class).to(
                    new NewsListItemSingleProvider(simpleOnItemClickListener),
                    new NewsListItemMultiProvider(simpleOnItemClickListener),
                    new NewsListItemBigProvider(simpleOnItemClickListener)
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
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        keyword = bundle.getString(AppConstant.KEYWORD, "");
        ctype = bundle.getString(AppConstant.TYPE, "all");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_base_only_list;
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreSearchList(keyword, ctype, false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreSearchList(keyword, ctype, true);
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
    protected void onRetryClick() {
        mPresenter.initSearchKeyword(keyword, ctype);
    }

    @Override
    public void onGetSearchKeywordList(String json, boolean isCurRefresh) {
        if (isCurRefresh) {
            mItems.clear();
        }
        if (AppConstant.TYPE_CHANNEL.equals(ctype)) {
            List<SearchChannelItem> list = JsonUtils.jsonToList(json, SearchChannelItem.class);
            mItems.addAll(list);
        } else {
            List<SearchItem> list = JsonUtils.jsonToList(json, SearchItem.class);
            List<NewsListVo> newsList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                NewsListVo newsListVo = CommonUtils.parseNewsListVo(list.get(i));
                newsList.add(newsListVo);
            }
            mItems.addAll(newsList);
        }
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    public static SearchResultListFragment newInstance(String keyword, String ctype) {
        SearchResultListFragment fragment = new SearchResultListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.KEYWORD, keyword);
        bundle.putString(AppConstant.TYPE, ctype);
        fragment.setArguments(bundle);
        return fragment;
    }

    private SimpleItemClickListenerAdapter simpleOnItemClickListener = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, Object object) {
            if (object instanceof SearchChannelItem) {
                SearchChannelItem item = (SearchChannelItem) object;
                ((SupportFragment) getParentFragment()).start(UserFragment.newInstance(item.getChannelId(), item.getChannelName(), item.getChannelImage()));
            }
        }

        @Override
        public void OnItemClick(View view, Object object, int position) {
            if (object instanceof NewsListVo) {
                NewsListVo newsListVo = (NewsListVo) object;
                if (AppConstant.TYPE_VIDEO.equals(newsListVo.getCtype())) {
                    ((SupportFragment) getParentFragment()).start(VideoDetailFragment.newInstance(newsListVo, 0));
                } else {
                    ((SupportFragment) getParentFragment()).start(NewsDetailFragment.newInstance(newsListVo));
                }
            }
        }
    };
}
