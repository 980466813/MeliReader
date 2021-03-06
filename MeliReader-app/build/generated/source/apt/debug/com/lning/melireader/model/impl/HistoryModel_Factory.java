// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.model.impl;

import com.lning.melireader.core.repository.RepositoryManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class HistoryModel_Factory implements Factory<HistoryModel> {
  private final Provider<RepositoryManager> repositoryManagerProvider;

  public HistoryModel_Factory(Provider<RepositoryManager> repositoryManagerProvider) {
    this.repositoryManagerProvider = repositoryManagerProvider;
  }

  @Override
  public HistoryModel get() {
    return new HistoryModel(repositoryManagerProvider.get());
  }

  public static HistoryModel_Factory create(Provider<RepositoryManager> repositoryManagerProvider) {
    return new HistoryModel_Factory(repositoryManagerProvider);
  }
}
