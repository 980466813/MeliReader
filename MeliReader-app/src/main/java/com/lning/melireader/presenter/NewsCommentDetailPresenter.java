package com.lning.melireader.presenter;

import android.text.TextUtils;

import com.lning.melireader.contact.NewsCommentDetailContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.ReplyVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.model.impl.CommentModel;
import com.lning.melireader.model.impl.LikeModel;
import com.lning.melireader.ui.main.news.comment.NewsCommentDetailFragment;
import com.lning.melireader.ui.main.news.comment.NewsCommentFragment;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/20.
 */

public class NewsCommentDetailPresenter extends BasePresenter<NewsCommentDetailFragment, CommentModel>
        implements NewsCommentDetailContact.Presenter<NewsCommentDetailFragment> {

    @Inject
    LikeModel likeModel;

    @Inject
    public NewsCommentDetailPresenter(CommentModel mMvpModel) {
        super(mMvpModel);
    }


    @Override
    public void addNewsComment(String newsId, final CommentVo replyComment, String content) {
        mMvpView.showDialog("正在请求");
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.dismissDialog();
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.addNewsComment(newsId, replyComment.getId(), content)
                .map(new Function<CommentVo, ReplyVo>() {
                    @Override
                    public ReplyVo apply(CommentVo commentVo) throws Exception {
                        return CommonUtils.parseReplyVo(commentVo, replyComment);
                    }
                })
                .subscribe(new Consumer<ReplyVo>() {
                    @Override
                    public void accept(ReplyVo replyVo) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.showMessage("评论成功");
                        mMvpView.addCommentSuccess(replyVo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void getNewsCommentById(String commentId) {
        setCurRefreshError(false);
        mMvpView.showLoading();
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.showError("当前位置电波无法到达...");
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.getNewsCommentById(commentId)
                .subscribe(new Consumer<CommentVo>() {
                    @Override
                    public void accept(CommentVo commentVo) throws Exception {
                        mMvpView.onGetCommentVoSuccess(commentVo);
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
    public void likeObject(String commentId) {
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
                        mMvpView.dismissDialog();
                        mMvpView.showMessage("点赞成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }
}
