// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.model.impl;

import com.lning.melireader.core.repository.RepositoryManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CollectionModel_Factory implements Factory<CollectionModel> {
  private final Provider<RepositoryManager> repositoryManagerProvider;

  public CollectionModel_Factory(Provider<RepositoryManager> repositoryManagerProvider) {
    this.repositoryManagerProvider = repositoryManagerProvider;
  }

  @Override
  public CollectionModel get() {
    return new CollectionModel(repositoryManagerProvider.get());
  }

  public static CollectionModel_Factory create(
      Provider<RepositoryManager> repositoryManagerProvider) {
    return new CollectionModel_Factory(repositoryManagerProvider);
  }
}
