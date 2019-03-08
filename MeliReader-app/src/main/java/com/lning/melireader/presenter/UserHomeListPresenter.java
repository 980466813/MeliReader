package com.lning.melireader.presenter;

import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.contact.UserHomeListContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.model.impl.CommentModel;
import com.lning.melireader.model.impl.HistoryModel;
import com.lning.melireader.model.impl.LikeModel;
import com.lning.melireader.model.impl.NewsModel;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.main.user.detail.UserHomeListFragment;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/24.
 */

public class UserHomeListPresenter extends BasePresenter<UserHomeListFragment, NewsModel>
        implements UserHomeListContact.Presenter<UserHomeListFragment> {

    @Inject
    HistoryModel historyModel;

    @Inject
    CommentModel commentModel;

    @Inject
    LikeModel likeModel;

    private ItemListVo mItemList;

    @Inject
    public UserHomeListPresenter(NewsModel mMvpModel) {
        super(mMvpModel);
        mItemList = new ItemListVo();
        mItemList.setPage(1);
        mItemList.setLimit(10);
        mItemList.setEnd(false);
    }

    @Override
    public void getUserHomeList(final String userId, String ownerId, final String type, boolean isCurRefresh) {
        if (isCurRefresh) {
            mItemList.setPage(1);
            mItemList.setLimit(10);
            mItemList.setEnd(false);
        }
        if (mItemList.isEnd()) {
            mMvpView.showMessage("暂无更多历史记录");
            return;
        }
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        if (!isCurRefreshError) {
            mMvpView.showLoading();
        }
        mMvpModel.addSubscribe(Single.just(type.equals(AppConstant.COMMENT))
                .flatMap(new Function<Boolean, SingleSource<ItemListVo>>() {
                    @Override
                    public SingleSource<ItemListVo> apply(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            return mMvpModel.getNewsListVoWithCommentByUserId(userId, mItemList.getPage(), mItemList.getLimit())
                                    .doOnSuccess(new Consumer<ItemListVo>() {
                                        @Override
                                        public void accept(ItemListVo itemListVo) throws Exception {
                                            mItemList.setPage(itemListVo.getPage() + 1);
                                            mItemList.setTotalPage(itemListVo.getTotalPage());
                                            mItemList.setTotalRows(itemListVo.getTotalRows());
                                            mItemList.setEnd(itemListVo.isEnd());
                                            mItemList.setFirst(itemListVo.isFirst());
                                        }
                                    });
                        } else {
                            return mMvpModel.getNewsListVoByChannelId(userId, "all", mItemList.getPage(), mItemList.getLimit(), 0)
                                    .doOnSuccess(new Consumer<ItemListVo>() {
                                        @Override
                                        public void accept(ItemListVo itemListVo) throws Exception {
                                            mItemList.setPage(itemListVo.getPage() + 1);
                                            mItemList.setTotalPage(itemListVo.getTotalPage());
                                            mItemList.setTotalRows(itemListVo.getTotalRows());
                                            mItemList.setEnd(itemListVo.isEnd());
                                            mItemList.setFirst(itemListVo.isFirst());
                                        }
                                    });
                        }
                    }
                }).map(new Function<ItemListVo, String>() {
                    @Override
                    public String apply(ItemListVo itemListVo) throws Exception {
                        LogUtils.d(itemListVo.getRows().toString());
                        return itemListVo.getRows().toString();
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.onGetListSuccess(s, type);
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
    public void likeComment(String commentId) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpView.showDialog("正在请求");
        likeModel.addSubscribe(likeModel.likeObject(commentId)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.showMessage("点赞成功");
                        mMvpView.dismissDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
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
}
