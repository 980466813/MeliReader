package com.lning.melireader.ui.search;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.constant.SearchHistoryComparator;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.SearchContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.db.pojo.SearchHistory;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.presenter.SearchPresenter;
import com.lning.melireader.utils.ViewUtils;

import java.util.Collections;
import java.util.List;

import am.widget.wraplayout.WrapLayout;
import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by Ning on 2019/2/24.
 */

public class SearchFragment extends BaseFragment<SearchPresenter>
        implements SearchContact.View {

    @BindView(R.id.search_history_wrap_wl)
    WrapLayout searchHistoryWrapWl;

    @BindView(R.id.search_history_keyword_et)
    AppCompatEditText searchHistoryKeywordEt;

    @BindView(R.id.search_history_clear_btn)
    AppCompatButton searchHistoryClearBtn;
    private String keyword;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        initBasicData();
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        keyword = bundle.getString(AppConstant.KEYWORD, "");
        searchHistoryKeywordEt.setText(keyword);
        setSwipeBackEnable(true);
        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_LEFT);
        mPresenter.getSearchHistories();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onGetSearchHistories(List<SearchHistory> histories) {
        if (histories.size() > 0) {
            Collections.sort(histories, new SearchHistoryComparator());
            for (int i = 0; i < histories.size(); i++) {
                SearchHistory searchHistory = histories.get(i);
                searchHistoryWrapWl.addView(ViewUtils.makeTagView(getContext(), null, searchHistory.getKeywords(), simpleItemClickListenerAdapter));
            }
        } else {
            searchHistoryClearBtn.setVisibility(View.GONE);
            searchHistoryWrapWl.setVisibility(View.GONE);
        }
    }

    @Override
    public void jumpToSearchResult(String content) {
        startWithPop(SearchResultFragment.newInstance(content));
    }

    @OnClick(R.id.toolbar_right_btn)
    public void onSearchClick() {
        String content = searchHistoryKeywordEt.getText().toString();
        mPresenter.insertSearchHistory(content);
    }

    @OnClick(R.id.search_history_clear_btn)
    public void onClearClick() {
        mPresenter.clearAllSearchHistories();
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    public static SearchFragment newInstance(String keyword) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.KEYWORD, keyword);
        fragment.setArguments(bundle);
        return fragment;
    }

    SimpleItemClickListenerAdapter simpleItemClickListenerAdapter = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, Object object) {
            if (object instanceof String) {
                searchHistoryKeywordEt.setText(object.toString());
                mPresenter.insertSearchHistory((String) object);
            }
        }
    };
}
