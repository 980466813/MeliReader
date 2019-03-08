package com.lning.melireader.ui.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.event.TabSelectedEvent;
import com.lning.melireader.contact.SearchContact;
import com.lning.melireader.core.repository.db.pojo.SearchHistory;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.ui.adapter.SearchResultTabAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ning on 2019/2/24.
 */
public class SearchResultFragment extends BaseFragment
        implements OnLoadMoreListener, OnRefreshListener {
    public enum Item {
        AllFragment("综合", "all"),
        ArticleFragment("文章", "news"),
        VideoFragment("视频", "video"),
        ChannelFragment("Meli号", "channel");

        public String title;

        public String ctype;

        Item(String title, String ctype) {
            this.title = title;
            this.ctype = ctype;
        }

    }

    @BindView(R.id.search_result_pager_vg)
    ViewPager searchResultPagerVg;

    @BindView(R.id.search_result_tab_tl)
    TabLayout searchResultTabTl;

    @BindView(R.id.search_result_keyword_tv)
    AppCompatTextView searchResultKeywordTv;

    @BindView(R.id.search_result_refresh_srl)
    SmartRefreshLayout searchResultRefreshSrl;

    private SearchResultTabAdapter mAdapter;

    private String keyword;

    public static SearchResultFragment newInstance(String content) {
        SearchResultFragment fragment = new SearchResultFragment();
        LogUtils.d(content);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.KEYWORD, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        Bundle bundle = getArguments();
        keyword = bundle.getString(AppConstant.KEYWORD, "");
        searchResultKeywordTv.setText(keyword);
        mAdapter = new SearchResultTabAdapter(getChildFragmentManager(), Item.values(), keyword);
        searchResultPagerVg.setOffscreenPageLimit(Item.values().length / 2);
        searchResultPagerVg.setAdapter(mAdapter);
        searchResultTabTl.setupWithViewPager(searchResultPagerVg, true);
        searchResultTabTl.setTabTextColors(getResources().getColor(R.color.md_grey_666), getResources().getColor(R.color.md_selected_blue));
        searchResultTabTl.setSelectedTabIndicatorColor(getResources().getColor(R.color.md_selected_blue));
        searchResultRefreshSrl.setOnLoadMoreListener(this);
        searchResultRefreshSrl.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        mAdapter.getCurrentItem().onLoadMore(refreshLayout);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mAdapter.getCurrentItem().onRefresh(refreshLayout);
    }

    @OnClick(R.id.search_result_keyword_tv)
    public void onKeywordTvClick() {
        startWithPop(SearchFragment.newInstance(keyword));
    }

    @OnClick(R.id.toolbar_left_btn)
    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }
}
