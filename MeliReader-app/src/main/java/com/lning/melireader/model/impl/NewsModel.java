package com.lning.melireader.model.impl;

import android.text.TextUtils;

import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.core.utils.RxUtils;
import com.lning.melireader.model.INewsModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/17.
 */

public class NewsModel extends BaseModel
        implements INewsModel {

    @Inject
    public NewsModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Single<ItemListVo> getNewsListVoByDislikeIds(String dislikeId, String ctype, int page, int rows, int today) {
        return getVideoNewsListVoByDislikeIds(dislikeId, ctype, page, rows, today, false);
    }

    @Override
    public Single<ItemListVo> getVideoNewsListVoByDislikeIds(final String dislikeId, final String ctype, final int page, final int rows, final int today, boolean video) {
        String userId = mRepositoryManager.getLoginUserId();
        String token = mRepositoryManager.getLoginUserToken();
        if (TextUtils.isEmpty(token)) {
            userId = "";
        }
        final String finalUserId = userId;
        return Single.just(video)
                .flatMap(new Function<Boolean, SingleSource<Result>>() {
                    @Override
                    public SingleSource<Result> apply(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            return mRepositoryManager.getVideoNewsListVoByDislikeIds(dislikeId, finalUserId, ctype, page, rows, today);
                        }
                        return mRepositoryManager.getNewsListVoByDislikeIds(dislikeId, finalUserId, ctype, page, rows, today);
                    }
                }).compose(RxUtils.<ItemListVo>mappingResultToData());
    }

    @Override
    public Single<ItemListVo> getNewsListVoByChannelId(String channelId, String ctype, int page, int rows, int today) {
        String userId = mRepositoryManager.getLoginUserId();
        String token = mRepositoryManager.getLoginUserToken();
        if (TextUtils.isEmpty(token)) {
            userId = "";
        }
        return mRepositoryManager.getNewsListVoByChannelId(channelId, userId, ctype, page, rows, today)
                .compose(RxUtils.<ItemListVo>mappingResultToData());
    }

    @Override
    public Single<ItemListVo> getRecommendNewsList(String dislikeIds, String ctype, int page, int rows, int today) {
        String userId = mRepositoryManager.getLoginUserId();
        String token = mRepositoryManager.getLoginUserToken();
        if (TextUtils.isEmpty(token)) {
            userId = "";
        }
        return mRepositoryManager.getRecommendNewsList(dislikeIds, userId, ctype, page, rows, today)
                .compose(RxUtils.<ItemListVo>mappingResultToData());
    }

    @Override
    public Single<ItemListVo> getNewsListVoWithCommentByUserId(String userId, int page, int rows) {
        String ownerId = mRepositoryManager.getLoginUserId();
        return mRepositoryManager.getNewsListVoWithCommentByUserId(userId, ownerId, page, rows)
                .compose(RxUtils.<ItemListVo>mappingResultToData());
    }

    @Override
    public Single<ItemListVo> getUserAttentionNewsList(String ctype, int page, int rows, int today) {
        String userId = mRepositoryManager.getLoginUserId();
        String token = mRepositoryManager.getLoginUserToken();
        if (TextUtils.isEmpty(token)) {
            userId = "";
        }
        return mRepositoryManager.getUserAttentionNewsList(userId, ctype, page, rows, today)
                .compose(RxUtils.<ItemListVo>mappingResultToData());
    }

    @Override
    public Single<NewsVo> getNewsVoByNewsId(String newsId) {
        String userId = mRepositoryManager.getLoginUserId();
        String token = mRepositoryManager.getLoginUserToken();
        if (TextUtils.isEmpty(token)) {
            userId = "";
        }
        return mRepositoryManager.getNewsVoByNewsId(newsId, userId)
                .compose(RxUtils.<NewsVo>mappingResultToData());
    }
}
