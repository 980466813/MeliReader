package com.lning.melireader.model.impl;

import android.text.TextUtils;

import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.repository.http.exception.ApiException;
import com.lning.melireader.core.utils.RandomUtils;
import com.lning.melireader.core.utils.RxUtils;
import com.lning.melireader.model.IHistoryModel;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/12.
 */

public class HistoryModel extends BaseModel
        implements IHistoryModel {

    @Inject
    public HistoryModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Single<HistoryVo> insertHistory(final NewsListVo newsListVo) {
        final String token = mRepositoryManager.getLoginUserToken();
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
        }).flatMap(new Function<String, SingleSource<Result>>() {
            @Override
            public SingleSource<Result> apply(String s) throws Exception {
                if (!TextUtils.isEmpty(token)) {
                    return mRepositoryManager.insertHistory(s, newsListVo);
                } else {
                    return mRepositoryManager.insertLocalHistory(s, newsListVo);
                }
            }
        }).compose(RxUtils.<HistoryVo>transformResultToData());
    }

    @Override
    public Single<ItemListVo> getHistoryListByCurUser(final String type, final boolean isNetworkAvailable, final int page, final int rows) {
        final String token = mRepositoryManager.getLoginUserToken();
        return Single.just(!TextUtils.isEmpty(token) ? token : mRepositoryManager.getLocalPhoneMac())
                .flatMap(new Function<String, SingleSource<ItemListVo>>() {
                    @Override
                    public SingleSource<ItemListVo> apply(final String s) throws Exception {
                        return Single.create(new SingleOnSubscribe<String>() {
                            @Override
                            public void subscribe(final SingleEmitter<String> emitter) throws Exception {
                                mRepositoryManager.getUserInfoFromLocal("basic", s)
                                        .subscribe(new Consumer<Result>() {
                                            @Override
                                            public void accept(Result result) throws Exception {
                                                UserVo userVo = (UserVo) result.getData();
                                                mRepositoryManager.saveLoginUserId(userVo.getId());
                                                emitter.onSuccess(userVo.getId());
                                            }
                                        });
                            }
                        }).flatMap(new Function<String, SingleSource<Result>>() {
                            @Override
                            public SingleSource<Result> apply(String s) throws Exception {
                                if (isNetworkAvailable && !TextUtils.isEmpty(token)) {
                                    return mRepositoryManager.getHistoryListByUserId(s, type, page, rows);
                                } else {
                                    return mRepositoryManager.getLocalHistoryListByUserId(s, type, page, rows);
                                }
                            }
                        }).compose(RxUtils.<ItemListVo>transformResultToData());
                    }
                });
    }


    @Override
    public Single<Boolean> clearHistoryList() {
        final String token = mRepositoryManager.getLoginUserToken();
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
        }).flatMap(new Function<String, SingleSource<Result>>() {
            @Override
            public SingleSource<Result> apply(String s) throws Exception {
                if (!TextUtils.isEmpty(token)) {
                    return mRepositoryManager.deleteHistoryListByUserId(s);
                } else {
                    return mRepositoryManager.deleteLocalHistoryList(s);
                }
            }
        }).compose(RxUtils.mappingResultToCheck());
    }
}
