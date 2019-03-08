package com.lning.melireader.presenter;

import com.lning.melireader.contact.HistoryListContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.model.impl.HistoryModel;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.main.user.history.HistoryListFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/13.
 */

public class HistoryListPresenter extends BasePresenter<HistoryListFragment, HistoryModel>
        implements HistoryListContact.Presenter<HistoryListFragment> {

    private ItemListVo mItemList;

    @Inject
    UserModel userModel;

    @Inject
    public HistoryListPresenter(HistoryModel mMvpModel) {
        super(mMvpModel);
        mItemList = new ItemListVo();
    }

    @Override
    public void initHistoryList(String type) {
        isCurRefreshError = false;
        mMvpView.showLoading();
        onLoadMoreHistoryList(type, true);
    }

    @Override
    public void onLoadMoreHistoryList(final String type, final boolean isRefresh) {
        if (isRefresh) {
            mItemList.setPage(1);
            mItemList.setLimit(10);
            mItemList.setEnd(false);
        }
        if (mItemList.isEnd()) {
            mMvpView.showMessage("暂无更多历史记录");
            mMvpView.dismissDialog();
            return;
        }
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
        }
        int limit = mItemList.getLimit();
        mItemList.setLimit(limit <= 0 || limit >= 30 ? 10 : limit);
        mMvpModel.addSubscribe(mMvpModel.getHistoryListByCurUser(type, isNetworkAvailable,
                mItemList.getPage(), mItemList.getLimit())
                .doOnSuccess(new Consumer<ItemListVo>() {
                    @Override
                    public void accept(ItemListVo itemListVo) throws Exception {
                        LogUtils.d(itemListVo.toString());
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
                        List<HistoryVo> rows = JsonUtils.jsonToList(JsonUtils.objectToJson(itemListVo.getRows()), HistoryVo.class);
                        mMvpView.showHistoryList(rows, isRefresh);
                        mMvpView.showMain();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void clearHistoryList() {
        mMvpModel.addSubscribe(mMvpModel.clearHistoryList()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.showError("暂无更多历史记录");
                        mMvpView.showMessage("删除历史记录成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }
}
