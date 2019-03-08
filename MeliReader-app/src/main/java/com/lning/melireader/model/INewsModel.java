package com.lning.melireader.model;

import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ning on 2019/2/17.
 */

public interface INewsModel {

    Single<ItemListVo> getNewsListVoByDislikeIds(String dislikeId, String ctype, int page, int rows, int today);

    Single<ItemListVo> getVideoNewsListVoByDislikeIds(String dislikeId, String ctype, int page, int rows, int today, boolean video);

    Single<ItemListVo> getNewsListVoByChannelId(String channelId, String ctype, int page, int rows, int today);

    Single<ItemListVo> getRecommendNewsList(String dislikeIds, String ctype, int page, int rows, int today);

    Single<ItemListVo> getNewsListVoWithCommentByUserId(String userId, int page, int rows);

    Single<ItemListVo> getUserAttentionNewsList(String ctype, int page, int rows, int today);

    Single<NewsVo> getNewsVoByNewsId(String newsId);

}
