package com.lning.melireader.ui.main.user.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.UserHomeListContact;
import com.lning.melireader.core.app.base.App;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.CommentNewsListVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.presenter.UserHomeListPresenter;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.main.video.detail.VideoDetailFragment;
import com.lning.melireader.ui.provider.NewsListItemBigProvider;
import com.lning.melireader.ui.provider.NewsListItemMultiProvider;
import com.lning.melireader.ui.provider.NewsListItemSingleProvider;
import com.lning.melireader.ui.provider.UserCommentListItemProvider;
import com.lning.melireader.ui.provider.UserHomeListMultiItemProvider;
import com.lning.melireader.ui.provider.UserHomeListSingleItemProvider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

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

public class UserHomeListFragment extends RootFragment<UserHomeListPresenter>
        implements UserHomeListContact.View, OnLoadMoreListener {

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Inject
    MultiTypeAdapter mMultiTypeAdapter;

    @Inject
    Items mItems;

    private String userId;
    private String type;
    private String defaultJson;
    private String ownerId;
    private RefreshLayout mRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.view_base_only_list;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        initBasicData();
        initMultiTypeAdapter();
        if (!TextUtils.isEmpty(defaultJson)) {
            ItemListVo itemListVo = JsonUtils.jsonToPojo(defaultJson, ItemListVo.class);
            onGetListSuccess(itemListVo.getRows().toString(), type);
        } else {
            mPresenter.setCurRefreshError(false);
            mPresenter.getUserHomeList(userId, ownerId, type, true);
        }
    }

    private void initMultiTypeAdapter() {
        if (type.equals(AppConstant.TYPE_NEWS)) {
            mMultiTypeAdapter.register(NewsListVo.class).to(
                    new UserHomeListSingleItemProvider(simpleOnItemClickListener),
                    new UserHomeListMultiItemProvider(simpleOnItemClickListener)
            ).withClassLinker(new ClassLinker<NewsListVo>() {
                @NonNull
                @Override
                public Class<? extends ItemViewBinder<NewsListVo, ?>> index(int position, @NonNull NewsListVo newsListVo) {
                    if (newsListVo.getCtype().equals(AppConstant.TYPE_VIDEO)) {
                        return UserHomeListSingleItemProvider.class;
                    }
                    String image = newsListVo.getImage();
                    if (!TextUtils.isEmpty(image)) {
                        String[] images = image.split(",");
                        if (images.length >= 3) {
                            if (position % 2 == 0) {
                                return UserHomeListMultiItemProvider.class;
                            }
                        }
                    }
                    return UserHomeListSingleItemProvider.class;
                }
            });
        } else {
            mMultiTypeAdapter.register(CommentNewsListVo.class, new UserCommentListItemProvider(simpleOnItemClickListener));
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        userId = bundle.getString(AppConstant.USER_ID, "");
        type = bundle.getString(AppConstant.TYPE, AppConstant.TYPE_NEWS);
        ownerId = bundle.getString(AppConstant.OWNER_ID, "");
        defaultJson = bundle.getString(AppConstant.OBJECT, "");
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
    public void onGetListSuccess(String defaultJson, String type) {
        if (type.equals(AppConstant.TYPE_NEWS)) {
            List<NewsListVo> listVos = JsonUtils.jsonToList(defaultJson, NewsListVo.class);
            mItems.addAll(listVos);
        } else {
            List<CommentNewsListVo> listVos = JsonUtils.jsonToList(defaultJson, CommentNewsListVo.class);
            mItems.addAll(listVos);
        }
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onInsertHistoryNewsListVoSuccess(NewsListVo newsListVo) {
        if (AppConstant.TYPE_VIDEO.equals(newsListVo.getCtype())) {
            ((SupportFragment) getParentFragment()).start(VideoDetailFragment.newInstance(newsListVo, 0));
        } else {
            ((SupportFragment) getParentFragment()).start(NewsDetailFragment.newInstance(newsListVo));
        }
    }

    OnItemClickListener simpleOnItemClickListener = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, Object object) {
            if (object instanceof NewsListVo) {
                NewsListVo newsListVo = (NewsListVo) object;
                mPresenter.insertHistory(newsListVo);
            }
        }

        @Override
        public void OnItemClick(View view, Object object, int position) {
            if (object instanceof String) {
                mPresenter.likeComment(object.toString());
            }
        }
    };

    public static UserHomeListFragment newInstance(String userId, String ownerId, String type, String defaultValue) {
        UserHomeListFragment fragment = new UserHomeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.USER_ID, userId);
        bundle.putString(AppConstant.TYPE, type);
        bundle.putString(AppConstant.OBJECT, defaultValue);
        bundle.putString(AppConstant.OWNER_ID, ownerId);
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
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.setCurRefreshError(true);
        mPresenter.getUserHomeList(userId, ownerId, type, false);
    }
}
