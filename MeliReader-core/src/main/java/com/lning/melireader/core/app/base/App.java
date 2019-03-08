package com.lning.melireader.core.app.base;

import android.support.annotation.NonNull;

import com.lning.melireader.core.app.di.component.AppComponent;

public interface App {
    @NonNull
    AppComponent getAppComponent();
}