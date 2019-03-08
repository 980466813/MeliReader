package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;

import java.util.List;

/**
 * Created by Ning on 2019/2/19.
 */

public interface NewsCommentContact {
    interface Presenter<V extends View> {

        void getNewsCommentsByNewsId(String newsId, boolean isCurRefresh);

        void initNewsCommentsByNewsId(String newsId);

        void likeComment(String commentId);

        void addNewsComment(String newsId, String replyId, String content);
    }

    interface View {

        void onAddNewsCommentError();

        void onGetCommentsSuccess(List<CommentVo> commentVos, boolean isCurRefresh);

        void onGetFullComments(String message);

        void onAddCommentSuccess(CommentVo commentVo);
    }
}
