package com.lning.melireader.app.constant;

import com.lning.melireader.core.repository.db.pojo.SearchHistory;

import java.util.Comparator;

/**
 * Created by Ning on 2019/2/24.
 */

public class SearchHistoryComparator implements Comparator<SearchHistory> {


    @Override
    public int compare(SearchHistory searchHistory, SearchHistory t1) {
        if (searchHistory != null && t1 != null) {
            return searchHistory.getClick() - t1.getClick();
        }
        return 0;
    }
}
