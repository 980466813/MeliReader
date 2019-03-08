// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.ui.main.video.list;

import com.lning.melireader.core.ui.fragment.BaseFragment_MembersInjector;
import com.lning.melireader.presenter.VideoListPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class VideoListFragment_MembersInjector implements MembersInjector<VideoListFragment> {
  private final Provider<VideoListPresenter> mPresenterProvider;

  public VideoListFragment_MembersInjector(Provider<VideoListPresenter> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static MembersInjector<VideoListFragment> create(
      Provider<VideoListPresenter> mPresenterProvider) {
    return new VideoListFragment_MembersInjector(mPresenterProvider);
  }

  @Override
  public void injectMembers(VideoListFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
  }
}
