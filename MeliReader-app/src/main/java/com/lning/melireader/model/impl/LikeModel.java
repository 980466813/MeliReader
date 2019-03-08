package com.lning.melireader.model.impl;

import android.text.TextUtils;

import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.repository.http.exception.ApiException;
import com.lning.melireader.core.utils.RxUtils;
import com.lning.melireader.model.ILikeModel;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/21.
 */


public class LikeModel extends BaseModel
        implements ILikeModel {

    @Inject
    public LikeModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Single<Boolean> likeObject(final String objectId) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                final String userId = mRepositoryManager.getLoginUserId();
                if (!TextUtils.isEmpty(userId)) {
                    emitter.onSuccess(userId);
                } else {
                    String token = mRepositoryManager.getLoginUserToken();
                    token = !TextUtils.isEmpty(token) ? token : mRepositoryManager.getLocalPhoneMac();
                    UserVo userVo = mRepositoryManager.getLocalUserInfo("basic", token);
                    if (userVo != null) {
                        mRepositoryManager.saveLoginUserId(userVo.getId());
                        emitter.onSuccess(userVo.getId());
                    } else {
                        throw new ApiException(0000, "发生未知错误");
                    }
                }
            }
        }).flatMap(new Function<String, SingleSource<? extends Boolean>>() {
            @Override
            public SingleSource<? extends Boolean> apply(String s) throws Exception {
                return mRepositoryManager.likeObject(s, objectId)
                        .compose(RxUtils.mappingResultToCheck());
            }
        });
    }

    public Single<Boolean> dislikeObject(final String newsId) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                final String userId = mRepositoryManager.getLoginUserId();
                if (!TextUtils.isEmpty(userId)) {
                    emitter.onSuccess(userId);
                } else {
                    String token = mRepositoryManager.getLoginUserToken();
                    token = !TextUtils.isEmpty(token) ? token : mRepositoryManager.getLocalPhoneMac();
                    UserVo userVo = mRepositoryManager.getLocalUserInfo("basic", token);
                    if (userVo != null) {
                        mRepositoryManager.saveLoginUserId(userVo.getId());
                        emitter.onSuccess(userVo.getId());
                    } else {
                        throw new ApiException(0000, "发生未知错误");
                    }
                }
            }
        }).flatMap(new Function<String, SingleSource<? extends Boolean>>() {
            @Override
            public SingleSource<? extends Boolean> apply(String s) throws Exception {
                return mRepositoryManager.dislikeObject(s, newsId)
                        .compose(RxUtils.<Boolean>mappingResultToData());
            }
        });
    }
}
