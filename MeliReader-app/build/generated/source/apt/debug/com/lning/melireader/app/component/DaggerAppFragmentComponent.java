// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.app.component;

import android.app.Activity;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.app.module.FragmentModule_ProvideActivityFactory;
import com.lning.melireader.app.module.FragmentModule_ProvideItemsFactory;
import com.lning.melireader.app.module.FragmentModule_ProvideMultiTypeAdapterFactory;
import com.lning.melireader.app.module.FragmentModule_ProvideRxPermissionsFactory;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.ui.fragment.BaseFragment_MembersInjector;
import com.lning.melireader.model.impl.AttentionModel;
import com.lning.melireader.model.impl.CollectionModel;
import com.lning.melireader.model.impl.CommentModel;
import com.lning.melireader.model.impl.ContentModel;
import com.lning.melireader.model.impl.FavouriteModel;
import com.lning.melireader.model.impl.HistoryModel;
import com.lning.melireader.model.impl.LikeModel;
import com.lning.melireader.model.impl.NewsModel;
import com.lning.melireader.model.impl.SearchModel;
import com.lning.melireader.model.impl.TagModel;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.presenter.AttentionCenterPresenter;
import com.lning.melireader.presenter.AttentionCenterPresenter_Factory;
import com.lning.melireader.presenter.AttentionCenterPresenter_MembersInjector;
import com.lning.melireader.presenter.CollectionListPresenter;
import com.lning.melireader.presenter.CollectionUpdatePresenter;
import com.lning.melireader.presenter.CollectionUpdatePresenter_Factory;
import com.lning.melireader.presenter.CollectionUpdatePresenter_MembersInjector;
import com.lning.melireader.presenter.HistoryListPresenter;
import com.lning.melireader.presenter.HistoryListPresenter_Factory;
import com.lning.melireader.presenter.HistoryListPresenter_MembersInjector;
import com.lning.melireader.presenter.LauncherPresenter;
import com.lning.melireader.presenter.LauncherPresenter_Factory;
import com.lning.melireader.presenter.LauncherPresenter_MembersInjector;
import com.lning.melireader.presenter.LoginPresenter;
import com.lning.melireader.presenter.NewsCommentDetailPresenter;
import com.lning.melireader.presenter.NewsCommentDetailPresenter_Factory;
import com.lning.melireader.presenter.NewsCommentDetailPresenter_MembersInjector;
import com.lning.melireader.presenter.NewsCommentPresenter;
import com.lning.melireader.presenter.NewsCommentPresenter_Factory;
import com.lning.melireader.presenter.NewsCommentPresenter_MembersInjector;
import com.lning.melireader.presenter.NewsDetailPresenter;
import com.lning.melireader.presenter.NewsDetailPresenter_Factory;
import com.lning.melireader.presenter.NewsDetailPresenter_MembersInjector;
import com.lning.melireader.presenter.NewsListPresenter;
import com.lning.melireader.presenter.NewsListPresenter_Factory;
import com.lning.melireader.presenter.NewsListPresenter_MembersInjector;
import com.lning.melireader.presenter.NewsRecommendPresenter;
import com.lning.melireader.presenter.SearchPresenter;
import com.lning.melireader.presenter.SearchResultListPresenter;
import com.lning.melireader.presenter.SettingPresenter;
import com.lning.melireader.presenter.UpdateDialogPresenter;
import com.lning.melireader.presenter.UserCenterPresenter;
import com.lning.melireader.presenter.UserHomeListPresenter;
import com.lning.melireader.presenter.UserHomeListPresenter_Factory;
import com.lning.melireader.presenter.UserHomeListPresenter_MembersInjector;
import com.lning.melireader.presenter.UserInfoPresenter;
import com.lning.melireader.presenter.UserPresenter;
import com.lning.melireader.presenter.UserPresenter_Factory;
import com.lning.melireader.presenter.UserPresenter_MembersInjector;
import com.lning.melireader.presenter.VideoDetailPresenter;
import com.lning.melireader.presenter.VideoDetailPresenter_Factory;
import com.lning.melireader.presenter.VideoDetailPresenter_MembersInjector;
import com.lning.melireader.presenter.VideoListPresenter;
import com.lning.melireader.presenter.VideoListPresenter_Factory;
import com.lning.melireader.presenter.VideoListPresenter_MembersInjector;
import com.lning.melireader.ui.LauncherFragment;
import com.lning.melireader.ui.LauncherFragment_MembersInjector;
import com.lning.melireader.ui.login.LoginFragment;
import com.lning.melireader.ui.login.LoginFragment_MembersInjector;
import com.lning.melireader.ui.main.attention.AttentionTabFragment;
import com.lning.melireader.ui.main.attention.AttentionTabFragment_MembersInjector;
import com.lning.melireader.ui.main.news.article.NewsListFragment;
import com.lning.melireader.ui.main.news.article.NewsListFragment_MembersInjector;
import com.lning.melireader.ui.main.news.comment.NewsCommentDetailFragment;
import com.lning.melireader.ui.main.news.comment.NewsCommentDetailFragment_MembersInjector;
import com.lning.melireader.ui.main.news.comment.NewsCommentFragment;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.main.news.recommend.NewsRecommendFragment;
import com.lning.melireader.ui.main.news.recommend.NewsRecommendFragment_MembersInjector;
import com.lning.melireader.ui.main.user.UserCenterFragment;
import com.lning.melireader.ui.main.user.collection.CollectionListFragment;
import com.lning.melireader.ui.main.user.collection.CollectionListFragment_MembersInjector;
import com.lning.melireader.ui.main.user.collection.CollectionUpdateFragment;
import com.lning.melireader.ui.main.user.detail.UserFragment;
import com.lning.melireader.ui.main.user.detail.UserHomeListFragment;
import com.lning.melireader.ui.main.user.detail.UserHomeListFragment_MembersInjector;
import com.lning.melireader.ui.main.user.history.HistoryListFragment;
import com.lning.melireader.ui.main.user.history.HistoryListFragment_MembersInjector;
import com.lning.melireader.ui.main.user.info.UpdateDialogFragment;
import com.lning.melireader.ui.main.user.info.UserInfoFragment;
import com.lning.melireader.ui.main.user.setting.SettingFragment;
import com.lning.melireader.ui.main.video.detail.VideoDetailFragment;
import com.lning.melireader.ui.main.video.list.VideoListFragment;
import com.lning.melireader.ui.search.SearchFragment;
import com.lning.melireader.ui.search.list.SearchResultListFragment;
import com.lning.melireader.ui.search.list.SearchResultListFragment_MembersInjector;
import com.tbruyelle.rxpermissions2.RxPermissions;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public final class DaggerAppFragmentComponent implements AppFragmentComponent {
  private AppComponent appComponent;

  private Provider<Items> provideItemsProvider;

  private Provider<Activity> provideActivityProvider;

  private Provider<MultiTypeAdapter> provideMultiTypeAdapterProvider;

  private Provider<RxPermissions> provideRxPermissionsProvider;

  private DaggerAppFragmentComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  private ContentModel getContentModel() {
    return new ContentModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private UserModel getUserModel() {
    return new UserModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private FavouriteModel getFavouriteModel() {
    return new FavouriteModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private LauncherPresenter getLauncherPresenter() {
    return injectLauncherPresenter(
        LauncherPresenter_Factory.newLauncherPresenter(getContentModel()));
  }

  private LoginPresenter getLoginPresenter() {
    return new LoginPresenter(getUserModel());
  }

  private UserCenterPresenter getUserCenterPresenter() {
    return new UserCenterPresenter(getUserModel());
  }

  private CollectionModel getCollectionModel() {
    return new CollectionModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private CollectionListPresenter getCollectionListPresenter() {
    return new CollectionListPresenter(getCollectionModel());
  }

  private HistoryModel getHistoryModel() {
    return new HistoryModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private HistoryListPresenter getHistoryListPresenter() {
    return injectHistoryListPresenter(
        HistoryListPresenter_Factory.newHistoryListPresenter(getHistoryModel()));
  }

  private TagModel getTagModel() {
    return new TagModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private CollectionUpdatePresenter getCollectionUpdatePresenter() {
    return injectCollectionUpdatePresenter(
        CollectionUpdatePresenter_Factory.newCollectionUpdatePresenter(getCollectionModel()));
  }

  private SettingPresenter getSettingPresenter() {
    return new SettingPresenter(getUserModel());
  }

  private NewsModel getNewsModel() {
    return new NewsModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private NewsListPresenter getNewsListPresenter() {
    return injectNewsListPresenter(NewsListPresenter_Factory.newNewsListPresenter(getNewsModel()));
  }

  private CommentModel getCommentModel() {
    return new CommentModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private LikeModel getLikeModel() {
    return new LikeModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private AttentionModel getAttentionModel() {
    return new AttentionModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private NewsDetailPresenter getNewsDetailPresenter() {
    return injectNewsDetailPresenter(
        NewsDetailPresenter_Factory.newNewsDetailPresenter(getNewsModel()));
  }

  private NewsRecommendPresenter getNewsRecommendPresenter() {
    return new NewsRecommendPresenter(getNewsModel());
  }

  private NewsCommentPresenter getNewsCommentPresenter() {
    return injectNewsCommentPresenter(
        NewsCommentPresenter_Factory.newNewsCommentPresenter(getCommentModel()));
  }

  private NewsCommentDetailPresenter getNewsCommentDetailPresenter() {
    return injectNewsCommentDetailPresenter(
        NewsCommentDetailPresenter_Factory.newNewsCommentDetailPresenter(getCommentModel()));
  }

  private VideoListPresenter getVideoListPresenter() {
    return injectVideoListPresenter(
        VideoListPresenter_Factory.newVideoListPresenter(getNewsModel()));
  }

  private VideoDetailPresenter getVideoDetailPresenter() {
    return injectVideoDetailPresenter(
        VideoDetailPresenter_Factory.newVideoDetailPresenter(getNewsModel()));
  }

  private SearchModel getSearchModel() {
    return new SearchModel(
        Preconditions.checkNotNull(
            appComponent.repositoryManager(),
            "Cannot return null from a non-@Nullable component method"));
  }

  private SearchPresenter getSearchPresenter() {
    return new SearchPresenter(getSearchModel());
  }

  private SearchResultListPresenter getSearchResultListPresenter() {
    return new SearchResultListPresenter(getSearchModel());
  }

  private UserPresenter getUserPresenter() {
    return injectUserPresenter(UserPresenter_Factory.newUserPresenter(getUserModel()));
  }

  private UserHomeListPresenter getUserHomeListPresenter() {
    return injectUserHomeListPresenter(
        UserHomeListPresenter_Factory.newUserHomeListPresenter(getNewsModel()));
  }

  private AttentionCenterPresenter getAttentionCenterPresenter() {
    return injectAttentionCenterPresenter(
        AttentionCenterPresenter_Factory.newAttentionCenterPresenter(getNewsModel()));
  }

  private UserInfoPresenter getUserInfoPresenter() {
    return new UserInfoPresenter(getUserModel());
  }

  private UpdateDialogPresenter getUpdateDialogPresenter() {
    return new UpdateDialogPresenter(getUserModel());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideItemsProvider =
        DoubleCheck.provider(FragmentModule_ProvideItemsFactory.create(builder.fragmentModule));
    this.provideActivityProvider =
        DoubleCheck.provider(FragmentModule_ProvideActivityFactory.create(builder.fragmentModule));
    this.provideMultiTypeAdapterProvider =
        DoubleCheck.provider(
            FragmentModule_ProvideMultiTypeAdapterFactory.create(
                builder.fragmentModule, provideItemsProvider));
    this.appComponent = builder.appComponent;
    this.provideRxPermissionsProvider =
        DoubleCheck.provider(
            FragmentModule_ProvideRxPermissionsFactory.create(builder.fragmentModule));
  }

  @Override
  public Items items() {
    return provideItemsProvider.get();
  }

  @Override
  public Activity activity() {
    return provideActivityProvider.get();
  }

  @Override
  public MultiTypeAdapter multiTypeAdapter() {
    return provideMultiTypeAdapterProvider.get();
  }

  @Override
  public void inject(LauncherFragment delegate) {
    injectLauncherFragment(delegate);
  }

  @Override
  public void inject(LoginFragment loginFragment) {
    injectLoginFragment(loginFragment);
  }

  @Override
  public void inject(UserCenterFragment userCenterFragment) {
    injectUserCenterFragment(userCenterFragment);
  }

  @Override
  public void inject(CollectionListFragment collectionListFragment) {
    injectCollectionListFragment(collectionListFragment);
  }

  @Override
  public void inject(HistoryListFragment historyListFragment) {
    injectHistoryListFragment(historyListFragment);
  }

  @Override
  public void inject(CollectionUpdateFragment collectionUpdateFragment) {
    injectCollectionUpdateFragment(collectionUpdateFragment);
  }

  @Override
  public void inject(SettingFragment settingFragment) {
    injectSettingFragment(settingFragment);
  }

  @Override
  public void inject(NewsListFragment newsListFragment) {
    injectNewsListFragment(newsListFragment);
  }

  @Override
  public void inject(NewsDetailFragment newsDetailFragment) {
    injectNewsDetailFragment(newsDetailFragment);
  }

  @Override
  public void inject(NewsRecommendFragment newsRecommendFragment) {
    injectNewsRecommendFragment(newsRecommendFragment);
  }

  @Override
  public void inject(NewsCommentFragment newsCommentFragment) {
    injectNewsCommentFragment(newsCommentFragment);
  }

  @Override
  public void inject(NewsCommentDetailFragment newsCommentDetailFragment) {
    injectNewsCommentDetailFragment(newsCommentDetailFragment);
  }

  @Override
  public void inject(VideoListFragment videoListFragment) {
    injectVideoListFragment(videoListFragment);
  }

  @Override
  public void inject(VideoDetailFragment videoDetailFragment) {
    injectVideoDetailFragment(videoDetailFragment);
  }

  @Override
  public void inject(SearchFragment searchFragment) {
    injectSearchFragment(searchFragment);
  }

  @Override
  public void inject(SearchResultListFragment searchResultListFragment) {
    injectSearchResultListFragment(searchResultListFragment);
  }

  @Override
  public void inject(UserFragment userFragment) {
    injectUserFragment(userFragment);
  }

  @Override
  public void inject(UserHomeListFragment userHomeListFragment) {
    injectUserHomeListFragment(userHomeListFragment);
  }

  @Override
  public void inject(AttentionTabFragment attentionCenterFragment) {
    injectAttentionTabFragment(attentionCenterFragment);
  }

  @Override
  public void inject(UserInfoFragment userInfoFragment) {
    injectUserInfoFragment(userInfoFragment);
  }

  @Override
  public void inject(UpdateDialogFragment updateDialogFragment) {
    injectUpdateDialogFragment(updateDialogFragment);
  }

  private LauncherPresenter injectLauncherPresenter(LauncherPresenter instance) {
    LauncherPresenter_MembersInjector.injectUserModel(instance, getUserModel());
    LauncherPresenter_MembersInjector.injectFavouriteModel(instance, getFavouriteModel());
    return instance;
  }

  private LauncherFragment injectLauncherFragment(LauncherFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getLauncherPresenter());
    LauncherFragment_MembersInjector.injectRxPermissions(
        instance, provideRxPermissionsProvider.get());
    return instance;
  }

  private LoginFragment injectLoginFragment(LoginFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getLoginPresenter());
    LoginFragment_MembersInjector.injectRxPermissions(instance, provideRxPermissionsProvider.get());
    return instance;
  }

  private UserCenterFragment injectUserCenterFragment(UserCenterFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getUserCenterPresenter());
    return instance;
  }

  private CollectionListFragment injectCollectionListFragment(CollectionListFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getCollectionListPresenter());
    CollectionListFragment_MembersInjector.injectMMultiTypeAdapter(
        instance, provideMultiTypeAdapterProvider.get());
    CollectionListFragment_MembersInjector.injectMItems(instance, provideItemsProvider.get());
    return instance;
  }

  private HistoryListPresenter injectHistoryListPresenter(HistoryListPresenter instance) {
    HistoryListPresenter_MembersInjector.injectUserModel(instance, getUserModel());
    return instance;
  }

  private HistoryListFragment injectHistoryListFragment(HistoryListFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getHistoryListPresenter());
    HistoryListFragment_MembersInjector.injectMMultiTypeAdapter(
        instance, provideMultiTypeAdapterProvider.get());
    HistoryListFragment_MembersInjector.injectMItems(instance, provideItemsProvider.get());
    return instance;
  }

  private CollectionUpdatePresenter injectCollectionUpdatePresenter(
      CollectionUpdatePresenter instance) {
    CollectionUpdatePresenter_MembersInjector.injectTagModel(instance, getTagModel());
    return instance;
  }

  private CollectionUpdateFragment injectCollectionUpdateFragment(
      CollectionUpdateFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getCollectionUpdatePresenter());
    return instance;
  }

  private SettingFragment injectSettingFragment(SettingFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getSettingPresenter());
    return instance;
  }

  private NewsListPresenter injectNewsListPresenter(NewsListPresenter instance) {
    NewsListPresenter_MembersInjector.injectHistoryModel(instance, getHistoryModel());
    return instance;
  }

  private NewsListFragment injectNewsListFragment(NewsListFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getNewsListPresenter());
    NewsListFragment_MembersInjector.injectMMultiTypeAdapter(
        instance, provideMultiTypeAdapterProvider.get());
    NewsListFragment_MembersInjector.injectMItems(instance, provideItemsProvider.get());
    return instance;
  }

  private NewsDetailPresenter injectNewsDetailPresenter(NewsDetailPresenter instance) {
    NewsDetailPresenter_MembersInjector.injectCommentModel(instance, getCommentModel());
    NewsDetailPresenter_MembersInjector.injectCollectionModel(instance, getCollectionModel());
    NewsDetailPresenter_MembersInjector.injectTagModel(instance, getTagModel());
    NewsDetailPresenter_MembersInjector.injectLikeModel(instance, getLikeModel());
    NewsDetailPresenter_MembersInjector.injectAttentionModel(instance, getAttentionModel());
    return instance;
  }

  private NewsDetailFragment injectNewsDetailFragment(NewsDetailFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getNewsDetailPresenter());
    return instance;
  }

  private NewsRecommendFragment injectNewsRecommendFragment(NewsRecommendFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getNewsRecommendPresenter());
    NewsRecommendFragment_MembersInjector.injectMMultiTypeAdapter(
        instance, provideMultiTypeAdapterProvider.get());
    NewsRecommendFragment_MembersInjector.injectMItems(instance, provideItemsProvider.get());
    return instance;
  }

  private NewsCommentPresenter injectNewsCommentPresenter(NewsCommentPresenter instance) {
    NewsCommentPresenter_MembersInjector.injectNewsModel(instance, getNewsModel());
    NewsCommentPresenter_MembersInjector.injectLikeModel(instance, getLikeModel());
    return instance;
  }

  private NewsCommentFragment injectNewsCommentFragment(NewsCommentFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getNewsCommentPresenter());
    return instance;
  }

  private NewsCommentDetailPresenter injectNewsCommentDetailPresenter(
      NewsCommentDetailPresenter instance) {
    NewsCommentDetailPresenter_MembersInjector.injectLikeModel(instance, getLikeModel());
    return instance;
  }

  private NewsCommentDetailFragment injectNewsCommentDetailFragment(
      NewsCommentDetailFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getNewsCommentDetailPresenter());
    NewsCommentDetailFragment_MembersInjector.injectMItems(instance, provideItemsProvider.get());
    NewsCommentDetailFragment_MembersInjector.injectMMultiTypeAdapter(
        instance, provideMultiTypeAdapterProvider.get());
    return instance;
  }

  private VideoListPresenter injectVideoListPresenter(VideoListPresenter instance) {
    VideoListPresenter_MembersInjector.injectHistoryModel(instance, getHistoryModel());
    VideoListPresenter_MembersInjector.injectAttentionModel(instance, getAttentionModel());
    return instance;
  }

  private VideoListFragment injectVideoListFragment(VideoListFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getVideoListPresenter());
    return instance;
  }

  private VideoDetailPresenter injectVideoDetailPresenter(VideoDetailPresenter instance) {
    VideoDetailPresenter_MembersInjector.injectCollectionModel(instance, getCollectionModel());
    VideoDetailPresenter_MembersInjector.injectAttentionModel(instance, getAttentionModel());
    VideoDetailPresenter_MembersInjector.injectCommentModel(instance, getCommentModel());
    VideoDetailPresenter_MembersInjector.injectTagModel(instance, getTagModel());
    VideoDetailPresenter_MembersInjector.injectLikeModel(instance, getLikeModel());
    return instance;
  }

  private VideoDetailFragment injectVideoDetailFragment(VideoDetailFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getVideoDetailPresenter());
    return instance;
  }

  private SearchFragment injectSearchFragment(SearchFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getSearchPresenter());
    return instance;
  }

  private SearchResultListFragment injectSearchResultListFragment(
      SearchResultListFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getSearchResultListPresenter());
    SearchResultListFragment_MembersInjector.injectMItems(instance, provideItemsProvider.get());
    SearchResultListFragment_MembersInjector.injectMMultiTypeAdapter(
        instance, provideMultiTypeAdapterProvider.get());
    return instance;
  }

  private UserPresenter injectUserPresenter(UserPresenter instance) {
    UserPresenter_MembersInjector.injectAttentionModel(instance, getAttentionModel());
    return instance;
  }

  private UserFragment injectUserFragment(UserFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getUserPresenter());
    return instance;
  }

  private UserHomeListPresenter injectUserHomeListPresenter(UserHomeListPresenter instance) {
    UserHomeListPresenter_MembersInjector.injectHistoryModel(instance, getHistoryModel());
    UserHomeListPresenter_MembersInjector.injectCommentModel(instance, getCommentModel());
    UserHomeListPresenter_MembersInjector.injectLikeModel(instance, getLikeModel());
    return instance;
  }

  private UserHomeListFragment injectUserHomeListFragment(UserHomeListFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getUserHomeListPresenter());
    UserHomeListFragment_MembersInjector.injectMMultiTypeAdapter(
        instance, provideMultiTypeAdapterProvider.get());
    UserHomeListFragment_MembersInjector.injectMItems(instance, provideItemsProvider.get());
    return instance;
  }

  private AttentionCenterPresenter injectAttentionCenterPresenter(
      AttentionCenterPresenter instance) {
    AttentionCenterPresenter_MembersInjector.injectHistoryModel(instance, getHistoryModel());
    AttentionCenterPresenter_MembersInjector.injectAttentionModel(instance, getAttentionModel());
    return instance;
  }

  private AttentionTabFragment injectAttentionTabFragment(AttentionTabFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getAttentionCenterPresenter());
    AttentionTabFragment_MembersInjector.injectMMultiTypeAdapter(
        instance, provideMultiTypeAdapterProvider.get());
    AttentionTabFragment_MembersInjector.injectMItems(instance, provideItemsProvider.get());
    return instance;
  }

  private UserInfoFragment injectUserInfoFragment(UserInfoFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getUserInfoPresenter());
    return instance;
  }

  private UpdateDialogFragment injectUpdateDialogFragment(UpdateDialogFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, getUpdateDialogPresenter());
    return instance;
  }

  public static final class Builder {
    private FragmentModule fragmentModule;

    private AppComponent appComponent;

    private Builder() {}

    public AppFragmentComponent build() {
      if (fragmentModule == null) {
        throw new IllegalStateException(FragmentModule.class.getCanonicalName() + " must be set");
      }
      if (appComponent == null) {
        throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
      }
      return new DaggerAppFragmentComponent(this);
    }

    public Builder fragmentModule(FragmentModule fragmentModule) {
      this.fragmentModule = Preconditions.checkNotNull(fragmentModule);
      return this;
    }

    public Builder appComponent(AppComponent appComponent) {
      this.appComponent = Preconditions.checkNotNull(appComponent);
      return this;
    }
  }
}