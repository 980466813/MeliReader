package com.lning.melireader.contact;

import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;

import java.util.List;

/**
 * Created by Ning on 2019/2/18.
 */

public interface NewsDetailContact {

    interface View {
        void onGetNewsVoSuccess(NewsVo newsVo);

        void onAddNewsCommentSuccess(CommentVo commentVo);

        void onAddNewsCommentError();

        void onOperateCollectNewsSuccess(boolean collected);

        void onGetCollectedSuccess(boolean collected);

        void onUpdateCollectionError();

        void onGetUserTagsSuccess(List<TagPojo> tagPojoList);

        void onDislikeObjectSuccess(boolean isDisliked);

        void onOperateAttentionSuccess(boolean isAttention);
    }

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
}
