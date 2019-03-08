package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.VideoNewsListVo;

import java.util.List;

/**
 * Created by Ning on 2019/2/22.
 */

public interface VideoListContact {

    interface Presenter<V extends View> {
        void getVideoNewsListByDislikeIds(String channelIds, String ctype);

        void onLoadMoreVideoNewsListByDislikeIds(String channelIds, String ctype, boolean isRefresh);

        void insertHistory(NewsListVo newsListVo);

        void operateAttention(boolean attention, String objectId);
    }

    interface View {
        void onGetVideoNewsListSuccess(List<VideoNewsListVo> newsListVoList, boolean isRefresh);

        void onInsertHistoryNewsListVoSuccess(NewsListVo newsListVo);

        void onOperateAttentionSuccess(boolean attention);
    }
}
