// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.app.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import okhttp3.HttpUrl;

public final class GlobalConfigModule_ProvideHttpUrlFactory implements Factory<HttpUrl> {
  private final GlobalConfigModule module;

  public GlobalConfigModule_ProvideHttpUrlFactory(GlobalConfigModule module) {
    this.module = module;
  }

  @Override
  public HttpUrl get() {
    return Preconditions.checkNotNull(
        module.provideHttpUrl(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static GlobalConfigModule_ProvideHttpUrlFactory create(GlobalConfigModule module) {
    return new GlobalConfigModule_ProvideHttpUrlFactory(module);
  }

  public static HttpUrl proxyProvideHttpUrl(GlobalConfigModule instance) {
    return Preconditions.checkNotNull(
        instance.provideHttpUrl(), "Cannot return null from a non-@Nullable @Provides method");
  }
}