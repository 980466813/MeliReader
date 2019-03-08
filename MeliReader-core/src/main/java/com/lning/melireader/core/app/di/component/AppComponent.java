package com.lning.melireader.core.app.di.component;

import android.app.Application;
import android.support.v4.app.FragmentManager;

import com.lning.melireader.core.app.base.lifecycleable.AppDelegate;
import com.lning.melireader.core.app.di.module.AppModule;
import com.lning.melireader.core.app.di.module.ClientModule;
import com.lning.melireader.core.app.di.module.GlobalConfigModule;
import com.lning.melireader.core.repository.RepositoryManager;

import java.util.List;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Ning on 2018/11/18.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, GlobalConfigModule.class})
public interface AppComponent {
    Application application();

    void inject(AppDelegate application);

    RepositoryManager repositoryManager();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder globalConfigModule(GlobalConfigModule globalConfigModule);

        AppComponent build();
    }
}
