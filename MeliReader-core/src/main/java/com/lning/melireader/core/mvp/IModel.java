package com.lning.melireader.core.mvp;

import io.reactivex.disposables.Disposable;

/**
 * Created by Ning on 2018/11/15.
 */

public interface IModel {

    /**
     * 是否可以取消请求
     *
     * @return
     */
    boolean canCancelable();

    /**
     * 统一管理Retrofit请求生命周期
     *
     * @param disposable
     */
    void addSubscribe(Disposable disposable);

    /**
     * 取消订阅
     */
    void cancelSubscribe();

    /**
     *
     */
    void onDestroy();
}
