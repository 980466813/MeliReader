// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.NewsModel;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NewsRecommendPresenter_Factory implements Factory<NewsRecommendPresenter> {
  private final Provider<NewsModel> mMvpModelProvider;

  public NewsRecommendPresenter_Factory(Provider<NewsModel> mMvpModelProvider) {
    this.mMvpModelProvider = mMvpModelProvider;
  }

  @Override
  public NewsRecommendPresenter get() {
    return new NewsRecommendPresenter(mMvpModelProvider.get());
  }

  public static NewsRecommendPresenter_Factory create(Provider<NewsModel> mMvpModelProvider) {
    return new NewsRecommendPresenter_Factory(mMvpModelProvider);
  }
}
