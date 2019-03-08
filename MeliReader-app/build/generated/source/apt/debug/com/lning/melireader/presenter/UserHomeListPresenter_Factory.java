// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.CommentModel;
import com.lning.melireader.model.impl.HistoryModel;
import com.lning.melireader.model.impl.LikeModel;
import com.lning.melireader.model.impl.NewsModel;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class UserHomeListPresenter_Factory implements Factory<UserHomeListPresenter> {
  private final Provider<NewsModel> mMvpModelProvider;

  private final Provider<HistoryModel> historyModelProvider;

  private final Provider<CommentModel> commentModelProvider;

  private final Provider<LikeModel> likeModelProvider;

  public UserHomeListPresenter_Factory(
      Provider<NewsModel> mMvpModelProvider,
      Provider<HistoryModel> historyModelProvider,
      Provider<CommentModel> commentModelProvider,
      Provider<LikeModel> likeModelProvider) {
    this.mMvpModelProvider = mMvpModelProvider;
    this.historyModelProvider = historyModelProvider;
    this.commentModelProvider = commentModelProvider;
    this.likeModelProvider = likeModelProvider;
  }

  @Override
  public UserHomeListPresenter get() {
    UserHomeListPresenter instance = new UserHomeListPresenter(mMvpModelProvider.get());
    UserHomeListPresenter_MembersInjector.injectHistoryModel(instance, historyModelProvider.get());
    UserHomeListPresenter_MembersInjector.injectCommentModel(instance, commentModelProvider.get());
    UserHomeListPresenter_MembersInjector.injectLikeModel(instance, likeModelProvider.get());
    return instance;
  }

  public static UserHomeListPresenter_Factory create(
      Provider<NewsModel> mMvpModelProvider,
      Provider<HistoryModel> historyModelProvider,
      Provider<CommentModel> commentModelProvider,
      Provider<LikeModel> likeModelProvider) {
    return new UserHomeListPresenter_Factory(
        mMvpModelProvider, historyModelProvider, commentModelProvider, likeModelProvider);
  }

  public static UserHomeListPresenter newUserHomeListPresenter(NewsModel mMvpModel) {
    return new UserHomeListPresenter(mMvpModel);
  }
}
