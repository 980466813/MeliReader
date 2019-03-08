// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.AttentionModel;
import com.lning.melireader.model.impl.UserModel;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class UserPresenter_Factory implements Factory<UserPresenter> {
  private final Provider<UserModel> mMvpModelProvider;

  private final Provider<AttentionModel> attentionModelProvider;

  public UserPresenter_Factory(
      Provider<UserModel> mMvpModelProvider, Provider<AttentionModel> attentionModelProvider) {
    this.mMvpModelProvider = mMvpModelProvider;
    this.attentionModelProvider = attentionModelProvider;
  }

  @Override
  public UserPresenter get() {
    UserPresenter instance = new UserPresenter(mMvpModelProvider.get());
    UserPresenter_MembersInjector.injectAttentionModel(instance, attentionModelProvider.get());
    return instance;
  }

  public static UserPresenter_Factory create(
      Provider<UserModel> mMvpModelProvider, Provider<AttentionModel> attentionModelProvider) {
    return new UserPresenter_Factory(mMvpModelProvider, attentionModelProvider);
  }

  public static UserPresenter newUserPresenter(UserModel mMvpModel) {
    return new UserPresenter(mMvpModel);
  }
}