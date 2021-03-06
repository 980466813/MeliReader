// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.app.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class GlobalConfigModule_ProvideOkHttpConfigurationFactory
    implements Factory<ClientModule.OkHttpConfiguration> {
  private final GlobalConfigModule module;

  public GlobalConfigModule_ProvideOkHttpConfigurationFactory(GlobalConfigModule module) {
    this.module = module;
  }

  @Override
  public ClientModule.OkHttpConfiguration get() {
    return Preconditions.checkNotNull(
        module.provideOkHttpConfiguration(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static GlobalConfigModule_ProvideOkHttpConfigurationFactory create(
      GlobalConfigModule module) {
    return new GlobalConfigModule_ProvideOkHttpConfigurationFactory(module);
  }

  public static ClientModule.OkHttpConfiguration proxyProvideOkHttpConfiguration(
      GlobalConfigModule instance) {
    return Preconditions.checkNotNull(
        instance.provideOkHttpConfiguration(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
