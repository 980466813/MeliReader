package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.NewsListVo;

import java.util.List;

/**
 * Created by Ning on 2019/2/17.
 */

public interface NewsListContact {

    interface View {
        void onGetNewsListSuccess(List<NewsListVo> newsListVoList, boolean isRefresh);

        void onInsertHistoryNewsListVoSuccess(NewsListVo newsListVo);
    }

    interface Presenter<V extends View> {
        void getNewsListByDislikeIds(String channelIds, String ctype);

        void onLoadMoreNewsListByDislikeIds(String channelIds, String ctype, boolean isRefresh);

        void insertClickHistory(NewsListVo newsListVo);
    }

}
