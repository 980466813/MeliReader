package com.lning.melireader.core.repository.db;

import android.text.TextUtils;

import com.lning.melireader.core.repository.db.dao.CollectionPojoDao;
import com.lning.melireader.core.repository.db.dao.DaoSession;
import com.lning.melireader.core.repository.db.dao.HistoryPojoDao;
import com.lning.melireader.core.repository.db.dao.NewsListVoDao;
import com.lning.melireader.core.repository.db.dao.SearchHistoryDao;
import com.lning.melireader.core.repository.db.dao.TagPojoDao;
import com.lning.melireader.core.repository.db.dao.UserVoDao;
import com.lning.melireader.core.repository.db.pojo.CollectionPojo;
import com.lning.melireader.core.repository.db.pojo.HistoryPojo;
import com.lning.melireader.core.repository.db.pojo.SearchHistory;
import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.bean.CollectionVo;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.UserBasicVo;
import com.lning.melireader.core.repository.http.bean.UserVo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import retrofit2.http.POST;

/**
 * Created by Ning on 2018/11/19.
 */
@Singleton
public class DatabaseHelperImpl implements DatabaseHelper {

    private final DaoSession daoSession;

    @Inject
    public DatabaseHelperImpl(DaoSession daoSession, @Named("debug") Boolean debug) {
        this.daoSession = daoSession;
        QueryBuilder.LOG_SQL = debug; // 开启SQL语句的DEBUG
        QueryBuilder.LOG_VALUES = debug; // 开启SQL值的DEBUG
    }

    @Override
    public Long insertNewsListVo(NewsListVo newsListVo) {
        NewsListVo dbNewsList = getNewsListVoById(newsListVo.getId());
        if (dbNewsList != null) {
            daoSession.getNewsListVoDao().update(newsListVo);
            return 1L;
        }
        return daoSession.getNewsListVoDao().insertOrReplace(newsListVo);
    }

    @Override
    public NewsListVo getNewsListVoById(String newsId) {
        return daoSession.getNewsListVoDao()
                .queryBuilder()
                .where(NewsListVoDao.Properties.Id.eq(newsId))
                .build()
                .unique();
    }

    @Override
    public UserVo selectUserInfo(String type, String token) {
        return daoSession.getUserVoDao()
                .queryBuilder()
                .where(UserVoDao.Properties.Type.eq(type), UserVoDao.Properties.Token.eq(token))
                .unique();
    }

    @Override
    public Long insertUserInfo(String type, String token, UserVo userVo) {
        userVo.setType(type);
        userVo.setToken(token);
        deleteUserInfo(type, token);
        return daoSession.getUserVoDao().insertOrReplace(userVo);
    }

    @Override
    public Long deleteUserInfo(String type, String token) {
        UserVo userVo = selectUserInfo(type, token);
        if (userVo != null) {
            daoSession.getUserVoDao().delete(userVo);
            return selectUserInfo(type, token) != null ? -1L : 1L;
        }
        return 1L;
    }

    @Override
    public List<HistoryPojo> selectHistoryByUserId(String type, String userId, boolean isOrderByTime, int page, int rows) {
        QueryBuilder<HistoryPojo> queryBuilder = daoSession.getHistoryPojoDao().queryBuilder();
        if (TextUtils.isEmpty(type) || "all".equals(type)) {
            queryBuilder.where(HistoryPojoDao.Properties.UserId.eq(userId));
        } else {
            queryBuilder.where(HistoryPojoDao.Properties.UserId.eq(userId), HistoryPojoDao.Properties.Ctype.eq(type));
        }
        if (page >= 0 && rows > 0) {
            return queryBuilder
                    .orderDesc(HistoryPojoDao.Properties.Created)
                    .limit(rows)
                    .offset((page - 1) * rows)
                    .list();
        } else {
            return queryBuilder
                    .orderDesc(HistoryPojoDao.Properties.Created)
                    .list();
        }
    }


