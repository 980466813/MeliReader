// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.UserModel;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class HistoryListPresenter_MembersInjector
    implements MembersInjector<HistoryListPresenter> {
  private final Provider<UserModel> userModelProvider;

  public HistoryListPresenter_MembersInjector(Provider<UserModel> userModelProvider) {
    this.userModelProvider = userModelProvider;
  }

  public static MembersInjector<HistoryListPresenter> create(
      Provider<UserModel> userModelProvider) {
    return new HistoryListPresenter_MembersInjector(userModelProvider);
  }

  @Override
  public void injectMembers(HistoryListPresenter instance) {
    injectUserModel(instance, userModelProvider.get());
  }

  public static void injectUserModel(HistoryListPresenter instance, UserModel userModel) {
    instance.userModel = userModel;
  }
}
