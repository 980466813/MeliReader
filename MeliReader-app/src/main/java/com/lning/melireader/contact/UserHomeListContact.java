package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.NewsListVo;

/**
 * Created by Ning on 2019/2/24.
 */

public interface UserHomeListContact {

    interface Presenter<V extends View> {

        void getUserHomeList(String userId, String ownerId, String type, boolean isCurRefresh);

        void likeComment(String commentId);

        void insertHistory(NewsListVo newsListVo);
    }

    interface View {

        void onGetListSuccess(String defaultJson, String type);

        void onInsertHistoryNewsListVoSuccess(NewsListVo newsListVo);
    }
}
