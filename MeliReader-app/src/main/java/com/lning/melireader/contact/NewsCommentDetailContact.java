package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.ReplyVo;

/**
 * Created by Ning on 2019/2/20.
 */

public interface NewsCommentDetailContact {

    interface Presenter<V extends View> {
        void addNewsComment(String newsId, CommentVo replyVo, String content);

        void getNewsCommentById(String commentId);

        void likeObject(String commentId);
    }

    interface View {
        void onGetCommentVoSuccess(CommentVo commentVo);

        void addCommentSuccess(ReplyVo replyVo);
    }

}
