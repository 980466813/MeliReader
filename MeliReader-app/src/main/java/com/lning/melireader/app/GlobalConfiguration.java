package com.lning.melireader.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.lning.melireader.core.app.base.lifecycleable.AppLifecycleCallbacks;
import com.lning.melireader.core.app.di.module.ClientModule;
import com.lning.melireader.core.app.di.module.ConfigModule;
import com.lning.melireader.core.app.di.module.GlobalConfigModule;
import com.lning.melireader.core.repository.http.interceptor.DebugInterceptor;
import com.lning.melireader.core.repository.http.interceptor.RequestInterceptor;
import com.lning.melireader.core.repository.http.log.HttpLogger;

import java.util.List;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Ning on 2018/11/21.192.168.43.124
 */
//192.168.0.101
public class GlobalConfiguration implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        builder
                .runUrlManager(true)
                .debug(true)
                .validate(true)
                .startAdvancedUrl("http://10.0.2.2:8083/rest/")
                .multiDomain("SSO_URL", "http://10.0.2.2:8082/")
                .multiDomain("SEARCH_URL", "http://10.0.2.2:8081/search/")
                .preferencesInfo("MELI_SP")
                .databaseInfo("MELI_DB")
                .level(DebugInterceptor.Level.HEADER)
       /*         .httpLogger(new HttpLogger() {
                    @Override
                    public void printHeaders(String contentType, long contentLength, Headers headers) {
                        Log.d("TAG", "printHeaders");
                    }

                    @Override
                    public void printRequest(String protocol, String method, String params) {
                        Log.d("TAG", "printRequest");
                    }

                    @Override
                    public void printResponse(long spendTime, long contentLength, int code, String message) {
                        Log.d("TAG", "printResponse");
                    }
                })*/
                .okHttpConfiguration(new ClientModule.OkHttpConfiguration() {
                    @Override
                    public void configOkHttp(Context context, OkHttpClient.Builder builder) {

                    }
                })
                .retrofitConfiguration(new ClientModule.RetrofitConfiguration() {
                    @Override
                    public void configRetrofit(Context context, Retrofit.Builder builder) {

                    }
                });

    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycleCallbacks> lifecycles) {
        lifecycles.add(new AppLifecycleCallbacksImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }
}
