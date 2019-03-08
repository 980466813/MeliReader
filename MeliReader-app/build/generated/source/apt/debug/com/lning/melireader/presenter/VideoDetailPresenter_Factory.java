// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.AttentionModel;
import com.lning.melireader.model.impl.CollectionModel;
import com.lning.melireader.model.impl.CommentModel;
import com.lning.melireader.model.impl.LikeModel;
import com.lning.melireader.model.impl.NewsModel;
import com.lning.melireader.model.impl.TagModel;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class VideoDetailPresenter_Factory implements Factory<VideoDetailPresenter> {
  private final Provider<NewsModel> mMvpModelProvider;

  private final Provider<CollectionModel> collectionModelProvider;

  private final Provider<AttentionModel> attentionModelProvider;

  private final Provider<CommentModel> commentModelProvider;

  private final Provider<TagModel> tagModelProvider;

  private final Provider<LikeModel> likeModelProvider;

  public VideoDetailPresenter_Factory(
      Provider<NewsModel> mMvpModelProvider,
      Provider<CollectionModel> collectionModelProvider,
      Provider<AttentionModel> attentionModelProvider,
      Provider<CommentModel> commentModelProvider,
      Provider<TagModel> tagModelProvider,
      Provider<LikeModel> likeModelProvider) {
    this.mMvpModelProvider = mMvpModelProvider;
    this.collectionModelProvider = collectionModelProvider;
    this.attentionModelProvider = attentionModelProvider;
    this.commentModelProvider = commentModelProvider;
    this.tagModelProvider = tagModelProvider;
    this.likeModelProvider = likeModelProvider;
  }

  @Override
  public VideoDetailPresenter get() {
    VideoDetailPresenter instance = new VideoDetailPresenter(mMvpModelProvider.get());
    VideoDetailPresenter_MembersInjector.injectCollectionModel(
        instance, collectionModelProvider.get());
    VideoDetailPresenter_MembersInjector.injectAttentionModel(
        instance, attentionModelProvider.get());
    VideoDetailPresenter_MembersInjector.injectCommentModel(instance, commentModelProvider.get());
    VideoDetailPresenter_MembersInjector.injectTagModel(instance, tagModelProvider.get());
    VideoDetailPresenter_MembersInjector.injectLikeModel(instance, likeModelProvider.get());
    return instance;
  }

  public static VideoDetailPresenter_Factory create(
      Provider<NewsModel> mMvpModelProvider,
      Provider<CollectionModel> collectionModelProvider,
      Provider<AttentionModel> attentionModelProvider,
      Provider<CommentModel> commentModelProvider,
      Provider<TagModel> tagModelProvider,
      Provider<LikeModel> likeModelProvider) {
    return new VideoDetailPresenter_Factory(
        mMvpModelProvider,
        collectionModelProvider,
        attentionModelProvider,
        commentModelProvider,
        tagModelProvider,
        likeModelProvider);
  }

  public static VideoDetailPresenter newVideoDetailPresenter(NewsModel mMvpModel) {
    return new VideoDetailPresenter(mMvpModel);
  }
}