package com.lning.melireader.core.app.di.module;

import android.app.Application;
import android.support.v4.app.FragmentManager;

import com.lning.melireader.core.app.base.lifecycleable.DefaultActivityLifecycleCallbacks;
import com.lning.melireader.core.app.base.lifecycleable.FragmentRxLifecycleCallbacks;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.RepositoryManagerImpl;
import com.lning.melireader.core.repository.db.DatabaseHelper;
import com.lning.melireader.core.repository.db.DatabaseHelperImpl;
import com.lning.melireader.core.repository.http.HttpHelper;
import com.lning.melireader.core.repository.http.HttpHelperImpl;
import com.lning.melireader.core.repository.preference.PreferencesHelper;
import com.lning.melireader.core.repository.preference.PreferencesHelperImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Ning on 2018/11/19.
 */

@Module
public abstract class AppModule {

    @Singleton
    @Binds
    abstract DatabaseHelper provideDatabaseHelper(DatabaseHelperImpl databaseHelper);

    @Singleton
    @Binds
    abstract PreferencesHelper providePreferencesHelper(PreferencesHelperImpl preferencesHelper);

    @Singleton
    @Binds
    abstract HttpHelper provideHttpHelper(HttpHelperImpl httpHelper);

    @Singleton
    @Binds
    abstract RepositoryManager provideRepositoryManager(RepositoryManagerImpl repositoryManager);

    //    @Named("ActivityLifecycleCallbacks") // 需要同时provide两个相同返回类型的函数上时，该注解才会起作用
    //    @Named("FragmentRxLifecycleCallbacks")
    @Provides
    @Singleton
    static FragmentManager.FragmentLifecycleCallbacks provideFragmentRxLifecycleCallbacks() {
        return new FragmentRxLifecycleCallbacks();
    }

    @Provides
    static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifecycles() {
        return new ArrayList<>();
    }

    @Singleton
    @Binds
    abstract Application.ActivityLifecycleCallbacks provideActivityLifecycleCallbacks(DefaultActivityLifecycleCallbacks defaultActivityLifecycleCallbacks);
}