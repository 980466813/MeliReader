package com.lning.melireader.presenter;

import com.lning.melireader.contact.VideoDetailContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.model.impl.AttentionModel;
import com.lning.melireader.model.impl.CollectionModel;
import com.lning.melireader.model.impl.CommentModel;
import com.lning.melireader.model.impl.LikeModel;
import com.lning.melireader.model.impl.NewsModel;
import com.lning.melireader.model.impl.TagModel;
import com.lning.melireader.ui.main.video.detail.VideoDetailFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/22.
 */

public class VideoDetailPresenter extends BasePresenter<VideoDetailFragment, NewsModel>
        implements VideoDetailContact.Presenter<VideoDetailFragment> {

    @Inject
    CollectionModel collectionModel;

    @Inject
    AttentionModel attentionModel;

    @Inject
    CommentModel commentModel;

    @Inject
    TagModel tagModel;

    @Inject
    LikeModel likeModel;

    @Inject
    public VideoDetailPresenter(NewsModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void checkNewsListCollected(String newsId) {
        collectionModel.addSubscribe(collectionModel.checkNewsCollected(newsId)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.onGetCollectedSuccess(aBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }


    @Override
    public void collectNews(NewsVo newsVo) {
        mMvpView.showDialog("请稍侯");
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.dismissDialog();
            return;
        }
        collectionModel.addSubscribe(collectionModel.insertCollection("", newsVo)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.onOperateCollectNewsSuccess(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void updateCollection(String newsId, String tag) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        collectionModel.addSubscribe(collectionModel.updateCollection(newsId, tag)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));

    }

    @Override
    public void getNewsDetailByNewsId(String newsId) {
        mMvpView.showLoading();
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.dismissDialog();
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.getNewsVoByNewsId(newsId)
                .subscribe(new Consumer<NewsVo>() {
                    @Override
                    public void accept(NewsVo newsVo) throws Exception {
                        mMvpView.dismissDialog();
                        if (newsVo != null) {
                            mMvpView.onGetNewsVoSuccess(newsVo);
                        } else {
                            mMvpView.showError("暂未查询该新闻详情");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void addNewsComment(String newsId, String content) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.onAddNewsCommentError();
            return;
        }
        commentModel.addSubscribe(commentModel.addNewsComment(newsId, "", content)
                .subscribe(new Consumer<CommentVo>() {
                    @Override
                    public void accept(CommentVo commentVo) throws Exception {
                        mMvpView.showMessage("评论成功");
                        mMvpView.onAddNewsCommentSuccess(commentVo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cancelAddNewsComment();
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void cancelAddNewsComment() {
        LogUtils.d("取消评论");
        mMvpView.onAddNewsCommentError();
        commentModel.cancelSubscribe();
    }

    @Override
    public void getAllUserTags() {
        tagModel.addSubscribe(tagModel.getUserTagList()
                .subscribe(new Consumer<List<TagPojo>>() {
                    @Override
                    public void accept(List<TagPojo> tagPojos) throws Exception {
                        mMvpView.onGetUserTagsSuccess(tagPojos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.d("用户标签为空");
                        mMvpView.onGetUserTagsSuccess(new ArrayList<TagPojo>());
                    }
                }));
    }

    @Override
    public void cancelCollectNews(String newsId) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.onAddNewsCommentError();
            return;
        }
        collectionModel.addSubscribe(collectionModel.deleteCollection(newsId)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.onGetCollectedSuccess(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cancelAddNewsComment();
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void likeObject(String newsId) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.onAddNewsCommentError();
            return;
        }
        mMvpView.showDialog("正在请求");
        likeModel.addSubscribe(likeModel.likeObject(newsId)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.showMessage("点赞成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void dislikeObject(String newsId) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.onAddNewsCommentError();
            return;
        }
        mMvpView.showDialog("正在请求");
        likeModel.addSubscribe(likeModel.dislikeObject(newsId)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.onDislikeObjectSuccess(aBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void operateAttention(final boolean checked, String objectId) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpView.showDialog("正在请求");
        attentionModel.addSubscribe(attentionModel.operateAttention(objectId)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.onOperateAttentionSuccess(checked);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }
}
