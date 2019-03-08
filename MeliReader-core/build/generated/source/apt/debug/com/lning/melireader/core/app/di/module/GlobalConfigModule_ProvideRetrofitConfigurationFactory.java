// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.app.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class GlobalConfigModule_ProvideRetrofitConfigurationFactory
    implements Factory<ClientModule.RetrofitConfiguration> {
  private final GlobalConfigModule module;

  public GlobalConfigModule_ProvideRetrofitConfigurationFactory(GlobalConfigModule module) {
    this.module = module;
  }

  @Override
  public ClientModule.RetrofitConfiguration get() {
    return Preconditions.checkNotNull(
        module.provideRetrofitConfiguration(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static GlobalConfigModule_ProvideRetrofitConfigurationFactory create(
      GlobalConfigModule module) {
    return new GlobalConfigModule_ProvideRetrofitConfigurationFactory(module);
  }

  public static ClientModule.RetrofitConfiguration proxyProvideRetrofitConfiguration(
      GlobalConfigModule instance) {
    return Preconditions.checkNotNull(
        instance.provideRetrofitConfiguration(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
