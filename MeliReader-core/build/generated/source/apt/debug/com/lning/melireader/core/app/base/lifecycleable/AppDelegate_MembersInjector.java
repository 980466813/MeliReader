// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.app.base.lifecycleable;

import android.app.Application;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class AppDelegate_MembersInjector implements MembersInjector<AppDelegate> {
  private final Provider<Application.ActivityLifecycleCallbacks> mActivityLifecycleProvider;

  public AppDelegate_MembersInjector(
      Provider<Application.ActivityLifecycleCallbacks> mActivityLifecycleProvider) {
    this.mActivityLifecycleProvider = mActivityLifecycleProvider;
  }

  public static MembersInjector<AppDelegate> create(
      Provider<Application.ActivityLifecycleCallbacks> mActivityLifecycleProvider) {
    return new AppDelegate_MembersInjector(mActivityLifecycleProvider);
  }

  @Override
  public void injectMembers(AppDelegate instance) {
    injectMActivityLifecycle(instance, mActivityLifecycleProvider.get());
  }

  public static void injectMActivityLifecycle(
      AppDelegate instance, Application.ActivityLifecycleCallbacks mActivityLifecycle) {
    instance.mActivityLifecycle = mActivityLifecycle;
  }
}
