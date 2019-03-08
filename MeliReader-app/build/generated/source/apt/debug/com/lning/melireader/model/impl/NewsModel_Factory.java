// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.model.impl;

import com.lning.melireader.core.repository.RepositoryManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NewsModel_Factory implements Factory<NewsModel> {
  private final Provider<RepositoryManager> repositoryManagerProvider;

  public NewsModel_Factory(Provider<RepositoryManager> repositoryManagerProvider) {
    this.repositoryManagerProvider = repositoryManagerProvider;
  }

  @Override
  public NewsModel get() {
    return new NewsModel(repositoryManagerProvider.get());
  }

  public static NewsModel_Factory create(Provider<RepositoryManager> repositoryManagerProvider) {
    return new NewsModel_Factory(repositoryManagerProvider);
  }
}
