// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.UserModel;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class UpdateDialogPresenter_Factory implements Factory<UpdateDialogPresenter> {
  private final Provider<UserModel> mMvpModelProvider;

  public UpdateDialogPresenter_Factory(Provider<UserModel> mMvpModelProvider) {
    this.mMvpModelProvider = mMvpModelProvider;
  }

  @Override
  public UpdateDialogPresenter get() {
    return new UpdateDialogPresenter(mMvpModelProvider.get());
  }

  public static UpdateDialogPresenter_Factory create(Provider<UserModel> mMvpModelProvider) {
    return new UpdateDialogPresenter_Factory(mMvpModelProvider);
  }
}