// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.ui.main.user.history;

import com.lning.melireader.core.ui.fragment.BaseFragment_MembersInjector;
import com.lning.melireader.presenter.HistoryListPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public final class HistoryListFragment_MembersInjector
    implements MembersInjector<HistoryListFragment> {
  private final Provider<HistoryListPresenter> mPresenterProvider;

  private final Provider<MultiTypeAdapter> mMultiTypeAdapterProvider;

  private final Provider<Items> mItemsProvider;

  public HistoryListFragment_MembersInjector(
      Provider<HistoryListPresenter> mPresenterProvider,
      Provider<MultiTypeAdapter> mMultiTypeAdapterProvider,
      Provider<Items> mItemsProvider) {
    this.mPresenterProvider = mPresenterProvider;
    this.mMultiTypeAdapterProvider = mMultiTypeAdapterProvider;
    this.mItemsProvider = mItemsProvider;
  }

  public static MembersInjector<HistoryListFragment> create(
      Provider<HistoryListPresenter> mPresenterProvider,
      Provider<MultiTypeAdapter> mMultiTypeAdapterProvider,
      Provider<Items> mItemsProvider) {
    return new HistoryListFragment_MembersInjector(
        mPresenterProvider, mMultiTypeAdapterProvider, mItemsProvider);
  }

  @Override
  public void injectMembers(HistoryListFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
    injectMMultiTypeAdapter(instance, mMultiTypeAdapterProvider.get());
    injectMItems(instance, mItemsProvider.get());
  }

  public static void injectMMultiTypeAdapter(
      HistoryListFragment instance, MultiTypeAdapter mMultiTypeAdapter) {
    instance.mMultiTypeAdapter = mMultiTypeAdapter;
  }

  public static void injectMItems(HistoryListFragment instance, Items mItems) {
    instance.mItems = mItems;
  }
}
