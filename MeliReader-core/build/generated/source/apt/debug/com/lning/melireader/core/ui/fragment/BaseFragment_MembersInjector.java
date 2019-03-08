// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.ui.fragment;

import com.lning.melireader.core.mvp.BasePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class BaseFragment_MembersInjector<P extends BasePresenter>
    implements MembersInjector<BaseFragment<P>> {
  private final Provider<P> mPresenterProvider;

  public BaseFragment_MembersInjector(Provider<P> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static <P extends BasePresenter> MembersInjector<BaseFragment<P>> create(
      Provider<P> mPresenterProvider) {
    return new BaseFragment_MembersInjector<P>(mPresenterProvider);
  }

  @Override
  public void injectMembers(BaseFragment<P> instance) {
    injectMPresenter(instance, mPresenterProvider.get());
  }

  public static <P extends BasePresenter> void injectMPresenter(
      BaseFragment<P> instance, P mPresenter) {
    instance.mPresenter = mPresenter;
  }
}
