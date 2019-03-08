// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.ui.main.user.detail;

import com.lning.melireader.core.ui.fragment.BaseFragment_MembersInjector;
import com.lning.melireader.presenter.UserHomeListPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public final class UserHomeListFragment_MembersInjector
    implements MembersInjector<UserHomeListFragment> {
  private final Provider<UserHomeListPresenter> mPresenterProvider;

  private final Provider<MultiTypeAdapter> mMultiTypeAdapterProvider;

  private final Provider<Items> mItemsProvider;

  public UserHomeListFragment_MembersInjector(
      Provider<UserHomeListPresenter> mPresenterProvider,
      Provider<MultiTypeAdapter> mMultiTypeAdapterProvider,
      Provider<Items> mItemsProvider) {
    this.mPresenterProvider = mPresenterProvider;
    this.mMultiTypeAdapterProvider = mMultiTypeAdapterProvider;
    this.mItemsProvider = mItemsProvider;
  }

  public static MembersInjector<UserHomeListFragment> create(
      Provider<UserHomeListPresenter> mPresenterProvider,
      Provider<MultiTypeAdapter> mMultiTypeAdapterProvider,
      Provider<Items> mItemsProvider) {
    return new UserHomeListFragment_MembersInjector(
        mPresenterProvider, mMultiTypeAdapterProvider, mItemsProvider);
  }

  @Override
  public void injectMembers(UserHomeListFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
    injectMMultiTypeAdapter(instance, mMultiTypeAdapterProvider.get());
    injectMItems(instance, mItemsProvider.get());
  }

  public static void injectMMultiTypeAdapter(
      UserHomeListFragment instance, MultiTypeAdapter mMultiTypeAdapter) {
    instance.mMultiTypeAdapter = mMultiTypeAdapter;
  }

  public static void injectMItems(UserHomeListFragment instance, Items mItems) {
    instance.mItems = mItems;
  }
}
