// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.presenter;

import com.lning.melireader.model.impl.CollectionModel;
import com.lning.melireader.model.impl.TagModel;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CollectionUpdatePresenter_Factory implements Factory<CollectionUpdatePresenter> {
  private final Provider<CollectionModel> mMvpModelProvider;

  private final Provider<TagModel> tagModelProvider;

  public CollectionUpdatePresenter_Factory(
      Provider<CollectionModel> mMvpModelProvider, Provider<TagModel> tagModelProvider) {
    this.mMvpModelProvider = mMvpModelProvider;
    this.tagModelProvider = tagModelProvider;
  }

  @Override
  public CollectionUpdatePresenter get() {
    CollectionUpdatePresenter instance = new CollectionUpdatePresenter(mMvpModelProvider.get());
    CollectionUpdatePresenter_MembersInjector.injectTagModel(instance, tagModelProvider.get());
    return instance;
  }

  public static CollectionUpdatePresenter_Factory create(
      Provider<CollectionModel> mMvpModelProvider, Provider<TagModel> tagModelProvider) {
    return new CollectionUpdatePresenter_Factory(mMvpModelProvider, tagModelProvider);
  }

  public static CollectionUpdatePresenter newCollectionUpdatePresenter(CollectionModel mMvpModel) {
    return new CollectionUpdatePresenter(mMvpModel);
  }
}
