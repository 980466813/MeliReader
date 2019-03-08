package com.lning.melireader.core.mvp;

/**
 * Created by Ning on 2018/11/15.
 */

public interface IView {

    /**
     * 显示信息
     *
     * @param msg
     */
    void showMessage(String msg);

    /**
     * 取消自定义加载框
     */
    void dismissDialog();

    /**
     * 显示自定义加载框
     *
     */
    void showDialog(String message);

    /**
     * 显示加载页面
     */
    void showLoading();

    /**
     * 显示错误/异常页面
     */
    void showError(String msg);

    /**
     * 显示空页面
     */
    void showEmpty();

    /**
     * 显示主页面
     */
    void showMain();

}
