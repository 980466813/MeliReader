package com.lning.melireader.core.repository;

import android.text.TextUtils;

import com.lning.melireader.core.app.constant.ResponseCode;
import com.lning.melireader.core.repository.db.DatabaseHelper;
import com.lning.melireader.core.repository.db.pojo.CollectionPojo;
import com.lning.melireader.core.repository.db.pojo.HistoryPojo;
import com.lning.melireader.core.repository.db.pojo.SearchHistory;
import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.HttpHelper;
import com.lning.melireader.core.repository.http.bean.CollectionVo;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.FavouriteChannel;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.repository.preference.PreferencesHelper;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.RxUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2018/11/15.
 */
@Singleton
public class RepositoryManagerImpl implements RepositoryManager {

    private PreferencesHelper mPreferencesHelper;
    private HttpHelper mHttpHelper;
    private DatabaseHelper mDatabaseHelper;

    @Inject
    public RepositoryManagerImpl(PreferencesHelper preferencesHelper, HttpHelper httpHelper, DatabaseHelper databaseHelper) {
        this.mPreferencesHelper = preferencesHelper;
        this.mHttpHelper = httpHelper;
        this.mDatabaseHelper = databaseHelper;
    }

    @Override
    public Boolean getFirstLauncherStatus() {
        return mPreferencesHelper.getFirstLauncherStatus();
    }

    @Override
    public void updateFirstLauncherStatus(boolean firstLauncher) {
        mPreferencesHelper.updateFirstLauncherStatus(firstLauncher);
    }

    @Override
    public void saveLocalUserInfo(String type, String token, UserVo userVo) {
        mDatabaseHelper.insertUserInfo(type, token, userVo);
    }

    @Override
    public UserVo getLocalUserInfo(String type, String token) {
        return mDatabaseHelper.selectUserInfo(type, token);
    }

    @Override
    public void saveLoginUserToken(String token) {
        mPreferencesHelper.saveLoginUserToken(token);
    }

    @Override
    public String getLoginUserToken() {
        return mPreferencesHelper.getLoginUserToken();
    }

    @Override
    public String getFavouriteChannels() {
        return mPreferencesHelper.getLocalFavouriteChannels();
    }

    @Override
    public void updateFavouriteChannels(String favouriteChannels) {
        mPreferencesHelper.updateLocalFavouriteChannels(favouriteChannels);
    }

    @Override
    public void saveLoginUserId(String username) {
        mPreferencesHelper.saveLoginUserId(username);
    }

    @Override
    public String getLoginUserId() {
        return mPreferencesHelper.getLoginUserId();
    }

    @Override
    public String getLocalPhoneMac() {
        return mPreferencesHelper.getLocalPhoneMac();
    }

    @Override
    public void updateLocalPhoneMac(String mac) {
        mPreferencesHelper.updateLocalPhoneMac(mac);
    }

    @Override
    public Integer getLocalTextSize() {
        return mPreferencesHelper.getLocalTextSize();
    }

    @Override
    public void updateLocalTextSize(int textSize) {
        mPreferencesHelper.updateLocalTextSize(textSize);
    }

    @Override
    public Boolean getLocalWifiTip() {
        return mPreferencesHelper.getLocalWifiTip();
    }

    @Override
    public void updateLocalWifiTip(boolean wifiTip) {
        mPreferencesHelper.updateLocalWifiTip(wifiTip);
    }

