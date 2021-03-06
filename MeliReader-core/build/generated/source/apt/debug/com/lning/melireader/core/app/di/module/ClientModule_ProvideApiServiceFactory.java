// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.app.di.module;

import com.lning.melireader.core.repository.http.service.ApiService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import retrofit2.Retrofit;

public final class ClientModule_ProvideApiServiceFactory implements Factory<ApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public ClientModule_ProvideApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public ApiService get() {
    return Preconditions.checkNotNull(
        ClientModule.provideApiService(retrofitProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static ClientModule_ProvideApiServiceFactory create(Provider<Retrofit> retrofitProvider) {
    return new ClientModule_ProvideApiServiceFactory(retrofitProvider);
  }

  public static ApiService proxyProvideApiService(Retrofit retrofit) {
    return Preconditions.checkNotNull(
        ClientModule.provideApiService(retrofit),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