    @Override
    public Long getHistoryListCount(String type, String userId) {
        QueryBuilder<HistoryPojo> queryBuilder = daoSession.getHistoryPojoDao().queryBuilder();
        if (TextUtils.isEmpty(type) || "all".equals(type)) {
            queryBuilder.where(HistoryPojoDao.Properties.UserId.eq(userId));
        } else {
            queryBuilder.where(HistoryPojoDao.Properties.UserId.eq(userId), HistoryPojoDao.Properties.Ctype.eq(type));
        }
        return queryBuilder.count();
    }

    @Override
    public HistoryPojo getHistoryByUserAndNewsId(String userId, String newsId) {
        return daoSession.getHistoryPojoDao().queryBuilder()
                .where(HistoryPojoDao.Properties.UserId.eq(userId),
                        HistoryPojoDao.Properties.NewsId.eq(newsId))
                .build()
                .unique();
    }


    @Override
    public Long insertHistory(HistoryVo historyVo, String userId) {
        NewsListVo newsListVo = historyVo.getNewsListVo();
        insertNewsListVo(newsListVo);
        HistoryPojo historyPojo = new HistoryPojo();
        historyPojo.setCtype(newsListVo.getCtype());
        historyPojo.setNewsId(newsListVo.getId());
        historyPojo.setCreated(historyVo.getCreated());
        historyPojo.setId(historyVo.getId());
        historyPojo.setUserId(userId);
        HistoryPojo pojo = getHistoryByUserAndNewsId(userId, newsListVo.getId());
        if (pojo != null) {
            historyPojo.setHistoryId(pojo.getHistoryId());
            daoSession.getHistoryPojoDao().update(historyPojo);
            return 1L;
        }
        return daoSession.getHistoryPojoDao().insertOrReplace(historyPojo);
    }

    @Override
    public Long deleteHistoryByUserId(String userId) {
        List<HistoryPojo> historyPojos = selectHistoryByUserId("all", userId, false, -1, -1);
        if (historyPojos != null && historyPojos.size() > 0) {
            daoSession.getHistoryPojoDao().deleteInTx(historyPojos);
            historyPojos = selectHistoryByUserId("all", userId, false, -1, -1);
            return historyPojos != null && historyPojos.size() > 0 ? -1L : 1L;
        }
        return 1L;
    }

    @Override
    public Long deleteHistoryListByUserIdAndCtype(String userId, String ctype) {
        List<HistoryPojo> historyPojos = selectHistoryByUserId(ctype, userId, false, -1, -1);
        if (historyPojos != null && historyPojos.size() > 0) {
            daoSession.getHistoryPojoDao().deleteInTx(historyPojos);
            historyPojos = selectHistoryByUserId(ctype, userId, false, 0, 1);
            return historyPojos != null && historyPojos.size() > 0 ? -1L : 1L;
        }
        return 1L;
    }

    @Override
    public Long deleteHistoryByUserAndNewsId(String userId, String newsId) {
        HistoryPojo historyPojo = getHistoryByUserAndNewsId(userId, newsId);
        if (historyPojo != null) {
            daoSession.getHistoryPojoDao().delete(historyPojo);
            return getHistoryByUserAndNewsId(userId, newsId) != null ? -1L : 1L;
        }
        return 1L;
    }

