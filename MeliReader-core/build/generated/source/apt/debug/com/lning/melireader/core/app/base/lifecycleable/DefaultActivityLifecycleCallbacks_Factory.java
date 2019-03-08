// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.app.base.lifecycleable;

import android.support.v4.app.FragmentManager;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.List;
import javax.inject.Provider;

public final class DefaultActivityLifecycleCallbacks_Factory
    implements Factory<DefaultActivityLifecycleCallbacks> {
  private final Provider<FragmentManager.FragmentLifecycleCallbacks>
      mFragmentLifecycleCallbacksProvider;

  private final Provider<List<FragmentManager.FragmentLifecycleCallbacks>>
      mFragmentLifecyclesProvider;

  public DefaultActivityLifecycleCallbacks_Factory(
      Provider<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycleCallbacksProvider,
      Provider<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecyclesProvider) {
    this.mFragmentLifecycleCallbacksProvider = mFragmentLifecycleCallbacksProvider;
    this.mFragmentLifecyclesProvider = mFragmentLifecyclesProvider;
  }

  @Override
  public DefaultActivityLifecycleCallbacks get() {
    return new DefaultActivityLifecycleCallbacks(
        DoubleCheck.lazy(mFragmentLifecycleCallbacksProvider),
        DoubleCheck.lazy(mFragmentLifecyclesProvider));
  }

  public static DefaultActivityLifecycleCallbacks_Factory create(
      Provider<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycleCallbacksProvider,
      Provider<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecyclesProvider) {
    return new DefaultActivityLifecycleCallbacks_Factory(
        mFragmentLifecycleCallbacksProvider, mFragmentLifecyclesProvider);
  }
}
