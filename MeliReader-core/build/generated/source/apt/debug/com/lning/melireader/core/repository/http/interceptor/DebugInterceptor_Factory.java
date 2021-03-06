// Generated by Dagger (https://google.github.io/dagger).
package com.lning.melireader.core.repository.http.interceptor;

import com.lning.melireader.core.repository.http.log.HttpLogger;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DebugInterceptor_Factory implements Factory<DebugInterceptor> {
  private final Provider<Boolean> debugProvider;

  private final Provider<HttpLogger> loggerProvider;

  private final Provider<DebugInterceptor.Level> levelProvider;

  public DebugInterceptor_Factory(
      Provider<Boolean> debugProvider,
      Provider<HttpLogger> loggerProvider,
      Provider<DebugInterceptor.Level> levelProvider) {
    this.debugProvider = debugProvider;
    this.loggerProvider = loggerProvider;
    this.levelProvider = levelProvider;
  }

  @Override
  public DebugInterceptor get() {
    DebugInterceptor instance = new DebugInterceptor();
    DebugInterceptor_MembersInjector.injectDebug(instance, debugProvider.get());
    DebugInterceptor_MembersInjector.injectLogger(instance, loggerProvider.get());
    DebugInterceptor_MembersInjector.injectLevel(instance, levelProvider.get());
    return instance;
  }

  public static DebugInterceptor_Factory create(
      Provider<Boolean> debugProvider,
      Provider<HttpLogger> loggerProvider,
      Provider<DebugInterceptor.Level> levelProvider) {
    return new DebugInterceptor_Factory(debugProvider, loggerProvider, levelProvider);
  }

  public static DebugInterceptor newDebugInterceptor() {
    return new DebugInterceptor();
  }
}
