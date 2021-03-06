// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.app.di.module;

import android.support.v4.app.FragmentManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.List;

public final class AppModule_ProvideFragmentLifecyclesFactory
    implements Factory<List<FragmentManager.FragmentLifecycleCallbacks>> {
  private static final AppModule_ProvideFragmentLifecyclesFactory INSTANCE =
      new AppModule_ProvideFragmentLifecyclesFactory();

  @Override
  public List<FragmentManager.FragmentLifecycleCallbacks> get() {
    return Preconditions.checkNotNull(
        AppModule.provideFragmentLifecycles(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static AppModule_ProvideFragmentLifecyclesFactory create() {
    return INSTANCE;
  }

  public static List<FragmentManager.FragmentLifecycleCallbacks> proxyProvideFragmentLifecycles() {
    return Preconditions.checkNotNull(
        AppModule.provideFragmentLifecycles(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