    @Override
    public Single<Result> insertHistory(final String userId, NewsListVo newsListVo) {
        return mHttpHelper.insertHistory(userId, newsListVo.getId())
                .map(RxUtils.mappingResponseToResult(HistoryVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        HistoryVo historyVo = (HistoryVo) result.getData();
                        mDatabaseHelper.insertHistory(historyVo, userId);
                    }
                });
    }

    @Override
    public Single<Result> deleteHistoryListByUserId(final String userId) {
        return mHttpHelper.deleteHistoryListByUserId(userId)
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        mDatabaseHelper.deleteHistoryByUserId(userId);
                    }
                });
    }

    @Override
    public Single<Result> insertTagPojo(final String userId, final String tag) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                Long result = mDatabaseHelper.insertTag(new TagPojo(null, userId, tag, new Date()));
                emitter.onSuccess(Result.build(result > 0 ? ResponseCode.SUCCESS : ResponseCode.NOT_NEED_SHOW_MESSAGE));
            }
        }).compose(RxUtils.<Result>rxSchedulerHelper());
    }


    @Override
    public Single<Result> getUserTags(String userId) {
        List<TagPojo> tagPojos = mDatabaseHelper.selectTagsByUserId(userId);
        boolean isNotEmpty = tagPojos != null && tagPojos.size() > 0;
        return Single.just(Result.build(isNotEmpty ? 2000 : 0, isNotEmpty ? "" : "暂无更多标签信息", tagPojos))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> insertLocalHistory(final String userId, final NewsListVo newsListVo) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                HistoryVo historyVo = new HistoryVo(0L, userId, newsListVo);
                Long result = mDatabaseHelper.insertHistory(historyVo, userId);
                emitter.onSuccess(Result.build(result > 0 ? 2000 : 0000, result > 0 ? "" : "插入历史记录失败", result > 0 ? historyVo : null));
            }
        });
    }

    @Override
    public Single<Result> deleteLocalHistoryList(final String userId) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                Long result = mDatabaseHelper.deleteHistoryByUserId(userId);
                emitter.onSuccess(Result.build(result > 0 ? 2000 : 0000, result > 0 ? "删除历史记录成功" : "删除历史记录失败"));
            }
        });
    }

    @Override
    public Single<Result> insertLocalCollection(final String userId, final String tag, final NewsListVo newsListVo) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                CollectionVo collectionVo = new CollectionVo(0L, userId, tag, newsListVo);
                Long result = mDatabaseHelper.insertCollection(collectionVo, userId);
                emitter.onSuccess(Result.build(result > 0 ? 2000 : 0000, result > 0 ? "收藏成功" : "收藏失败"));
            }
        });
    }

    @Override
    public Single<Result> updateLocalCollection(final String userId, final String newsId, final String tag) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                Long result = mDatabaseHelper.updateCollection(userId, newsId, tag);
                emitter.onSuccess(Result.build(result > 0 ? 2000 : 0000, result > 0 ? "更改成功" : "更改失败"));
            }
        });
    }

    @Override
    public Single<Result> deleteLocalCollection(final String userId, final String newsId) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                Long result = mDatabaseHelper.deleteCollectionByUserAndNewsId(userId, newsId);
                emitter.onSuccess(Result.build(result > 0 ? 2000 : 0000, result > 0 ? "删除成功" : "删除失败"));
            }
        });
    }

    @Override
    public Single<Result> deleteCollection(final String userId, final String newsId) {
        return mHttpHelper.cancelCollectNews(userId, newsId)
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        mDatabaseHelper.deleteCollectionByUserAndNewsId(userId, newsId);
                    }
                });
    }

    @Override
    public Single<Result> getCollection(final String userId, final String newsId) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                CollectionPojo collectionPojo = mDatabaseHelper.getCollectionByUserAndNewsId(userId, newsId);
                emitter.onSuccess(Result.build(collectionPojo == null ? 0000 : 2000, ""));
            }
        }).compose(RxUtils.<Result>rxSchedulerHelper());
    }

    /**
     * 收藏新闻(上传后台)
     *
     * @param userId
     * @param tag
     * @param newsVo
     * @return
     */
    @Override
    public Single<Result> insertCollection(final String userId, final String tag, final NewsVo newsVo) {
        return mHttpHelper.insertCollection(userId, newsVo.getId(), tag)
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        CollectionVo collectionVo = new CollectionVo(0L, userId, tag, CommonUtils.parseNewsListVo(newsVo));
                        mDatabaseHelper.insertCollection(collectionVo, userId);
                    }
                });
    }

    /**
     * 更改收藏标签(上传后台)
     *
     * @param userId
     * @param newsId
     * @param tag
     * @return
     */
    @Override
    public Single<Result> updateCollection(final String userId, final String newsId, final String tag) {
        return mHttpHelper.updateCollection(userId, newsId, tag)
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        mDatabaseHelper.updateCollection(userId, newsId, tag);
                    }
                });
    }

    /**
     * 取消收藏(上传后台)
     *
     * @param userId
     * @param newsId
     * @return
     */
    @Override
    public Single<Result> cancelCollection(final String userId, final String newsId) {
        return mHttpHelper.cancelCollectNews(userId, newsId)
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        mDatabaseHelper.deleteCollectionByUserAndNewsId(userId, newsId);
                    }
                });
    }

    @Override
    public Single<Result> getUserInfo(String type, String token) {
        return mHttpHelper.getUserInfo(type, token)
                .map(RxUtils.mappingResponseToResult(UserVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> getUserInfoByUserId(String type, String token, String userId, String ownerId) {
        return mHttpHelper.getUserInfoByUserId(type, token, userId, ownerId)
                .map(RxUtils.mappingResponseToResult(UserVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> updateUserInfo(final String userId, final String nickname, final byte gender, final String image, final Date birthday, final String address, final String signature) {
        return mHttpHelper.updateUserInfo(userId, nickname, gender, image, birthday, address, signature)
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        Long updateResult = mDatabaseHelper.updateUserInfo("basic", getLoginUserToken(), nickname, gender, image, birthday, address, signature);
                    }
                });
    }

    @Override
    public Single<Result> login(String username, String password, String mac) {
        return mHttpHelper.login(username, password, mac)
                .map(RxUtils.mappingResponseToResult(String.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> logout(String username, String mac) {
        return mHttpHelper.logout(username, mac)
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> register(String username, String password, String nickname, String code) {
        return mHttpHelper.register(username, password, nickname, code)
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> checkUserExist(String username) {
        return mHttpHelper.checkUserExist(username)
                .map(RxUtils.mappingResponseToResult(String.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> getUserProfile(String username) {
        return mHttpHelper.checkUserExist(username)
                .map(new Function<String, Result>() {
                    @Override
                    public Result apply(String s) throws Exception {
                        return Result.formatToPojo(s, String.class);
                    }
                })
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> verifyCode(String username, String code) {
        return mHttpHelper.checkCodeCorrect(username, code)
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> getUserInfoFromLocal(final String type, final String token) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                UserVo userVo = mDatabaseHelper.selectUserInfo(type, token);
                emitter.onSuccess(Result.build(userVo != null ? 2000 : 0, "", userVo));
            }
        }).compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> getHistoryListByUserId(final String userId, String ctype, int page, int rows) {
        return mHttpHelper.getHistoryListByUserId(userId, ctype, page, rows)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        ItemListVo itemListVo = (ItemListVo) result.getData();
                        LogUtils.d(itemListVo.toString());
                        List<HistoryVo> rows = JsonUtils.jsonToList(itemListVo.getRows().toString(), HistoryVo.class);
                        for (HistoryVo historyVo : rows) {
                            mDatabaseHelper.insertHistory(historyVo, userId);
                        }
                    }
                });
    }

    @Override
    public Single<Result> getLocalHistoryListByUserId(final String userId, final String ctype, final int page, final int rows) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                List<HistoryPojo> historyPojos = mDatabaseHelper.selectHistoryByUserId(ctype, userId, true, page, rows);
                Long count = mDatabaseHelper.getHistoryListCount(ctype, userId);
                double totalPages = (count * 1.0) / rows;
                Long pages = totalPages - Math.floor(totalPages) > 0 ? (long) (totalPages + 1) : (long) totalPages;
                boolean isNotEmpty = historyPojos != null && historyPojos.size() > 0;
                List<HistoryVo> historyVos = new ArrayList<>();
                if (isNotEmpty) {
                    for (HistoryPojo historyPojo : historyPojos) {
                        HistoryVo historyVo = CommonUtils.parseHistory(historyPojo);
                        historyVos.add(historyVo);
                    }
                }
                ItemListVo itemListVo = new ItemListVo();
                itemListVo.setTotalRows(count);
                itemListVo.setTotalPage(pages);
                itemListVo.setEnd(page == pages);
                itemListVo.setFirst(page == 1);
                itemListVo.setLimit(rows);
                itemListVo.setRows(historyVos);
                emitter.onSuccess(Result.build(isNotEmpty ? 2000 : 0, !isNotEmpty ? "暂无更多历史记录" : "", itemListVo));
            }
        }).compose(RxUtils.<Result>rxSchedulerHelper());
    }

    /**
     * 通过收藏新闻类别分页查询(网络)
     *
     * @param userId 用户ID
     * @param type   分类: news picture video
     * @param page   页码
     * @param rows   每页条数
     * @return
     */
    @Override
    public Single<Result> getCollectionListByUserIdAndCtype(final String userId, String type, int page, int rows) {
        return mHttpHelper.getCollectionListByUserId(userId, type, page, rows)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        ItemListVo itemListVo = (ItemListVo) result.getData();
                        List<CollectionVo> rows = JsonUtils.jsonToList(itemListVo.getRows().toString(), CollectionVo.class);
                        for (CollectionVo collectionVo : rows) {
                            mDatabaseHelper.insertCollection(collectionVo, userId);
                        }
                    }
                });
    }

    /**
     * 通过收藏新闻标签分页查询(网络)
     *
     * @param userId 用户ID
     * @param tag    标签
     * @param page   页码
     * @param rows   每页条数
     * @return
     */
    @Override
    public Single<Result> getCollectionListByUserIdAndTag(final String userId, String tag, int page, int rows) {
        return mHttpHelper.getCollectionListByUserIdAndTag(userId, tag, page, rows)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        ItemListVo itemListVo = (ItemListVo) result.getData();
                        LogUtils.d(itemListVo.toString());
                        List<CollectionVo> rows = JsonUtils.jsonToList(itemListVo.getRows().toString(), CollectionVo.class);
                        for (CollectionVo collectionVo : rows) {
                            mDatabaseHelper.insertCollection(collectionVo, userId);
                        }
                    }
                });
    }

    @Override
    public Single<Result> getNewsListVoByChannelId(String channelId, String userId, String ctype, int page, int rows, int today) {
        return mHttpHelper.listNewsListVoByChannelId(channelId, userId, ctype, page, rows, today)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {

                    }
                });
    }

    @Override
    public Single<Result> getNewsListVoByDislikeIds(String channels, String userId, String ctype, int page, int rows, int today) {
        return mHttpHelper.listNewsListVoByDislikeIds(channels, userId, ctype, page, rows, today)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {

                    }
                });
    }

    @Override
    public Single<Result> getNewsListVoWithCommentByUserId(String userId, String ownerId, int page, int rows) {
        return mHttpHelper.listNewsListVoWithCommentsByUserId(userId, ownerId, page, rows)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {

                    }
                });
    }

    @Override
    public Single<Result> getRecommendNewsList(String dislikeIds, String userId, String ctype, int page, int rows, int today) {
        return mHttpHelper.listRecommendNewsList(dislikeIds, userId, ctype, page, rows, today)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> getUserAttentionNewsList(String userId, String ctype, int page, int rows, int today) {
        return mHttpHelper.listUserAttentionNewsList(userId, ctype, page, rows, today)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> getVideoNewsListVoByDislikeIds(String channels, String userId, String ctype, int page, int rows, int today) {
        return mHttpHelper.listVideoNewsListVoByDislikeIds(channels, userId, ctype, page, rows, today)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> getDefaultFavouriteChannels() {
        return mHttpHelper.getDefaultFavouriteChannels()
                .map(RxUtils.mappingResponseToResultList(FavouriteChannel.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> getNewsVoByNewsId(String newsId, String userId) {
        return mHttpHelper.getNewsVoByNewsId(newsId, userId)
                .map(RxUtils.mappingResponseToResult(NewsVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper())
                .doOnSuccess(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {

                    }
                });
    }

    @Override
    public Single<Result> getNewsCommentsByNewsId(String newsId, String userId, int page, int rows) {
        return mHttpHelper.getNewsCommentsByNewsId(newsId, userId, page, rows)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> addNewsComment(String newsId, String userId, String replyId, String content) {
        return mHttpHelper.addNewsComment(newsId, userId, replyId, content)
                .map(RxUtils.mappingResponseToResult(CommentVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> searchKeywords(String keyword, String ctype, int page, int rows) {
        return mHttpHelper.searchKeywords(keyword, ctype, page, rows)
                .map(RxUtils.mappingResponseToResult(ItemListVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> operateAttention(String userId, String objectId) {
        return mHttpHelper.operateAttention(userId, objectId)
                .map(RxUtils.mappingResponseToResult(Boolean.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }


    @Override
    public Single<Result> getNewsCommentById(String commentId, String userId) {
        return mHttpHelper.getNewsCommentById(commentId, userId)
                .map(RxUtils.mappingResponseToResult(CommentVo.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> likeObject(String userId, String objectId) {
        return mHttpHelper.likeObject(userId, objectId)
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> dislikeObject(String userId, String objectId) {
        return mHttpHelper.dislikeObject(userId, objectId)
                .map(RxUtils.mappingResponseToResult(Boolean.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Boolean> clearAllSearchHistories() {
        return Single.just(mDatabaseHelper.clearAllSearchHistories())
                .map(new Function<Long, Boolean>() {
                    @Override
                    public Boolean apply(Long aLong) throws Exception {
                        return aLong > 0;
                    }
                }).compose(RxUtils.<Boolean>rxSchedulerHelper());
    }

    @Override
    public Single<Result> getAllSearchHistories() {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                List<SearchHistory> searchHistories = mDatabaseHelper.getAllSearchHistories();
                emitter.onSuccess(Result.success(searchHistories == null || searchHistories.size() <= 0 ? new ArrayList<SearchHistory>() : searchHistories));
            }
        }).compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> insertSearchHistory(final String content) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                Long result = mDatabaseHelper.insertSearchHistory(content);
                emitter.onSuccess(Result.success(result > 0));
            }
        }).compose(RxUtils.<Result>rxSchedulerHelper());
    }


    /**
     * @param userId
     * @param type
     * @param tag
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Single<Result> getLocalCollectionListByUserId(final String userId, final String type, final String tag,
                                                         final int page, final int rows) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                List<CollectionPojo> collectionPojos = mDatabaseHelper.selectCollectionByUserId(userId, type, tag, page, rows);
                Long count = mDatabaseHelper.getCollectionByUserIdCount(userId, type, tag);
                double totalPages = (count * 1.0) / rows;
                Long pages = totalPages - Math.floor(totalPages) > 0 ? (long) (totalPages + 1) : (long) totalPages;
                boolean isNotEmpty = collectionPojos != null && collectionPojos.size() > 0;
                List<CollectionVo> collectionVos = new ArrayList<>();
                if (isNotEmpty) {
                    for (CollectionPojo collectionPojo : collectionPojos) {
                        CollectionVo collectionVo = CommonUtils.parseCollection(collectionPojo);
                        collectionVos.add(collectionVo);
                    }
                }
                ItemListVo itemListVo = new ItemListVo();
                itemListVo.setTotalRows(count);
                itemListVo.setTotalPage(pages);
                itemListVo.setEnd(page == pages);
                itemListVo.setFirst(page == 1);
                itemListVo.setLimit(rows);
                itemListVo.setRows(collectionVos);
                emitter.onSuccess(Result.build(isNotEmpty ? 2000 : 0, !isNotEmpty ? "暂无更多收藏记录" : "", itemListVo));
            }
        }).compose(RxUtils.<Result>rxSchedulerHelper());
    }

    @Override
    public Single<Result> sendVerificationCode(String username) {
        return mHttpHelper.sendVerificationCode(username)
                .map(RxUtils.mappingResponseToResult(String.class))
                .compose(RxUtils.<Result>rxSchedulerHelper());
    }

}
