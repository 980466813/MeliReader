// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.HistoryModel;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class NewsListPresenter_MembersInjector implements MembersInjector<NewsListPresenter> {
  private final Provider<HistoryModel> historyModelProvider;

  public NewsListPresenter_MembersInjector(Provider<HistoryModel> historyModelProvider) {
    this.historyModelProvider = historyModelProvider;
  }

  public static MembersInjector<NewsListPresenter> create(
      Provider<HistoryModel> historyModelProvider) {
    return new NewsListPresenter_MembersInjector(historyModelProvider);
  }

  @Override
  public void injectMembers(NewsListPresenter instance) {
    injectHistoryModel(instance, historyModelProvider.get());
  }

  public static void injectHistoryModel(NewsListPresenter instance, HistoryModel historyModel) {
    instance.historyModel = historyModel;
  }
}