    @Override
    public List<CollectionPojo> selectCollectionByUserId(String userId, String ctype, String tag, int page, int rows) {
        QueryBuilder<CollectionPojo> queryBuilder = daoSession.getCollectionPojoDao().queryBuilder();
        if (TextUtils.isEmpty(ctype) || "all".equals(ctype)) {
            if (TextUtils.isEmpty(tag) || "all".equals(tag)) {
                queryBuilder.where(CollectionPojoDao.Properties.UserId.eq(userId));
            } else {
                queryBuilder.where(CollectionPojoDao.Properties.UserId.eq(userId), CollectionPojoDao.Properties.Tag.like("%" + tag + "%"));
            }
        } else {
            if (TextUtils.isEmpty(tag) || "all".equals(tag)) {
                queryBuilder.where(CollectionPojoDao.Properties.UserId.eq(userId), CollectionPojoDao.Properties.Ctype.eq(ctype));
            } else {
                queryBuilder.where(CollectionPojoDao.Properties.UserId.eq(userId), CollectionPojoDao.Properties.Ctype.eq(ctype)
                        , CollectionPojoDao.Properties.Tag.like("%" + tag + "%"));
            }
        }
        if (page < 0 && rows < 0) {
            return queryBuilder
                    .orderDesc(CollectionPojoDao.Properties.Created)
                    .build()
                    .list();
        }
        return queryBuilder
                .orderDesc(CollectionPojoDao.Properties.Created)
                .limit(rows)
                .offset((page - 1) * rows)
                .build()
                .list();
    }

    @Override
    public Long getCollectionByUserIdCount(String userId, String ctype, String tag) {
        QueryBuilder<CollectionPojo> queryBuilder = daoSession.getCollectionPojoDao().queryBuilder();
        if (!TextUtils.isEmpty(tag) || "all".equals(tag)) {
            if (!TextUtils.isEmpty(ctype) || "all".equals(ctype)) {
                queryBuilder.where(CollectionPojoDao.Properties.UserId.eq(userId));
            } else {
                queryBuilder.where(CollectionPojoDao.Properties.UserId.eq(userId), CollectionPojoDao.Properties.Ctype.eq(ctype));
            }
        } else {
            if (!TextUtils.isEmpty(ctype) || "all".equals(ctype)) {
                queryBuilder.where(CollectionPojoDao.Properties.UserId.eq(userId));
            } else {
                queryBuilder.where(CollectionPojoDao.Properties.UserId.eq(userId), CollectionPojoDao.Properties.Tag.like("%" + tag + "%")
                        , CollectionPojoDao.Properties.Ctype.eq(ctype));
            }
        }
        return queryBuilder
                .count();
    }

    @Override
    public TagPojo getTagPojoByUserIdAndName(String userId, String name) {
        return daoSession.getTagPojoDao().queryBuilder()
                .where(TagPojoDao.Properties.UserId.eq(userId), TagPojoDao.Properties.Content.eq(name))
                .build()
                .unique();
    }

    @Override
    public Long insertTag(TagPojo tagPojo) {
        TagPojo tag = getTagPojoByUserIdAndName(tagPojo.getUserId(), tagPojo.getContent());
        if (tag == null) {
            return daoSession.getTagPojoDao().insert(tagPojo);
        }
        return 1L;
    }

    @Override
    public List<TagPojo> selectTagsByUserId(String userId) {
        return daoSession.getTagPojoDao().queryBuilder()
                .where(TagPojoDao.Properties.UserId.eq(userId))
                .build()
                .list();

    }

    @Override
    public Long clearAllSearchHistories() {
        daoSession.getSearchHistoryDao().deleteAll();
        return 1L;
    }

    @Override
    public List<SearchHistory> getAllSearchHistories() {
        return daoSession.getSearchHistoryDao().queryBuilder()
                .build()
                .list();
    }

    @Override
    public SearchHistory getSearchHistory(String content) {
        return daoSession.getSearchHistoryDao().queryBuilder()
                .where(SearchHistoryDao.Properties.Keywords.eq(content))
                .build()
                .unique();
    }

    @Override
    public Long insertSearchHistory(String content) {
        SearchHistory searchHistory = getSearchHistory(content);
        if (searchHistory == null) {
            return daoSession.getSearchHistoryDao()
                    .insert(new SearchHistory(null, content, 1));
        }
        searchHistory.setClick(searchHistory.getClick() + 1);
        daoSession.getSearchHistoryDao().update(searchHistory);
        return 1L;
    }

