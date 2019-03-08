package com.lning.melireader.presenter;

import com.lning.melireader.contact.VideoListContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.VideoNewsListVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.model.impl.AttentionModel;
import com.lning.melireader.model.impl.HistoryModel;
import com.lning.melireader.model.impl.NewsModel;
import com.lning.melireader.ui.main.video.list.VideoListFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/22.
 */

public class VideoListPresenter extends BasePresenter<VideoListFragment, NewsModel>
        implements VideoListContact.Presenter<VideoListFragment> {

    private ItemListVo mItemList;

    @Inject
    HistoryModel historyModel;

    @Inject
    AttentionModel attentionModel;

    @Inject
    public VideoListPresenter(NewsModel mMvpModel) {
        super(mMvpModel);
        mItemList = new ItemListVo();
        mItemList.setPage(1);
        mItemList.setLimit(10);
        mItemList.setEnd(false);
    }

    @Override
    public void getVideoNewsListByDislikeIds(String channelIds, String ctype) {
        setCurRefreshError(false);
        mMvpView.showLoading();
        onLoadMoreVideoNewsListByDislikeIds(channelIds, ctype, true);
    }

    @Override
    public void onLoadMoreVideoNewsListByDislikeIds(String channelIds, String ctype, final boolean isCurRefresh) {
        if (isCurRefresh) {
            mItemList.setPage(1);
            mItemList.setLimit(10);
            mItemList.setEnd(false);
        }
        if (mItemList.isEnd()) {
            mMvpView.showMessage("暂无更多新闻");
            mMvpView.dismissDialog();
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
        mMvpModel.addSubscribe(mMvpModel.getVideoNewsListVoByDislikeIds(channelIds, "all",
                mItemList.getPage(), mItemList.getLimit(), 0, true).doOnSuccess(new Consumer<ItemListVo>() {
            @Override
            public void accept(ItemListVo itemListVo) throws Exception {
                mItemList.setPage(itemListVo.getPage() + 1);
                mItemList.setTotalPage(itemListVo.getTotalPage());
                mItemList.setTotalRows(itemListVo.getTotalRows());
                mItemList.setEnd(itemListVo.isEnd());
                mItemList.setFirst(itemListVo.isFirst());
                LogUtils.d(mItemList.toString());
            }
        }).subscribe(new Consumer<ItemListVo>() {
            @Override
            public void accept(ItemListVo itemListVo) throws Exception {
                mMvpView.dismissDialog();
                List<VideoNewsListVo> rows = JsonUtils.jsonToList(itemListVo.getRows().toString()
                        , VideoNewsListVo.class);
                mMvpView.onGetVideoNewsListSuccess(rows, isCurRefresh);
                mMvpView.showMain();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                handlerApiError(throwable);
                mMvpView.onGetListError();
            }
        }));
    }

    @Override
    public void insertHistory(final NewsListVo newsListVo) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        historyModel.addSubscribe(historyModel.insertHistory(newsListVo)
                .subscribe(new Consumer<HistoryVo>() {
                    @Override
                    public void accept(HistoryVo historyVo) throws Exception {
                        mMvpView.onInsertHistoryNewsListVoSuccess(newsListVo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mMvpView.onInsertHistoryNewsListVoSuccess(newsListVo);
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void operateAttention(final boolean attention, String objectId) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpView.showDialog("正在请求");
        attentionModel.addSubscribe(attentionModel.operateAttention(objectId)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.onOperateAttentionSuccess(attention);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }
}
