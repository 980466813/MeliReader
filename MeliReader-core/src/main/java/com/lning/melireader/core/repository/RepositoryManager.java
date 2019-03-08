package com.lning.melireader.core.repository;

import com.lning.melireader.core.repository.http.bean.CollectionVo;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.core.repository.http.bean.UserVo;

import java.util.Date;

import io.reactivex.Single;

/**
 * Created by Ning on 2018/11/15.
 */

public interface RepositoryManager {
    Boolean getFirstLauncherStatus();

    void updateFirstLauncherStatus(boolean firstLauncher);

    void saveLocalUserInfo(String type, String token, UserVo userVo);

    UserVo getLocalUserInfo(String type, String token);

    void saveLoginUserToken(String token);

    String getLoginUserToken();

    String getFavouriteChannels();

    void updateFavouriteChannels(String favouriteChannels);

    void saveLoginUserId(String id);

    String getLoginUserId();

    String getLocalPhoneMac();

    void updateLocalPhoneMac(String mac);

    Integer getLocalTextSize();

    void updateLocalTextSize(int textSize);

    Boolean getLocalWifiTip();

    void updateLocalWifiTip(boolean wifiTip);

    Single<Result> insertHistory(String userId, NewsListVo newsListVo);

    Single<Result> deleteHistoryListByUserId(String userId);

    Single<Result> deleteCollection(String userId, String newsId);

    Single<Result> getCollection(String userId, String newsId);

    Single<Result> insertCollection(String userId, String tag, NewsVo newsVo);

    Single<Result> updateCollection(String userId, String newsId, String tag);

    Single<Result> cancelCollection(String userId, String newsId);

    Single<Result> getUserInfo(String type, String token);

    Single<Result> getUserInfoByUserId(String type, String token, String userId, String ownerId);

    Single<Result> updateUserInfo(String userId, String nickname,
                                  byte gender, String image, Date birthday, String address, String signature);

    Single<Result> login(String username, String password, String mac);

    Single<Result> logout(String username, String mac);

    Single<Result> register(String username, String password, String nickname, String code);

    Single<Result> checkUserExist(String username);

    Single<Result> sendVerificationCode(String username);

    Single<Result> getUserProfile(String username);

    Single<Result> verifyCode(String username, String code);

    Single<Result> getHistoryListByUserId(String userId, String ctype, int page, int rows);

    Single<Result> getCollectionListByUserIdAndCtype(String userId, String ctype, int page, int rows);

    Single<Result> getCollectionListByUserIdAndTag(String userId, String tag, int page, int rows);

    Single<Result> getNewsListVoByChannelId(String channelId, String userId, String ctype, int page, int rows, int today);

    Single<Result> getNewsListVoByDislikeIds(String channels, String userId, String ctype, int page, int rows, int today);

    Single<Result> getNewsListVoWithCommentByUserId(String userId, String ownerId, int page, int rows);

    Single<Result> getRecommendNewsList(String dislikeIds, String userId, String ctype, int page, int rows, int today);

    Single<Result> getUserAttentionNewsList(String userId, String ctype, int page, int rows, int today);

    Single<Result> getVideoNewsListVoByDislikeIds(String channels, String userId, String ctype, int page, int rows, int today);

    Single<Result> getDefaultFavouriteChannels();

    Single<Result> getNewsVoByNewsId(String newsId, String userId);

    Single<Result> getNewsCommentsByNewsId(String newsId, String userId, int page, int rows);

    Single<Result> addNewsComment(String newsId, String userId, String replyId, String content);

    Single<Result> searchKeywords(String keyword, String ctype, int page, int rows);

    Single<Result> operateAttention(String userId, String objectId);

    Single<Result> insertLocalHistory(String userId, NewsListVo newsListVo);

    Single<Result> deleteLocalHistoryList(String userId);

    Single<Result> getUserInfoFromLocal(String type, String token);

    Single<Result> getLocalHistoryListByUserId(String userId, String ctype, int page, int rows);

    Single<Result> getLocalCollectionListByUserId(String userId, String ctype, String tag, int page, int rows);

    Single<Result> insertLocalCollection(String userId, String tag, NewsListVo newsListVo);

    Single<Result> updateLocalCollection(String userId, String newsId, String tag);

    Single<Result> deleteLocalCollection(String userId, String newsId);

    Single<Result> insertTagPojo(String userId, String tag);

    Single<Result> getUserTags(String userId);

    Single<Result> getNewsCommentById(String commentId, String userId);

    Single<Result> likeObject(String userId, String objectId);

    Single<Result> dislikeObject(String userId, String objectId);

    Single<Boolean> clearAllSearchHistories();

    Single<Result> getAllSearchHistories();

    Single<Result> insertSearchHistory(String content);

}
