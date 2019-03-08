// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.app.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.List;
import okhttp3.Interceptor;

public final class GlobalConfigModule_ProvideInterceptorsFactory
    implements Factory<List<Interceptor>> {
  private final GlobalConfigModule module;

  public GlobalConfigModule_ProvideInterceptorsFactory(GlobalConfigModule module) {
    this.module = module;
  }

  @Override
  public List<Interceptor> get() {
    return Preconditions.checkNotNull(
        module.provideInterceptors(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static GlobalConfigModule_ProvideInterceptorsFactory create(GlobalConfigModule module) {
    return new GlobalConfigModule_ProvideInterceptorsFactory(module);
  }

  public static List<Interceptor> proxyProvideInterceptors(GlobalConfigModule instance) {
    return Preconditions.checkNotNull(
        instance.provideInterceptors(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
