package com.lning.melireader.contact;

import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;

import java.util.List;

/**
 * Created by Ning on 2019/2/22.
 */

public interface VideoDetailContact {

    interface Presenter<V extends View> {

        void checkNewsListCollected(String newsId);

        void collectNews(NewsVo newsVo);

        void updateCollection(String newsId, String tag);

        void getNewsDetailByNewsId(String newsId);

        void addNewsComment(String newsId, String content);

        void cancelAddNewsComment();

        void getAllUserTags();

        void cancelCollectNews(String newsId);

        void likeObject(String newsId);

        void dislikeObject(String newsId);

        void operateAttention(boolean checked, String objectId);
    }

    interface View {

        void onOperateAttentionSuccess(boolean attention);

        void onGetNewsVoSuccess(NewsVo newsVo);

        void onAddNewsCommentSuccess(CommentVo commentVo);

        void onAddNewsCommentError();

        void onOperateCollectNewsSuccess(boolean collected);

        void onUpdateCollectionError();

        void onGetUserTagsSuccess(List<TagPojo> tagPojoList);

        void onDislikeObjectSuccess(boolean isDisliked);

        void onGetCollectedSuccess(boolean collected);
    }

}
