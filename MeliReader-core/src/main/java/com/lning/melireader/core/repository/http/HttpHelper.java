package com.lning.melireader.core.repository.http;

import com.lning.melireader.core.repository.http.bean.Result;

import java.util.Date;

import io.reactivex.Single;
import retrofit2.http.Field;

/**
 * Created by Ning on 2018/11/15.
 */

public interface HttpHelper {

    Single<String> getUserInfo(String type, String token);

    Single<String> getUserInfoByUserId(String type, String token, String userId, String ownerId);

    Single<String> updateUserInfo(String userId, String nickname, byte gender, String image, Date birthday, String address, String signature);

    Single<String> login(String username, String password, String mac);

    Single<String> logout(String username, String mac);

    Single<String> register(String username, String password, String nickname, String code);

    Single<String> checkUserExist(String username);

    Single<String> checkCodeCorrect(String username, String code);

    Single<String> sendVerificationCode(String phone);

    Single<String> getHistoryListByUserId(String userId, String ctype, int page, int rows);

    Single<String> deleteHistoryByUserAndNewsId(String userId, String newsId);

    Single<String> deleteHistoryListByUserId(String userId);

    Single<String> deleteHistoryListByCtype(String userId, String ctype);

    Single<String> getCollectionListByUserId(String userId, String ctype, int page, int rows);

    Single<String> getCollectionListByUserIdAndTag(String userId, String tag, int page, int rows);

    Single<String> insertCollection(String userId, String newsId, String tag);

    Single<String> updateCollection(String userId, String newsId, String tag);

    Single<String> cancelCollectNews(String userId, String newsId);

    Single<String> getNewsVoByNewsId(String newsId, String userId);

    Single<String> listNewsListVoByChannelId(String channelId, String userId, String ctype, int page, int rows, int today);

    Single<String> listNewsListVoByDislikeIds(String channels, String userId, String ctype, int page, int rows, int today);

    Single<String> listNewsListVoWithCommentsByUserId(String userId, String ownerId, int page, int rows);

    Single<String> listVideoNewsListVoByDislikeIds(String channels, String userId, String ctype, int page, int rows, int today);

    Single<String> listRecommendNewsList(String dislikeIds, String userId, String ctype, int page, int rows, int today);

    Single<String> listUserAttentionNewsList(String userId, String ctype, int page, int rows, int today);

    Single<String> getNewsCommentsByNewsId(String newsId, String userId, int page, int rows);

    Single<String> addNewsComment(String newsId, String userId, String replyId, String content);

    Single<String> deleteNewsComment(String newsId, String userId, String replyId);

    Single<String> getNewsCommentById(String commentId, String userId);

    Single<String> likeObject(String userId, String objectId);

    Single<String> insertHistory(String userId, String id);

    Single<String> dislikeObject(String userId, String objectId);

    Single<String> searchKeywords(String keywords, String ctype, int page, int rows);

    Single<String> operateAttention(String userId, String objectId);

    Single<String> getDefaultFavouriteChannels();
}
