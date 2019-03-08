package com.lning.melireader.model;

import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;

import io.reactivex.Single;

/**
 * Created by Ning on 2019/2/19.
 */

public interface ICommentModel {

    Single<ItemListVo> getNewsComments(String newsId, int page, int rows);

    Single<CommentVo> getNewsCommentById(String commentId);

    Single<CommentVo> addNewsComment(String newsId, String replyId, String content);

    Single<Boolean> deleteNewsComment(String newsId, String replyId);

}



