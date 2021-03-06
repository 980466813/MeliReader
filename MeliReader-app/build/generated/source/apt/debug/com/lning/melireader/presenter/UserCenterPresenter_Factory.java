// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.UserModel;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class UserCenterPresenter_Factory implements Factory<UserCenterPresenter> {
  private final Provider<UserModel> mMvpModelProvider;

  public UserCenterPresenter_Factory(Provider<UserModel> mMvpModelProvider) {
    this.mMvpModelProvider = mMvpModelProvider;
  }

  @Override
  public UserCenterPresenter get() {
    return new UserCenterPresenter(mMvpModelProvider.get());
  }

  public static UserCenterPresenter_Factory create(Provider<UserModel> mMvpModelProvider) {
    return new UserCenterPresenter_Factory(mMvpModelProvider);
  }
}
