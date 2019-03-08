package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.VideoNewsListVo;

import java.util.List;

/**
 * Created by Ning on 2019/2/27.
 */

public interface AttentionCenterContact {

    interface Presenter<V extends View> {
        void getAttentionNewsListByUserId(String ctype);

        void onLoadMoreAttentionNewsListByUserId(String ctype, boolean isRefresh);

        void insertHistory(NewsListVo newsListVo);

        void operateAttention(boolean attention, String objectId);
    }

    interface View {
        void onGetNewsListSuccess(List<NewsListVo> newsListVoList, boolean isRefresh);

        void onInsertHistoryNewsListVoSuccess(NewsListVo newsListVo);

        void onOperateAttentionSuccess(boolean attention);
    }
}
