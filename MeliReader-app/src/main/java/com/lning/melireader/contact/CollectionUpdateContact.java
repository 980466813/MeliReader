package com.lning.melireader.contact;

import com.lning.melireader.core.repository.db.pojo.TagPojo;

import java.util.List;

/**
 * Created by Ning on 2019/2/16.
 */

public interface CollectionUpdateContact {

    interface Presenter<V extends View> {
        void getUserTagList();

        void insertTagPojo(String tag);

        void updateCollection(String newsId, String tag);
    }

    interface View {
        void showAllUserTagList(List<TagPojo> tagPojos);

        void onUpdateCollectionTagSuccess();

    }
}
