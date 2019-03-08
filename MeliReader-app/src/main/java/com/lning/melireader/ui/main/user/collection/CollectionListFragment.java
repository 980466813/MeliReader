package com.lning.melireader.ui.main.user.collection;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.CollectionListContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.CollectionVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.DialogUtils;
import com.lning.melireader.presenter.CollectionListPresenter;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.provider.CollectionListItemProvider;
import com.lning.melireader.ui.provider.HistoryListItemProvider;
import com.lning.melireader.ui.provider.bean.CollectionPro;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Ning on 2019/2/13.
 */

public class CollectionListFragment extends RootFragment<CollectionListPresenter>
        implements CollectionListContact.View, OnRefreshListener, OnLoadMoreListener {

    public static final int MODE_TYPE = 0;
    public static final int MODE_TAG = 1;


    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @BindView(R.id.view_base_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @Inject
    MultiTypeAdapter mMultiTypeAdapter;

    @Inject
    Items mItems;


    private String title;
    private int mode;
    private String val;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection_list;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        super.init(savedInstanceState, view);
        initBasicData();
        initToolbar(view, title, R.mipmap.ic_navigation_back);
        initMultiTypeAdapter();
        mPresenter.initCollectionList(mode, val);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
    }

    private void initMultiTypeAdapter() {
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.md_grey_200));
        mMultiTypeAdapter.register(CollectionPro.class, new CollectionListItemProvider(simpleOnItemClickListener()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        title = bundle.getString(AppConstant.TITLE, getString(R.string.tips_user_center_collection));
        mode = bundle.getInt(AppConstant.MODE, MODE_TYPE);
        if ("收藏".equals(title)) {
            val = "all";
        } else {
            val = title;
        }
    }

    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    public static CollectionListFragment newInstance(int mode, String title) {
        CollectionListFragment fragment = new CollectionListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.TITLE, title);
        bundle.putInt(AppConstant.MODE, mode);
        fragment.setArguments(bundle);
        return fragment;
    }

    private OnItemClickListener simpleOnItemClickListener() {
        return new SimpleItemClickListenerAdapter() {
            //长按
            @Override
            public void OnItemClick(View view, final Object object, int position) {
                DialogUtils.showSortDialog(getContext(), new SimpleItemClickListenerAdapter() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        CollectionVo collectionVo = (CollectionVo) object;
                        if (position == 0) {
                            startForResult(CollectionUpdateFragment.newInstance(collectionVo, position), 0x10000);
                        } else {
                            mPresenter.deleteCollection(collectionVo.getNewsId());
                        }
                    }
                });
            }

            // View 为空，点击新闻
            // View 不为空，点击标签
            @Override
            public void OnItemClick(View view, Object object) {
                if (view != null) {
                    startWithPop(CollectionListFragment.newInstance(MODE_TAG, (String) object));
                } else {
                    CollectionVo collectionVo = (CollectionVo) object;
                    NewsListVo newsListVo = CommonUtils.parseNewsListVo(collectionVo);
                    start(NewsDetailFragment.newInstance(newsListVo));
                }
            }
        };
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
    public void showCollectionList(String val, List<CollectionPro> collectionPros, boolean isCurRefresh) {
        this.val = val;
        if (isCurRefresh) {
            mItems.clear();
        }
        mItems.addAll(collectionPros);
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreCollectionList(mode, val, false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.setCurRefreshError(true);
        mPresenter.onLoadMoreCollectionList(mode, val, true);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 0x10000 && resultCode == 0x10001) {
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void showError(String msg) {
        if (mCurrentState == STATE_ERROR)
            return;
        if (!isErrorViewAdded) {
            isErrorViewAdded = true;
            mEmptyResource = R.layout.view_error;
            View.inflate(mContext, mErrorResource, mParent);
            mViewError = mParent.findViewById(com.lning.melireader.core.R.id.view_error);
            if (!TextUtils.isEmpty(msg)) {
                ImageView imageView = mParent.findViewById(R.id.view_error_iv);
                imageView.setImageResource(R.mipmap.ic_empty_collection);
                TextView textView = mParent.findViewById(com.lning.melireader.core.R.id.view_error_msg);
                textView.setText(msg);
            }
            if (mViewError == null) {
                throw new IllegalStateException(
                        "A View should be named 'view_error' in ErrorLayoutResource.");
            }
            mViewError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetryClick();
                }
            });
        }
        hideCurrentPage();
        mCurrentState = STATE_ERROR;
        mViewError.setVisibility(View.VISIBLE);
    }
}
