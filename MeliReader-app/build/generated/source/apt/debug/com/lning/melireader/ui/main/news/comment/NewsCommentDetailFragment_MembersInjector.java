// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.ui.main.news.comment;

import com.lning.melireader.core.ui.fragment.BaseFragment_MembersInjector;
import com.lning.melireader.presenter.NewsCommentDetailPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public final class NewsCommentDetailFragment_MembersInjector
    implements MembersInjector<NewsCommentDetailFragment> {
  private final Provider<NewsCommentDetailPresenter> mPresenterProvider;

  private final Provider<Items> mItemsProvider;

  private final Provider<MultiTypeAdapter> mMultiTypeAdapterProvider;

  public NewsCommentDetailFragment_MembersInjector(
      Provider<NewsCommentDetailPresenter> mPresenterProvider,
      Provider<Items> mItemsProvider,
      Provider<MultiTypeAdapter> mMultiTypeAdapterProvider) {
    this.mPresenterProvider = mPresenterProvider;
    this.mItemsProvider = mItemsProvider;
    this.mMultiTypeAdapterProvider = mMultiTypeAdapterProvider;
  }

  public static MembersInjector<NewsCommentDetailFragment> create(
      Provider<NewsCommentDetailPresenter> mPresenterProvider,
      Provider<Items> mItemsProvider,
      Provider<MultiTypeAdapter> mMultiTypeAdapterProvider) {
    return new NewsCommentDetailFragment_MembersInjector(
        mPresenterProvider, mItemsProvider, mMultiTypeAdapterProvider);
  }

  @Override
  public void injectMembers(NewsCommentDetailFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
    injectMItems(instance, mItemsProvider.get());
    injectMMultiTypeAdapter(instance, mMultiTypeAdapterProvider.get());
  }

  public static void injectMItems(NewsCommentDetailFragment instance, Items mItems) {
    instance.mItems = mItems;
  }

  public static void injectMMultiTypeAdapter(
      NewsCommentDetailFragment instance, MultiTypeAdapter mMultiTypeAdapter) {
    instance.mMultiTypeAdapter = mMultiTypeAdapter;
  }
}
