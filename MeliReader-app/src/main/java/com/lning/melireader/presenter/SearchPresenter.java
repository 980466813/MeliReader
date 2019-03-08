package com.lning.melireader.presenter;

import android.text.TextUtils;

import com.lning.melireader.contact.SearchContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.db.pojo.SearchHistory;
import com.lning.melireader.model.impl.SearchModel;
import com.lning.melireader.ui.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/24.
 */

public class SearchPresenter extends BasePresenter<SearchFragment, SearchModel>
        implements SearchContact.Presenter<SearchFragment> {

    @Inject
    public SearchPresenter(SearchModel mMvpModel) {
        super(mMvpModel);
    }


    @Override
    public void getSearchHistories() {
        mMvpModel.addSubscribe(mMvpModel.searchHistories()
                .subscribe(new Consumer<List<SearchHistory>>() {
                    @Override
                    public void accept(List<SearchHistory> searchHistories) throws Exception {
                        mMvpView.onGetSearchHistories(searchHistories);
                    }
                }));
    }

    @Override
    public void clearAllSearchHistories() {
        mMvpModel.addSubscribe(mMvpModel.clearAllSearchHistories()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.onGetSearchHistories(new ArrayList<SearchHistory>());
                    }
                }));
    }

    @Override
    public void insertSearchHistory(final String content) {
        if (TextUtils.isEmpty(content)) {
            mMvpView.showMessage("搜索内容不能为空");
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.insertSearchHistory(content)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.jumpToSearchResult(content);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mMvpView.jumpToSearchResult(content);
                    }
                }));
    }
}
