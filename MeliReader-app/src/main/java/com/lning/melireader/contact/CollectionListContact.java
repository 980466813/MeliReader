package com.lning.melireader.contact;

import com.lning.melireader.ui.provider.bean.CollectionPro;

import java.util.List;

/**
 * Created by Ning on 2019/2/13.
 */

public interface CollectionListContact {

    interface View {
        void showCollectionList(String val, List<CollectionPro> historyVos, boolean isCurRefresh);
    }

    interface Presenter<V extends CollectionListContact.View> {

        void initCollectionList(int mode, String val);

        void onLoadMoreCollectionList(int mode, String val, boolean isCurRefresh);

        void deleteCollection(String newsId);

    }
}
