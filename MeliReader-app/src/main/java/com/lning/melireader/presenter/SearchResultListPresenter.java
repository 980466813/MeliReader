package com.lning.melireader.presenter;

import android.text.TextUtils;

import com.lning.melireader.contact.SearchContact;
import com.lning.melireader.contact.SearchResultListContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.model.impl.SearchModel;
import com.lning.melireader.ui.search.list.SearchResultListFragment;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/24.
 */

public class SearchResultListPresenter extends BasePresenter<SearchResultListFragment, SearchModel>
        implements SearchResultListContact.Presenter<SearchResultListFragment> {

    ItemListVo mItemList;

    @Inject
    public SearchResultListPresenter(SearchModel mMvpModel) {
        super(mMvpModel);
        mItemList = new ItemListVo();
        mItemList.setPage(1);
        mItemList.setLimit(10);
        mItemList.setEnd(false);
    }

    @Override
    public void initSearchKeyword(String keyword, String ctype) {
        setCurRefreshError(false);
        mMvpView.showLoading();
        onLoadMoreSearchList(keyword, ctype, true);
    }

    @Override
    public void onLoadMoreSearchList(String keyword, String ctype, final boolean isCurRefresh) {
        if (isCurRefresh) {
            mItemList.setPage(1);
            mItemList.setLimit(10);
            mItemList.setEnd(false);
        }
        if (mItemList.isEnd()) {
            mMvpView.showMessage("暂无更多新闻");
            return;
        }
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.dismissDialog();
            mMvpView.showMain();
            return;
        }
        int limit = mItemList.getLimit();
        mItemList.setLimit(limit <= 0 || limit >= 30 ? 10 : limit);
        mMvpModel.addSubscribe(mMvpModel.searchKeywordList(keyword, ctype, mItemList.getPage(), mItemList.getLimit())
                .doOnSuccess(new Consumer<ItemListVo>() {
                    @Override
                    public void accept(ItemListVo itemListVo) throws Exception {
                        mItemList.setPage(itemListVo.getPage() + 1);
                        mItemList.setTotalPage(itemListVo.getTotalPage());
                        mItemList.setTotalRows(itemListVo.getTotalRows());
                        mItemList.setEnd(itemListVo.isEnd());
                        mItemList.setFirst(itemListVo.isFirst());
                    }
                })
                .subscribe(new Consumer<ItemListVo>() {
                    @Override
                    public void accept(ItemListVo itemListVo) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.onGetSearchKeywordList(itemListVo.getRows().toString(), isCurRefresh);
                        mMvpView.showMain();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }
}
