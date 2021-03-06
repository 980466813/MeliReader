// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.ui.login;

import com.lning.melireader.core.ui.fragment.BaseFragment_MembersInjector;
import com.lning.melireader.presenter.LoginPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class LoginFragment_MembersInjector implements MembersInjector<LoginFragment> {
  private final Provider<LoginPresenter> mPresenterProvider;

  private final Provider<RxPermissions> rxPermissionsProvider;

  public LoginFragment_MembersInjector(
      Provider<LoginPresenter> mPresenterProvider, Provider<RxPermissions> rxPermissionsProvider) {
    this.mPresenterProvider = mPresenterProvider;
    this.rxPermissionsProvider = rxPermissionsProvider;
  }

  public static MembersInjector<LoginFragment> create(
      Provider<LoginPresenter> mPresenterProvider, Provider<RxPermissions> rxPermissionsProvider) {
    return new LoginFragment_MembersInjector(mPresenterProvider, rxPermissionsProvider);
  }

  @Override
  public void injectMembers(LoginFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
    injectRxPermissions(instance, rxPermissionsProvider.get());
  }

  public static void injectRxPermissions(LoginFragment instance, RxPermissions rxPermissions) {
    instance.rxPermissions = rxPermissions;
  }
}
