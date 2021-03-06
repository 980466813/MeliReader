// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.model.impl;

import com.lning.melireader.core.repository.RepositoryManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SearchModel_Factory implements Factory<SearchModel> {
  private final Provider<RepositoryManager> repositoryManagerProvider;

  public SearchModel_Factory(Provider<RepositoryManager> repositoryManagerProvider) {
    this.repositoryManagerProvider = repositoryManagerProvider;
  }

  @Override
  public SearchModel get() {
    return new SearchModel(repositoryManagerProvider.get());
  }

  public static SearchModel_Factory create(Provider<RepositoryManager> repositoryManagerProvider) {
    return new SearchModel_Factory(repositoryManagerProvider);
  }
}
