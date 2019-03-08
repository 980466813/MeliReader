// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.ui.search;

import com.lning.melireader.core.ui.fragment.BaseFragment_MembersInjector;
import com.lning.melireader.presenter.SearchPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class SearchFragment_MembersInjector implements MembersInjector<SearchFragment> {
  private final Provider<SearchPresenter> mPresenterProvider;

  public SearchFragment_MembersInjector(Provider<SearchPresenter> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static MembersInjector<SearchFragment> create(
      Provider<SearchPresenter> mPresenterProvider) {
    return new SearchFragment_MembersInjector(mPresenterProvider);
  }

  @Override
  public void injectMembers(SearchFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
  }
}