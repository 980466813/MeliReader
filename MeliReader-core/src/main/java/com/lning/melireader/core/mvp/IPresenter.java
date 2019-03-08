package com.lning.melireader.core.mvp;

/**
 * Created by Ning on 2018/11/15.
 */

public interface IPresenter<V extends IView,M extends IModel>{

    /**
     * 绑定View
     *
     * @param mvpView 实现MvpView的页面
     */
    void onAttach(V mvpView);

    /**
     * 解除绑定
     */
    void onDetach();

    /**
     * 统一处理请求异常
     *
     * @param apiException
     */
    void handlerApiError(Throwable apiException);

}
