package com.lning.melireader.core.repository.http;

import com.lning.melireader.core.repository.http.service.ApiService;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by Ning on 2018/11/15.
 */
@Singleton
public class HttpHelperImpl implements HttpHelper {


    private final ApiService mApiService;

    @Inject
    public HttpHelperImpl(ApiService apiService) {
        this.mApiService = apiService;
    }


    @Override
    public Single<String> getUserInfo(String type, String token) {
        return mApiService.getUserInfo(type, token);
    }

    @Override
    public Single<String> getUserInfoByUserId(String type, String token, String userId, String ownerId) {
        return mApiService.getUserInfoByUserId(type, token, userId, ownerId);
    }

    @Override
    public Single<String> updateUserInfo(String userId, String nickname, byte gender, String image, Date birthday
            , String address, String signature) {
        return mApiService.updateUserInfo(userId, nickname, birthday, image, address, signature);
    }

    @Override
    public Single<String> login(String username, String password, String mac) {
        return mApiService.login(username, password, mac, "app");
    }

    @Override
    public Single<String> logout(String username, String mac) {
        return mApiService.logout(username, mac, "app");
    }

    @Override
    public Single<String> register(String username, String password, String nickname, String code) {
        return mApiService.register(username, password, nickname, code);
    }

    @Override
    public Single<String> checkUserExist(String username) {
        return mApiService.verify("username", username, null);
    }

    @Override
    public Single<String> checkCodeCorrect(String username, String code) {
        return mApiService.verify("code", username, code);
    }

    @Override
    public Single<String> sendVerificationCode(String phone) {
        return mApiService.sendVerificationCode(phone);
    }

    @Override
    public Single<String> getHistoryListByUserId(String userId, String ctype, int page, int rows) {
        return mApiService.getHistoryListByUserId(userId, page, rows, ctype);
    }

    @Override
    public Single<String> deleteHistoryByUserAndNewsId(String userId, String newsId) {
        return mApiService.deleteHistoryUserAndNewsId(userId, newsId);
    }

    @Override
    public Single<String> deleteHistoryListByUserId(String userId) {
        return mApiService.deleteHistoryByUserId(userId);
    }

    @Override
    public Single<String> deleteHistoryListByCtype(String userId, String ctype) {
        return mApiService.deleteHistoryListByCtype(userId, ctype);
    }

    @Override
    public Single<String> getCollectionListByUserId(String userId, String ctype, int page, int rows) {
        return mApiService.getCollectionListByUserId(userId, page, rows, ctype);
    }

    @Override
    public Single<String> getCollectionListByUserIdAndTag(String userId, String tag, int page, int rows) {
        return mApiService.getCollectionListByUserIdAndTag(userId, page, rows, tag);
    }

    @Override
    public Single<String> insertCollection(String userId, String newsId, String tag) {
        return mApiService.createCollection(userId, newsId, tag);
    }

    @Override
    public Single<String> updateCollection(String userId, String newsId, String tag) {
        return mApiService.updateCollection(userId, newsId, tag);
    }

    @Override
    public Single<String> cancelCollectNews(String userId, String newsId) {
        return mApiService.deleteCollection(userId, newsId);
    }

    @Override
    public Single<String> getNewsVoByNewsId(String newsId, String userId) {
        return mApiService.getNewsVoByNewsId(newsId, userId);
    }

    @Override
    public Single<String> listNewsListVoByChannelId(String channelId, String userId, String ctype, int page, int rows, int today) {
        return mApiService.getNewsListByChannelId(channelId, userId, ctype, page, rows, today);
    }

    @Override
    public Single<String> listNewsListVoByDislikeIds(String channels, String userId, String ctype, int page, int rows, int today) {
        return mApiService.getNewsListVoByDislikeIds(channels, userId, ctype, page, rows, today);
    }

    @Override
    public Single<String> listNewsListVoWithCommentsByUserId(String userId, String ownerId, int page, int rows) {
        return mApiService.listNewsListVoWithCommentsByUserId(userId, ownerId, page, rows);
    }

    @Override
    public Single<String> listVideoNewsListVoByDislikeIds(String channels, String userId, String ctype, int page, int rows, int today) {
        return mApiService.getVideoNewsListVoByDislikeIds("qzNwaOiu," + channels, userId, ctype, page, rows, today);
    }

    @Override
    public Single<String> listRecommendNewsList(String dislikeIds, String userId, String ctype, int page, int rows, int today) {
        return mApiService.getRecommendNewsList(page, rows, userId, dislikeIds, ctype, today);
    }

    @Override
    public Single<String> listUserAttentionNewsList(String userId, String ctype, int page, int rows, int today) {
        return mApiService.getUserAttentionNewsList(page, rows, userId, ctype, today);
    }

    @Override
    public Single<String> getNewsCommentsByNewsId(String newsId, String userId, int page, int rows) {
        return mApiService.getNewsCommentsByNewsId(newsId, userId, page, rows);
    }

    @Override
    public Single<String> addNewsComment(String newsId, String userId, String replyId, String content) {
        return mApiService.addNewsComment(newsId, userId, replyId, content);
    }

    @Override
    public Single<String> deleteNewsComment(String newsId, String userId, String replyId) {
        return mApiService.deleteNewsComment(newsId, userId, replyId);
    }

    @Override
    public Single<String> getNewsCommentById(String commentId, String userId) {
        return mApiService.getNewsCommentById(commentId, userId);
    }

    @Override
    public Single<String> likeObject(String userId, String objectId) {
        return mApiService.likeObject(userId, objectId);
    }

    @Override
    public Single<String> insertHistory(String userId, String id) {
        return mApiService.insertHistory(userId, id);
    }

    @Override
    public Single<String> dislikeObject(String userId, String objectId) {
        return mApiService.dislikeObject(userId, objectId);
    }

    @Override
    public Single<String> searchKeywords(String keywords, String ctype, int page, int rows) {
        return mApiService.searchKeywords(keywords, ctype, page, rows);
    }

    @Override
    public Single<String> operateAttention(String userId, String objectId) {
        return mApiService.operateAttention(userId, objectId);
    }

    @Override
    public Single<String> getDefaultFavouriteChannels() {
        return mApiService.getDefaultFavouriteChannels();
    }
}
