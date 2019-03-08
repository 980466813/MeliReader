package com.lning.melireader.core.utils;

import android.content.Context;

import com.lning.melireader.core.app.base.App;
import com.lning.melireader.core.app.di.component.AppComponent;

/**
 * Created by Ning on 2018/11/18.
 */

public class DaggerUtils {
    private DaggerUtils() {
    }


    public static AppComponent obtainAppComponentFromContext(Context context) {
        return ((App) context.getApplicationContext()).getAppComponent();
    }
}
