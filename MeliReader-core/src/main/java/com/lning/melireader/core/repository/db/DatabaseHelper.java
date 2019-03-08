package com.lning.melireader.core.repository.db;

import com.lning.melireader.core.repository.db.pojo.CollectionPojo;
import com.lning.melireader.core.repository.db.pojo.HistoryPojo;
import com.lning.melireader.core.repository.db.pojo.SearchHistory;
import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.bean.CollectionVo;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.UserVo;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ning on 2018/11/15.
 */

public interface DatabaseHelper {

    Long insertNewsListVo(NewsListVo newsListVo);

    NewsListVo getNewsListVoById(String newsId);


    UserVo selectUserInfo(String type, String token);

    Long insertUserInfo(String type, String token, UserVo userVo);

    Long deleteUserInfo(String type, String token);

    List<HistoryPojo> selectHistoryByUserId(String type, String userId, boolean isOrderByTime, int page, int rows);

    Long getHistoryListCount(String type, String userId);

    HistoryPojo getHistoryByUserAndNewsId(String userId, String newsId);

    Long insertHistory(HistoryVo historyVo, String userId);

    Long deleteHistoryByUserId(String userId);

    Long deleteHistoryByUserAndNewsId(String userId, String newsId);

    Long deleteHistoryListByUserIdAndCtype(String userId, String ctype);

    List<CollectionPojo> selectCollectionByUserId(String userId, String ctype, String tag, int page, int rows);

    CollectionPojo getCollectionByUserAndNewsId(String userId, String newsId);

    Long getCollectionByUserIdCount(String userId, String ctype, String tag);

    Long updateUserInfo(String type, String token, String nickname, byte gender, String image,
                        Date birthday, String address, String signature);

    Long insertCollection(CollectionVo collectionVo, String userId);

    Long updateCollection(String userId, String newsId, String tag);

    Long deleteCollectionByUserId(String userId);

    Long deleteCollectionByUserAndNewsId(String userId, String newsId);

    Long insertTag(TagPojo tagPojo);

    TagPojo getTagPojoByUserIdAndName(String userId, String name);

    List<TagPojo> selectTagsByUserId(String userId);

    Long clearAllSearchHistories();

    List<SearchHistory> getAllSearchHistories();

    SearchHistory getSearchHistory(String content);

    Long insertSearchHistory(String content);

}
