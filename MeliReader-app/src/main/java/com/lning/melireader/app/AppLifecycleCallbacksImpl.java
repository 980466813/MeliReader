package com.lning.melireader.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.lning.melireader.core.app.base.lifecycleable.AppLifecycleCallbacks;

/**
 * Created by Ning on 2018/11/21.
 */

public class AppLifecycleCallbacksImpl implements AppLifecycleCallbacks {

    @Override
    public void attachBaseContext(@NonNull Context base) {
        MultiDex.install(base);
    }

    @Override
    public void onCreate(@NonNull Application application) {

    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
