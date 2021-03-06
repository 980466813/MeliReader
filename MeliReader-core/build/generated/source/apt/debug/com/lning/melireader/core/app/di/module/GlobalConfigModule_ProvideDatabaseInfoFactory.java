// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.app.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class GlobalConfigModule_ProvideDatabaseInfoFactory implements Factory<String> {
  private final GlobalConfigModule module;

  public GlobalConfigModule_ProvideDatabaseInfoFactory(GlobalConfigModule module) {
    this.module = module;
  }

  @Override
  public String get() {
    return Preconditions.checkNotNull(
        module.provideDatabaseInfo(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static GlobalConfigModule_ProvideDatabaseInfoFactory create(GlobalConfigModule module) {
    return new GlobalConfigModule_ProvideDatabaseInfoFactory(module);
  }

  public static String proxyProvideDatabaseInfo(GlobalConfigModule instance) {
    return Preconditions.checkNotNull(
        instance.provideDatabaseInfo(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
