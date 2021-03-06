// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.UserModel;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SettingPresenter_Factory implements Factory<SettingPresenter> {
  private final Provider<UserModel> mMvpModelProvider;

  public SettingPresenter_Factory(Provider<UserModel> mMvpModelProvider) {
    this.mMvpModelProvider = mMvpModelProvider;
  }

  @Override
  public SettingPresenter get() {
    return new SettingPresenter(mMvpModelProvider.get());
  }

  public static SettingPresenter_Factory create(Provider<UserModel> mMvpModelProvider) {
    return new SettingPresenter_Factory(mMvpModelProvider);
  }
}
