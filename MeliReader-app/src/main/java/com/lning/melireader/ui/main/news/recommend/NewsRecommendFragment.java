package com.lning.melireader.ui.main.news.recommend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.NewsRecommendContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.presenter.NewsRecommendPresenter;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.main.video.detail.VideoDetailFragment;
import com.lning.melireader.ui.provider.NewsListItemSingleProvider;
import com.lning.melireader.ui.provider.RecommendListSubTitleProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Ning on 2019/2/20.
 */

public class NewsRecommendFragment extends RootFragment<NewsRecommendPresenter>
        implements NewsRecommendContact.View {


    private String dislikeIds;
    private String ctype;

    @Inject
    MultiTypeAdapter mMultiTypeAdapter;

    @Inject
    Items mItems;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.view_base_only_list;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        initBasicData();
        initMultiTypeAdapter();
        mPresenter.getRecommendNewsList(dislikeIds, ctype);
    }

    private void initMultiTypeAdapter() {
        mMultiTypeAdapter.register(String.class, new RecommendListSubTitleProvider());
        mMultiTypeAdapter.register(NewsListVo.class, new NewsListItemSingleProvider(simpleOnItemClickListener));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        dislikeIds = bundle.getString(AppConstant.DISLIKE_IDS, "");
        ctype = bundle.getString(AppConstant.TYPE, "");
    }


    OnItemClickListener simpleOnItemClickListener = new SimpleItemClickListenerAdapter() {

        @Override
        public void OnItemClick(View view, Object object, int position) {
            NewsListVo newsListVo = (NewsListVo) object;
            if (newsListVo.getCtype().equals(AppConstant.TYPE_VIDEO)) {
                ((SupportFragment) getParentFragment()).startWithPop(VideoDetailFragment.newInstance(newsListVo, 0));
            } else {
                ((SupportFragment) getParentFragment()).startWithPop(NewsDetailFragment.newInstance(newsListVo));
            }
        }
    };


    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    public static NewsRecommendFragment newInstance(String dislikeIds, String ctype) {
        NewsRecommendFragment fragment = new NewsRecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.DISLIKE_IDS, dislikeIds);
        bundle.putString(AppConstant.TYPE, ctype);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onGetRecommendNewsListSuccess(List<NewsListVo> newsListVos) {
        mItems.clear();
        List<NewsListVo> list = new ArrayList<>();
        if (newsListVos != null && newsListVos.size() > 0) {
            if (newsListVos.size() > 8) {
                Iterator<NewsListVo> iterator = newsListVos.iterator();
                int index = 0;
                while (iterator.hasNext()) {
                    if (index <= 7) {
                        list.add(iterator.next());
                        index++;
                    } else {
                        break;
                    }
                }
            }
            mItems.add(getString(R.string.tips_relation_recommend));
            list.addAll(newsListVos);
            mItems.addAll(list);
            mMultiTypeAdapter.notifyDataSetChanged();
        }
    }
}
