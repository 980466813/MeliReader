// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.app.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import okhttp3.OkHttpClient;

public final class ClientModule_ProvideClientBuilderFactory
    implements Factory<OkHttpClient.Builder> {
  private static final ClientModule_ProvideClientBuilderFactory INSTANCE =
      new ClientModule_ProvideClientBuilderFactory();

  @Override
  public OkHttpClient.Builder get() {
    return Preconditions.checkNotNull(
        ClientModule.provideClientBuilder(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static ClientModule_ProvideClientBuilderFactory create() {
    return INSTANCE;
  }

  public static OkHttpClient.Builder proxyProvideClientBuilder() {
    return Preconditions.checkNotNull(
        ClientModule.provideClientBuilder(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}