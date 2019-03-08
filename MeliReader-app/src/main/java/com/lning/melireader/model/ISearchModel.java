package com.lning.melireader.model;

import com.lning.melireader.core.repository.db.pojo.SearchHistory;
import com.lning.melireader.core.repository.http.bean.ItemListVo;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ning on 2019/2/24.
 */

public interface ISearchModel {

    Single<ItemListVo> searchKeywordList(String keyword, String ctype, int page, int rows);

    Single<List<SearchHistory>> searchHistories();

    Single<Boolean> insertSearchHistory(String content);

    Single<Boolean> clearAllSearchHistories();
}
