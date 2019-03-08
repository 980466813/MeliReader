package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.HistoryVo;

import java.util.List;

/**
 * Created by Ning on 2019/2/12.
 */

public interface HistoryListContact {

    interface View {
        void showHistoryList(List<HistoryVo> historyVos, boolean b);
    }

    interface Presenter<V extends View> {

        void initHistoryList(String type);

        void onLoadMoreHistoryList(String type, boolean isCurRefresh);

        void clearHistoryList();
    }
}
