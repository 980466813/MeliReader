// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.ui.main.user.detail;

import com.lning.melireader.core.ui.fragment.BaseFragment_MembersInjector;
import com.lning.melireader.presenter.UserPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserFragment_MembersInjector implements MembersInjector<UserFragment> {
  private final Provider<UserPresenter> mPresenterProvider;

  public UserFragment_MembersInjector(Provider<UserPresenter> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static MembersInjector<UserFragment> create(Provider<UserPresenter> mPresenterProvider) {
    return new UserFragment_MembersInjector(mPresenterProvider);
  }

  @Override
  public void injectMembers(UserFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
  }
}
