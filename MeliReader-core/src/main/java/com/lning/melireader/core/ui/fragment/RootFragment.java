package com.lning.melireader.core.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lning.melireader.core.R;
import com.lning.melireader.core.mvp.BasePresenter;

/**
 * Created by Ning on 2018/11/15.
 */

public abstract class RootFragment<P extends BasePresenter>
        extends BaseFragment<P> {

    public static final int STATE_LOADING = 0x11;
    public static final int STATE_ERROR = 0x12;
    public static final int STATE_EMPTY = 0x13;

    public static final int STATE_MAIN = 0x14;
    protected int mCurrentState = STATE_MAIN;

    protected View mViewError;
    protected View mViewEmpty;
    protected View mViewLoading;
    protected ViewGroup mViewMain;
    protected ViewGroup mParent;
    protected int mErrorResource = R.layout.view_error;
    protected int mEmptyResource = R.layout.view_empty;
    protected boolean isErrorViewAdded = false;
    protected boolean isEmptyViewAdded = false;


    @Override
    protected void init(@Nullable Bundle savedInstanceState, View rootView) {
        mViewMain = getView().findViewById(R.id.view_main);
        if (mViewMain == null) {
            throw new IllegalStateException(
                    "The subclass of RootActivity must contain a View named 'view_main'.");
        }
        if (!(mViewMain.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException(
                    "view_main's ParentView should be a ViewGroup.");
        }
        mParent = (ViewGroup) mViewMain.getParent();
        View.inflate(getContext(), R.layout.view_progress, mParent);
        mViewLoading = mParent.findViewById(R.id.view_loading);
        mViewLoading.setVisibility(View.GONE);
        mViewMain.setVisibility(View.VISIBLE);
    }

    /**
     * 显示主页面
     */
    @Override
    public void showMain() {
        if (mCurrentState == STATE_MAIN)
            return;
        hideCurrentPage();
        mCurrentState = STATE_MAIN;
        mViewMain.setVisibility(View.VISIBLE);
    }

    /**
     * 显示加载页面
     */
    @Override
    public void showLoading() {
        if (mCurrentState == STATE_LOADING)
            return;
        hideCurrentPage();
        mCurrentState = STATE_LOADING;
        mViewLoading.setVisibility(View.VISIBLE);
//        mProgressDialog = CommonUtils.showLoadingDialog(getContext());
    }

    // 显示请求错误页面
    @Override
    public void showError(String msg) {
        if (mCurrentState == STATE_ERROR)
            return;
        if (!isErrorViewAdded) {
            isErrorViewAdded = true;
            View.inflate(mContext, mErrorResource, mParent);
            mViewError = mParent.findViewById(R.id.view_error);
            if (!TextUtils.isEmpty(msg)) {
                TextView textView = mParent.findViewById(R.id.view_error_msg);
                textView.setText(msg);
            }
            if (mViewError == null) {
                throw new IllegalStateException(
                        "A View should be named 'view_error' in ErrorLayoutResource.");
            }
            mViewError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetryClick();
                }
            });
        }
        hideCurrentPage();
        mCurrentState = STATE_ERROR;
        mViewError.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏当前页面
     */
    protected void hideCurrentPage() {
        switch (mCurrentState) {
            case STATE_MAIN:
                mViewMain.setVisibility(View.GONE);
                break;
            case STATE_LOADING:
                mViewLoading.setVisibility(View.GONE);
                break;
            case STATE_ERROR:
                if (mViewError != null) {
                    mViewError.setVisibility(View.GONE);
                }
                break;
            case STATE_EMPTY:
                if (mViewEmpty != null) {
                    mViewEmpty.setVisibility(View.GONE);
                }
        }
    }

    @Override
    public void showEmpty() {
        if (mCurrentState == STATE_EMPTY)
            return;
        if (!isEmptyViewAdded) {
            isEmptyViewAdded = true;
            View.inflate(mContext, mEmptyResource, mParent);
            mViewEmpty = mParent.findViewById(R.id.view_empty);
            if (mViewEmpty == null) {
                throw new IllegalStateException(
                        "A View should be named 'view_empty' in ErrorLayoutResource.");
            }
            mViewEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetryClick();
                }
            });
        }
        hideCurrentPage();
        mCurrentState = STATE_EMPTY;
        mViewEmpty.setVisibility(View.VISIBLE);
    }

    protected void onRetryClick() {
    }
}