    @Override
    public Long updateUserInfo(String type, String token, final String nickname, final byte gender, final String image,
                               final Date birthday, final String address, final String signature) {
        UserVo userVo = selectUserInfo(type, token);
        if (userVo != null) {
            if (!TextUtils.isEmpty(nickname))
                userVo.setNickname(nickname);
            if (!TextUtils.isEmpty(image))
                userVo.setProfile(image);
            if (!TextUtils.isEmpty(address))
                userVo.setProfile(address);
            if (!TextUtils.isEmpty(signature))
                userVo.setProfile(signature);
            if (birthday != null)
                userVo.setBirthday(birthday);
            if (gender >= -1 && gender < 2)
                userVo.setGender(gender);
            daoSession.getUserVoDao().update(userVo);
            return 1L;
        }
        return -1L;
    }

    @Override
    public Long insertCollection(CollectionVo collectionVo, String userId) {
        NewsListVo newsListVo = collectionVo.getNewsListVo();
        insertNewsListVo(newsListVo);
        CollectionPojo collectionPojo = new CollectionPojo();
        collectionPojo.setCtype(newsListVo.getCtype());
        collectionPojo.setNewsId(newsListVo.getId());
        collectionPojo.setCreated(collectionVo.getCreated());
        collectionPojo.setId(collectionVo.getId());
        collectionPojo.setUserId(userId);
        collectionPojo.setTag(collectionVo.getTag());
        if (!TextUtils.isEmpty(collectionVo.getTag())) {
            String[] tags = collectionVo.getTag().split(",");
            for (String tag : tags) {
                insertTag(new TagPojo(null, userId, tag, new Date()));
            }
        }
        CollectionPojo pojo = getCollectionByUserAndNewsId(userId, newsListVo.getId());
        if (pojo != null) {
            collectionPojo.setCollectionId(pojo.getCollectionId());
            daoSession.getCollectionPojoDao().update(collectionPojo);
            return 1L;
        }
        return daoSession.getCollectionPojoDao().insertOrReplace(collectionPojo);
    }

    @Override
    public Long updateCollection(String userId, String newsId, String tag) {
        CollectionPojo entity = getCollectionByUserAndNewsId(userId, newsId);
        if (entity != null) {
            if (!TextUtils.isEmpty(tag)) {
                String[] tags = tag.split(",");
                for (String t : tags) {
                    insertTag(new TagPojo(null, userId, t, new Date()));
                }
            }
            entity.setTag(tag);
            daoSession.getCollectionPojoDao().update(entity);
            return 1L;
        }
        return -1L;
    }

    @Override
    public Long deleteCollectionByUserId(String userId) {
        QueryBuilder<CollectionPojo> queryBuilder = daoSession.getCollectionPojoDao().queryBuilder().where(CollectionPojoDao.Properties.UserId.eq(userId));
        List<CollectionPojo> list = queryBuilder.build().list();
        daoSession.getCollectionPojoDao().deleteInTx(list);
        List<TagPojo> list1 = daoSession.getTagPojoDao().queryBuilder().where(TagPojoDao.Properties.UserId.eq(userId)).build().list();
        daoSession.getTagPojoDao().deleteInTx(list1);
        return 1L;
    }

    @Override
    public Long deleteCollectionByUserAndNewsId(String userId, String newsId) {
        CollectionPojo collectionPojo = getCollectionByUserAndNewsId(userId, newsId);
        if (collectionPojo != null) {
            daoSession.getCollectionPojoDao().delete(collectionPojo);
            return getCollectionByUserAndNewsId(userId, newsId) == null ? 1L : -1L;
        }
        return 1L;
    }

    @Override
    public CollectionPojo getCollectionByUserAndNewsId(String userId, String newsId) {
        return daoSession.getCollectionPojoDao().queryBuilder()
                .where(CollectionPojoDao.Properties.UserId.eq(userId), CollectionPojoDao.Properties.NewsId.eq(newsId))
                .build()
                .unique();
    }
}
