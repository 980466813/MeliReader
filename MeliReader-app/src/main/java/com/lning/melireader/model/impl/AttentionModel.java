package com.lning.melireader.model.impl;

import android.text.TextUtils;

import com.lning.melireader.core.app.constant.ResponseCode;
import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.core.repository.http.exception.ApiException;
import com.lning.melireader.core.utils.RxUtils;
import com.lning.melireader.model.IAttentionModel;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/25.
 */

public class AttentionModel extends BaseModel
        implements IAttentionModel {

    @Inject
    public AttentionModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Single<Boolean> operateAttention(String objectId) {
        String token = mRepositoryManager.getLoginUserToken();
        String userId = mRepositoryManager.getLoginUserId();
        if (TextUtils.isEmpty(token)) {
            return Single.create(new SingleOnSubscribe<Boolean>() {
                @Override
                public void subscribe(SingleEmitter<Boolean> emitter) throws Exception {
                    emitter.onError(new ApiException(ResponseCode.ERROR.getStatus(), "请先登录"));
                }
            });
        }
        return mRepositoryManager.operateAttention(userId, objectId)
                .map(new Function<Result, Boolean>() {
                    @Override
                    public Boolean apply(Result result) throws Exception {
                        return (Boolean) result.getData();
                    }
                });
    }
}
