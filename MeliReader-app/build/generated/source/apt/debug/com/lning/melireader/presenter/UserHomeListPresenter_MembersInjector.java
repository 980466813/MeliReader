// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.CommentModel;
import com.lning.melireader.model.impl.HistoryModel;
import com.lning.melireader.model.impl.LikeModel;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserHomeListPresenter_MembersInjector
    implements MembersInjector<UserHomeListPresenter> {
  private final Provider<HistoryModel> historyModelProvider;

  private final Provider<CommentModel> commentModelProvider;

  private final Provider<LikeModel> likeModelProvider;

  public UserHomeListPresenter_MembersInjector(
      Provider<HistoryModel> historyModelProvider,
      Provider<CommentModel> commentModelProvider,
      Provider<LikeModel> likeModelProvider) {
    this.historyModelProvider = historyModelProvider;
    this.commentModelProvider = commentModelProvider;
    this.likeModelProvider = likeModelProvider;
  }

  public static MembersInjector<UserHomeListPresenter> create(
      Provider<HistoryModel> historyModelProvider,
      Provider<CommentModel> commentModelProvider,
      Provider<LikeModel> likeModelProvider) {
    return new UserHomeListPresenter_MembersInjector(
        historyModelProvider, commentModelProvider, likeModelProvider);
  }

  @Override
  public void injectMembers(UserHomeListPresenter instance) {
    injectHistoryModel(instance, historyModelProvider.get());
    injectCommentModel(instance, commentModelProvider.get());
    injectLikeModel(instance, likeModelProvider.get());
  }

  public static void injectHistoryModel(UserHomeListPresenter instance, HistoryModel historyModel) {
    instance.historyModel = historyModel;
  }

  public static void injectCommentModel(UserHomeListPresenter instance, CommentModel commentModel) {
    instance.commentModel = commentModel;
  }

  public static void injectLikeModel(UserHomeListPresenter instance, LikeModel likeModel) {
    instance.likeModel = likeModel;
  }
}
