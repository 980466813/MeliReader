// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.AttentionModel;
import com.lning.melireader.model.impl.CollectionModel;
import com.lning.melireader.model.impl.CommentModel;
import com.lning.melireader.model.impl.LikeModel;
import com.lning.melireader.model.impl.TagModel;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class NewsDetailPresenter_MembersInjector
    implements MembersInjector<NewsDetailPresenter> {
  private final Provider<CommentModel> commentModelProvider;

  private final Provider<CollectionModel> collectionModelProvider;

  private final Provider<TagModel> tagModelProvider;

  private final Provider<LikeModel> likeModelProvider;

  private final Provider<AttentionModel> attentionModelProvider;

  public NewsDetailPresenter_MembersInjector(
      Provider<CommentModel> commentModelProvider,
      Provider<CollectionModel> collectionModelProvider,
      Provider<TagModel> tagModelProvider,
      Provider<LikeModel> likeModelProvider,
      Provider<AttentionModel> attentionModelProvider) {
    this.commentModelProvider = commentModelProvider;
    this.collectionModelProvider = collectionModelProvider;
    this.tagModelProvider = tagModelProvider;
    this.likeModelProvider = likeModelProvider;
    this.attentionModelProvider = attentionModelProvider;
  }

  public static MembersInjector<NewsDetailPresenter> create(
      Provider<CommentModel> commentModelProvider,
      Provider<CollectionModel> collectionModelProvider,
      Provider<TagModel> tagModelProvider,
      Provider<LikeModel> likeModelProvider,
      Provider<AttentionModel> attentionModelProvider) {
    return new NewsDetailPresenter_MembersInjector(
        commentModelProvider,
        collectionModelProvider,
        tagModelProvider,
        likeModelProvider,
        attentionModelProvider);
  }

  @Override
  public void injectMembers(NewsDetailPresenter instance) {
    injectCommentModel(instance, commentModelProvider.get());
    injectCollectionModel(instance, collectionModelProvider.get());
    injectTagModel(instance, tagModelProvider.get());
    injectLikeModel(instance, likeModelProvider.get());
    injectAttentionModel(instance, attentionModelProvider.get());
  }

  public static void injectCommentModel(NewsDetailPresenter instance, CommentModel commentModel) {
    instance.commentModel = commentModel;
  }

  public static void injectCollectionModel(
      NewsDetailPresenter instance, CollectionModel collectionModel) {
    instance.collectionModel = collectionModel;
  }

  public static void injectTagModel(NewsDetailPresenter instance, TagModel tagModel) {
    instance.tagModel = tagModel;
  }

  public static void injectLikeModel(NewsDetailPresenter instance, LikeModel likeModel) {
    instance.likeModel = likeModel;
  }

  public static void injectAttentionModel(
      NewsDetailPresenter instance, AttentionModel attentionModel) {
    instance.attentionModel = attentionModel;
  }
}
