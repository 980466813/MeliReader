package com.lning.melireader.core.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.lning.melireader.core.app.base.App;
import com.lning.melireader.core.app.base.lifecycleable.AppDelegate;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.utils.Preconditions;

/**
 * Created by Ning on 2018/11/15.
 */
public class MRApplication extends Application
        implements App {

    private AppDelegate mAppDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (mAppDelegate != null)
            this.mAppDelegate.onCreate(this);
//        Fragmentation.builder()
//                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
//                /**
//                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
//                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
//                 */
//                .handleException(new ExceptionHandler() {
//                    @Override
//                    public void onException(Exception e) {
//                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
//                        // Bugtags.sendException(e);
//                    }
//                })
//                .install();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s", AppDelegate.class.getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }
}
