package com.lning.melireader.model.impl;

import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.db.pojo.SearchHistory;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.utils.RxUtils;
import com.lning.melireader.model.ISearchModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Ning on 2019/2/24.
 */

public class SearchModel extends BaseModel
        implements ISearchModel {

    @Inject
    public SearchModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Single<ItemListVo> searchKeywordList(String keyword, String ctype, int page, int rows) {
        return mRepositoryManager.searchKeywords(keyword, ctype, page, rows)
                .compose(RxUtils.<ItemListVo>mappingResultToData());
    }

    @Override
    public Single<List<SearchHistory>> searchHistories() {
        return mRepositoryManager.getAllSearchHistories()
                .compose(RxUtils.<List<SearchHistory>>transformResultToData());
    }

    @Override
    public Single<Boolean> insertSearchHistory(String content) {
        return mRepositoryManager.insertSearchHistory(content)
                .compose(RxUtils.<Boolean>transformResultToData());
    }

    @Override
    public Single<Boolean> clearAllSearchHistories() {
        return mRepositoryManager.clearAllSearchHistories();
    }
}
