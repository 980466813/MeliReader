package com.lning.melireader.contact;

import com.lning.melireader.core.repository.db.pojo.SearchHistory;

import java.util.List;

/**
 * Created by Ning on 2019/2/24.
 */

public interface SearchContact {

    interface Presenter<V extends View> {
        void getSearchHistories();

        void clearAllSearchHistories();

        void insertSearchHistory(String content);
    }

    interface View {

        void onGetSearchHistories(List<SearchHistory> histories);

        void jumpToSearchResult(String content);
    }

}
