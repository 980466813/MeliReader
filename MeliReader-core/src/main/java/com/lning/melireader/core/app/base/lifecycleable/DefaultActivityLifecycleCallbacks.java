package com.lning.melireader.core.app.base.lifecycleable;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.lning.melireader.core.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Lazy;

/**
 * Created by Ning on 2018/11/21.
 */

@Singleton
public class DefaultActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    Lazy<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycleCallbacks;

    Lazy<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecycles;

    @Inject
    public DefaultActivityLifecycleCallbacks(Lazy<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycleCallbacks,
                                             Lazy<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecycles) {
        this.mFragmentLifecycleCallbacks = mFragmentLifecycleCallbacks;
        this.mFragmentLifecycles = mFragmentLifecycles;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (activity instanceof FragmentActivity) {
            LogUtils.d((mFragmentLifecycleCallbacks == null) + "");
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycleCallbacks.get(), true);
            //注册框架外部, 开发者扩展的 Fragment 生命周期逻辑
            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycles.get()) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
