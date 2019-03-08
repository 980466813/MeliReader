package com.lning.melireader.core.app.base.lifecycleable;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.lning.melireader.core.app.base.App;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.di.component.DaggerAppComponent;
import com.lning.melireader.core.app.di.module.ConfigModule;
import com.lning.melireader.core.app.di.module.GlobalConfigModule;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.ManifestParser;
import com.lning.melireader.core.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ning on 2019/2/5.
 */

public class AppDelegate implements App, AppLifecycles {

    private Application mApplication;
    protected AppComponent mAppComponent;

    @Inject
    protected Application.ActivityLifecycleCallbacks mActivityLifecycle;

    private List<ConfigModule> mModules;
    private List<AppLifecycleCallbacks> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();

    public AppDelegate(@NonNull Context base) {
        //用反射, 将 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
        this.mModules = new ManifestParser(base).parse();
        //遍历之前获得的集合, 执行每一个 ConfigModule 实现类的某些方法
        for (ConfigModule module : mModules) {
            //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
            module.injectAppLifecycle(base, mAppLifecycles);
            //将框架外部, 开发者实现的 Activity 的生命周期回调 (ActivityLifecycleCallbacks) 存入 mActivityLifecycles 集合 (此时还未注册回调)
            module.injectActivityLifecycle(base, mActivityLifecycles);
        }
        for (AppLifecycleCallbacks lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void attachBaseContext(@NonNull Context base) {
        //遍历 mAppLifecycles, 执行所有已注册的 AppLifecycles 的 attachBaseContext() 方法 (框架外部, 开发者扩展的逻辑)
        for (AppLifecycleCallbacks lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(@NonNull Application application) {
        this.mApplication = application;
        mAppComponent = DaggerAppComponent
                .builder()
                .application(mApplication)//提供application
                .globalConfigModule(getGlobalConfigModule(mApplication, mModules))//全局配置
                .build();
        mAppComponent.inject(this);
        this.mModules = null;
        //注册框架内部已实现的 Activity 生命周期逻辑
        LogUtils.d("TAG:" + (mActivityLifecycle == null));
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);
        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }
        //执行框架外部, 开发者扩展的 App onCreate 逻辑
        for (AppLifecycleCallbacks lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks callbacks : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(callbacks);
            }
        }
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (AppLifecycleCallbacks lifecycle : mAppLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }
        this.mAppComponent = null;
        this.mActivityLifecycle = null;
        this.mActivityLifecycles = null;
        this.mAppLifecycles = null;
        this.mApplication = null;
    }

    private GlobalConfigModule getGlobalConfigModule(Application application, List<ConfigModule> modules) {
        GlobalConfigModule.Builder builder = GlobalConfigModule
                .builder();
        //遍历 ConfigModule 集合, 给全局配置 GlobalConfigModule 添加参数
        for (ConfigModule module : modules) {
            module.applyOptions(application, builder);
        }
        return builder.build();
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppComponent,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                AppComponent.class.getName(), getClass().getName(), Application.class.getName());
        return mAppComponent;
    }
}
