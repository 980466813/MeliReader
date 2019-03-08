package com.lning.melireader.app.component;


import android.app.Activity;

import com.lning.melireader.annotation.scope.FragmentScope;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.ui.LauncherFragment;
import com.lning.melireader.ui.login.LoginFragment;
import com.lning.melireader.ui.main.attention.AttentionTabFragment;
import com.lning.melireader.ui.main.news.article.NewsListFragment;
import com.lning.melireader.ui.main.news.comment.NewsCommentDetailFragment;
import com.lning.melireader.ui.main.news.comment.NewsCommentFragment;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.main.news.recommend.NewsRecommendFragment;
import com.lning.melireader.ui.main.user.UserCenterFragment;
import com.lning.melireader.ui.main.user.info.UpdateDialogFragment;
import com.lning.melireader.ui.main.user.info.UserInfoFragment;
import com.lning.melireader.ui.main.user.collection.CollectionListFragment;
import com.lning.melireader.ui.main.user.collection.CollectionUpdateFragment;
import com.lning.melireader.ui.main.user.detail.UserFragment;
import com.lning.melireader.ui.main.user.detail.UserHomeListFragment;
import com.lning.melireader.ui.main.user.history.HistoryListFragment;
import com.lning.melireader.ui.main.user.setting.SettingFragment;
import com.lning.melireader.ui.main.video.detail.VideoDetailFragment;
import com.lning.melireader.ui.main.video.list.VideoListFragment;
import com.lning.melireader.ui.search.SearchFragment;
import com.lning.melireader.ui.search.list.SearchResultListFragment;

import dagger.Component;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Ning on 2018/11/20.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = {FragmentModule.class})
public interface AppFragmentComponent {

    Items items();

    Activity activity();

    MultiTypeAdapter multiTypeAdapter();

    void inject(LauncherFragment delegate);

    void inject(LoginFragment loginFragment);

    void inject(UserCenterFragment userCenterFragment);

    void inject(CollectionListFragment collectionListFragment);

    void inject(HistoryListFragment historyListFragment);

    void inject(CollectionUpdateFragment collectionUpdateFragment);

    void inject(SettingFragment settingFragment);

    void inject(NewsListFragment newsListFragment);

    void inject(NewsDetailFragment newsDetailFragment);

    void inject(NewsRecommendFragment newsRecommendFragment);

    void inject(NewsCommentFragment newsCommentFragment);

    void inject(NewsCommentDetailFragment newsCommentDetailFragment);

    void inject(VideoListFragment videoListFragment);

    void inject(VideoDetailFragment videoDetailFragment);

    void inject(SearchFragment searchFragment);

    void inject(SearchResultListFragment searchResultListFragment);

    void inject(UserFragment userFragment);

    void inject(UserHomeListFragment userHomeListFragment);

    void inject(AttentionTabFragment attentionCenterFragment);

    void inject(UserInfoFragment userInfoFragment);

    void inject(UpdateDialogFragment updateDialogFragment);
}
