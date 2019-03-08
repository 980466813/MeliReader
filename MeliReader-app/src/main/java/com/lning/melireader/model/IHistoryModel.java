package com.lning.melireader.model;

import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;

import io.reactivex.Single;

/**
 * Created by Ning on 2019/2/12.
 */

public interface IHistoryModel {

    Single<HistoryVo> insertHistory(NewsListVo newsListVo);

    Single<ItemListVo> getHistoryListByCurUser(String type, boolean isNetworkAvailable, int page, int rows);

    Single<Boolean> clearHistoryList();
}
