package com.lning.melireader.model.impl;

import android.text.TextUtils;

import com.lning.melireader.core.app.constant.ResponseCode;
import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.exception.ApiException;
import com.lning.melireader.core.utils.RxUtils;
import com.lning.melireader.model.ICommentModel;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by Ning on 2019/2/19.
 */

public class CommentModel extends BaseModel
        implements ICommentModel {

    @Inject
    public CommentModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Single<ItemListVo> getNewsComments(String newsId, int page, int rows) {
        String token = mRepositoryManager.getLoginUserToken();
        String userId = mRepositoryManager.getLoginUserId();
        if (TextUtils.isEmpty(token)) {
            userId = "";
        }
        return mRepositoryManager.getNewsCommentsByNewsId(newsId, userId, page, rows)
                .compose(RxUtils.<ItemListVo>mappingResultToData());
    }

    @Override
    public Single<CommentVo> getNewsCommentById(String commentId) {
        String userId = mRepositoryManager.getLoginUserId();
        String token = mRepositoryManager.getLoginUserToken();
        if (TextUtils.isEmpty(token)) {
            userId = "";
        }
        return mRepositoryManager.getNewsCommentById(commentId, userId)
                .compose(RxUtils.<CommentVo>mappingResultToData());
    }

    @Override
    public Single<CommentVo> addNewsComment(String newsId, String replyId, String content) {
        String token = mRepositoryManager.getLoginUserToken();
        String userId = mRepositoryManager.getLoginUserId();
        if (TextUtils.isEmpty(token)) {
            return Single.create(new SingleOnSubscribe<CommentVo>() {
                @Override
                public void subscribe(SingleEmitter<CommentVo> emitter) throws Exception {
                    emitter.onError(new ApiException(ResponseCode.ERROR.getStatus(), "请先登录"));
                }
            });
        }
        return mRepositoryManager.addNewsComment(newsId, userId, replyId, content)
                .compose(RxUtils.<CommentVo>mappingResultToData());
    }

    @Override
    public Single<Boolean> deleteNewsComment(String newsId, String replyId) {
        return null;
    }
}
