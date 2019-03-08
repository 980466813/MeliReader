package com.lning.melireader.model.impl;

import android.text.TextUtils;

import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.repository.http.exception.ApiException;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.RxUtils;
import com.lning.melireader.model.ICollectionModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/13.
 */

public class CollectionModel extends BaseModel
        implements ICollectionModel {

    @Inject
    public CollectionModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Single<Boolean> checkNewsCollected(final String newsId) {

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
                return mRepositoryManager.getCollection(s, newsId)
                        .map(new Function<Result, Boolean>() {
                            @Override
                            public Boolean apply(Result result) throws Exception {
                                return result.getStatus() == 2000;
                            }
                        });
            }
        });
    }

    @Override
    public Single<ItemListVo> getCollectionList(final String ctype, final String tag, final boolean isNetworkAvailable, final int page, final int rows) {
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
                                                emitter.onSuccess(userVo.getId());
                                            }
                                        });
                            }
                        }).flatMap(new Function<String, SingleSource<Result>>() {
                            @Override
                            public SingleSource<Result> apply(String s) throws Exception {
                                if (isNetworkAvailable && !TextUtils.isEmpty(token)) {
                                    if (!TextUtils.isEmpty(ctype) || "all".equals(ctype))
                                        return mRepositoryManager.getCollectionListByUserIdAndCtype(s, ctype, page, rows);
                                    else
                                        return mRepositoryManager.getCollectionListByUserIdAndTag(s, tag, page, rows);
                                } else {
                                    return mRepositoryManager.getLocalCollectionListByUserId(s, ctype, tag, page, rows);
                                }
                            }
                        }).compose(RxUtils.<ItemListVo>transformResultToData());
                    }
                });
    }

    @Override
    public Single<Boolean> updateCollection(final String newsId, final String tag) {
        String userId = mRepositoryManager.getLoginUserId();
        final String token = mRepositoryManager.getLoginUserToken();
        if (!TextUtils.isEmpty(userId)) {
            if (TextUtils.isEmpty(token)) {
                return mRepositoryManager.updateLocalCollection(userId, newsId, tag)
                        .compose(RxUtils.mappingResultToCheck());
            } else {
                return mRepositoryManager.updateCollection(userId, newsId, tag)
                        .compose(RxUtils.mappingResultToCheck());
            }
        } else {
            return Single.just(!TextUtils.isEmpty(token) ? token : mRepositoryManager.getLocalPhoneMac())
                    .flatMap(new Function<String, SingleSource<Boolean>>() {
                        @Override
                        public SingleSource<Boolean> apply(final String s) throws Exception {
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
                            }).flatMap(new Function<String, SingleSource<Boolean>>() {
                                @Override
                                public SingleSource<Boolean> apply(String s) throws Exception {
                                    if (!TextUtils.isEmpty(token)) {
                                        return mRepositoryManager.updateCollection(s, newsId, tag)
                                                .compose(RxUtils.mappingResultToCheck());
                                    } else {
                                        return mRepositoryManager.updateLocalCollection(s, newsId, tag)
                                                .compose(RxUtils.mappingResultToCheck());
                                    }
                                }
                            });
                        }
                    });
        }
    }

    @Override
    public Single<Boolean> insertCollection(final String tag, final NewsVo newsVo) {
        final String token = mRepositoryManager.getLoginUserToken();
        return Single.just(!TextUtils.isEmpty(token) ? token : mRepositoryManager.getLocalPhoneMac())
                .flatMap(new Function<String, SingleSource<Boolean>>() {
                    @Override
                    public SingleSource<Boolean> apply(final String s) throws Exception {
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
                        }).flatMap(new Function<String, SingleSource<Boolean>>() {
                            @Override
                            public SingleSource<Boolean> apply(String s) throws Exception {
                                if (!TextUtils.isEmpty(token)) {
                                    return mRepositoryManager.insertCollection(s, tag, newsVo)
                                            .compose(RxUtils.mappingResultToCheck());
                                } else {
                                    return mRepositoryManager.insertLocalCollection(s, tag, CommonUtils.parseNewsListVo(newsVo))
                                            .compose(RxUtils.mappingResultToCheck());
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public Single<Boolean> deleteCollection(final String newsId) {
        final String token = mRepositoryManager.getLoginUserToken();
        return Single.just(!TextUtils.isEmpty(token) ? token : mRepositoryManager.getLocalPhoneMac())
                .flatMap(new Function<String, SingleSource<Boolean>>() {
                    @Override
                    public SingleSource<Boolean> apply(final String s) throws Exception {
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
                        }).flatMap(new Function<String, SingleSource<Boolean>>() {
                            @Override
                            public SingleSource<Boolean> apply(String s) throws Exception {
                                if (!TextUtils.isEmpty(token)) {
                                    return mRepositoryManager.deleteCollection(s, newsId)
                                            .compose(RxUtils.mappingResultToCheck());
                                } else {
                                    return mRepositoryManager.deleteLocalCollection(s, newsId)
                                            .compose(RxUtils.mappingResultToCheck());
                                }
                            }
                        });
                    }
                });
    }


}