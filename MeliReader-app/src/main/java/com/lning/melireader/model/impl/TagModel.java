package com.lning.melireader.model.impl;

import android.text.TextUtils;

import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.utils.RxUtils;
import com.lning.melireader.model.ITagModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/16.
 */

public class TagModel extends BaseModel
        implements ITagModel {

    @Inject
    public TagModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Single<List<TagPojo>> getUserTagList() {
        String userId = mRepositoryManager.getLoginUserId();
        if (!TextUtils.isEmpty(userId)) {
            return mRepositoryManager.getUserTags(userId)
                    .compose(RxUtils.<TagPojo>mappingResultToList());
        } else {
            final String token = mRepositoryManager.getLoginUserToken();
            return Single.just(!TextUtils.isEmpty(token) ? token : mRepositoryManager.getLocalPhoneMac())
                    .flatMap(new Function<String, SingleSource<List<TagPojo>>>() {
                        @Override
                        public SingleSource<List<TagPojo>> apply(String s) throws Exception {
                            return Single.create(new SingleOnSubscribe<String>() {
                                @Override
                                public void subscribe(final SingleEmitter<String> emitter) throws Exception {
                                    mRepositoryManager.getUserInfoFromLocal("basic", token)
                                            .subscribe(new Consumer<Result>() {
                                                @Override
                                                public void accept(Result result) throws Exception {
                                                    UserVo userVo = (UserVo) result.getData();
                                                    emitter.onSuccess(userVo.getId());
                                                    mRepositoryManager.saveLoginUserId(userVo.getId());
                                                }
                                            });
                                }
                            }).flatMap(new Function<String, SingleSource<List<TagPojo>>>() {
                                @Override
                                public SingleSource<List<TagPojo>> apply(String s) throws Exception {
                                    return mRepositoryManager.getUserTags(s)
                                            .compose(RxUtils.<TagPojo>mappingResultToList());
                                }
                            });
                        }
                    });
        }
    }

    @Override
    public Single<Boolean> insertTagPojo(final String tag) {
        String userId = mRepositoryManager.getLoginUserId();
        final String token = mRepositoryManager.getLoginUserToken();
        if (!TextUtils.isEmpty(userId)) {
            return mRepositoryManager.insertTagPojo(userId, tag)
                    .compose(RxUtils.mappingResultToCheck());
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
                                                    emitter.onSuccess(userVo.getId());
                                                    mRepositoryManager.saveLoginUserId(userVo.getId());
                                                }
                                            });
                                }
                            }).flatMap(new Function<String, SingleSource<Boolean>>() {
                                @Override
                                public SingleSource<Boolean> apply(String s) throws Exception {
                                    return mRepositoryManager.insertTagPojo(s, tag)
                                            .compose(RxUtils.mappingResultToCheck());
                                }
                            });
                        }
                    });
        }
    }
}
