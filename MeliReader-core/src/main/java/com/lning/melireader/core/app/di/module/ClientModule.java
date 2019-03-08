package com.lning.melireader.core.app.di.module;

import android.app.Application;
import android.content.Context;

import com.lning.melireader.annotation.qualifier.DatabaseInfo;
import com.lning.melireader.core.repository.db.dao.DaoMaster;
import com.lning.melireader.core.repository.db.dao.DaoSession;
import com.lning.melireader.core.repository.db.manager.DatabaseOpenHelper;
import com.lning.melireader.core.repository.http.interceptor.DebugInterceptor;
import com.lning.melireader.core.repository.http.interceptor.RequestInterceptor;
import com.lning.melireader.core.repository.http.service.ApiService;
import com.lning.melireader.core.repository.http.url.UrlManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Ning on 2018/11/19.
 */

@Module
public abstract class ClientModule {

    @Named("DebugInterceptor")
    @Singleton
    @Binds
    abstract Interceptor provideDebugInterceptor(DebugInterceptor interceptor);

    @Named("RequestInterceptor")
    @Singleton
    @Binds
    abstract Interceptor provideRequestInterceptor(RequestInterceptor interceptor);

    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    static OkHttpClient.Builder provideClientBuilder() {
        return UrlManager.getInstance().with(new OkHttpClient.Builder());
    }

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(Application application, OkHttpClient.Builder builder, OkHttpConfiguration configuration, @Named("RequestInterceptor") Interceptor requestInterceptor,
                                            @Named("DebugInterceptor") Interceptor debugInterceptor,
                                            List<Interceptor> interceptors) {
        builder.addInterceptor(requestInterceptor)
                .addInterceptor(debugInterceptor)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS);
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        if (configuration != null) {
            configuration.configOkHttp(application, builder);
        }
        return builder.build();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(Application application, Retrofit.Builder builder,
                                    HttpUrl httpUrl, OkHttpClient okHttpClient, RetrofitConfiguration configuration) {
        if (configuration != null) {
            configuration.configRetrofit(application, builder);
        }
        return builder.baseUrl(httpUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    static DaoSession provideDaoSession(Application application, @DatabaseInfo String dbName) {
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(application, dbName);
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
        return daoMaster.newSession();
    }

    public interface RetrofitConfiguration {
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    public interface OkHttpConfiguration {
        void configOkHttp(Context context, OkHttpClient.Builder builder);
    }
}
