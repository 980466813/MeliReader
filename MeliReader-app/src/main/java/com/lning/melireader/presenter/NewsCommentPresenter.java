package com.lning.melireader.presenter;

import com.lning.melireader.contact.NewsCommentContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.exception.ApiException;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.model.impl.CommentModel;
import com.lning.melireader.model.impl.LikeModel;
import com.lning.melireader.model.impl.NewsModel;
import com.lning.melireader.ui.main.news.comment.NewsCommentFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/19.
 */

public class NewsCommentPresenter extends BasePresenter<NewsCommentFragment, CommentModel>
        implements NewsCommentContact.Presenter<NewsCommentFragment> {

    @Inject
    NewsModel newsModel;

    @Inject
    LikeModel likeModel;

    private ItemListVo mItemList;

    @Inject
    public NewsCommentPresenter(CommentModel mMvpModel) {
        super(mMvpModel);
        mItemList = new ItemListVo();
        mItemList.setEnd(false);
        mItemList.setPage(1);
        mItemList.setLimit(10);
    }


    @Override
    public void initNewsCommentsByNewsId(String newsId) {
        setCurRefreshError(false);
        mMvpView.showLoading();
        getNewsCommentsByNewsId(newsId, true);
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
    public void addNewsComment(String newsId, String replyId, String content) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.addNewsComment(newsId, replyId, content)
                .subscribe(new Consumer<CommentVo>() {
                    @Override
                    public void accept(CommentVo commentVo) throws Exception {
                        mMvpView.onAddCommentSuccess(commentVo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
//                        if (throwable instanceof ApiException) {
//                            ApiException exception = (ApiException) throwable;
//                            if ("已加载全部评论".equals(exception.getMessage())) {
//                                mMvpView.onGetFullComments(exception.getMessage());
//                            }
//                        }
                    }
                }));
    }

    @Override
    public void getNewsCommentsByNewsId(String newsId, final boolean isCurRefresh) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.dismissDialog();
            return;
        }
        if (isCurRefresh) {
            mItemList.setPage(1);
            mItemList.setLimit(6);
            mItemList.setEnd(false);
        }
        mMvpModel.addSubscribe(mMvpModel.getNewsComments(newsId, mItemList.getPage(), mItemList.getLimit())
                .subscribe(new Consumer<ItemListVo>() {
                    @Override
                    public void accept(ItemListVo itemListVo) throws Exception {
                        mItemList.setPage(itemListVo.getPage() + 1);
                        mItemList.setTotalPage(itemListVo.getTotalPage());
                        mItemList.setTotalRows(itemListVo.getTotalRows());
                        mItemList.setEnd(itemListVo.isEnd());
                        mItemList.setFirst(itemListVo.isFirst());
                        mMvpView.dismissDialog();
//                        LogUtils.d(itemListVo.getRows().toString());
                        List<CommentVo> rows = JsonUtils.jsonToList(itemListVo.getRows().toString(), CommentVo.class);
                        mMvpView.onGetCommentsSuccess(rows, isCurRefresh);
                        mMvpView.showMain();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                        if (throwable instanceof ApiException) {
                            ApiException exception = (ApiException) throwable;
                            if ("已加载全部评论".equals(exception.getMessage())) {
                                mMvpView.onGetFullComments(exception.getMessage());
                            }
                        }
                    }
                }));
    }
}
