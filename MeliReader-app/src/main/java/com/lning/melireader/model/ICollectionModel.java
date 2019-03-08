package com.lning.melireader.model;

import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ning on 2019/2/13.
 */

public interface ICollectionModel {

    Single<Boolean> checkNewsCollected(String newsId);

    Single<ItemListVo> getCollectionList(String ctype, String tag,
                                         boolean isNetworkAvailable, int page, int rows);

    Single<Boolean> updateCollection(String newsId, String tag);

    Single<Boolean> insertCollection(String tag, NewsVo newsVo);

    Single<Boolean> deleteCollection(String newsId);
}
