// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.SearchModel;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SearchResultListPresenter_Factory implements Factory<SearchResultListPresenter> {
  private final Provider<SearchModel> mMvpModelProvider;

  public SearchResultListPresenter_Factory(Provider<SearchModel> mMvpModelProvider) {
    this.mMvpModelProvider = mMvpModelProvider;
  }

  @Override
  public SearchResultListPresenter get() {
    return new SearchResultListPresenter(mMvpModelProvider.get());
  }

  public static SearchResultListPresenter_Factory create(Provider<SearchModel> mMvpModelProvider) {
    return new SearchResultListPresenter_Factory(mMvpModelProvider);
  }
}
