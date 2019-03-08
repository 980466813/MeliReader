package com.lning.melireader.contact;

import java.util.List;

/**
 * Created by Ning on 2019/2/24.
 */

public interface SearchResultListContact {
    interface View {
        void onGetSearchKeywordList(String json, boolean isCurRefresh);
    }

    interface Presenter<V extends View> {

        void initSearchKeyword(String keyword, String ctype);

        void onLoadMoreSearchList(String keyword, String ctype, boolean isCurRefresh);
    }
}
