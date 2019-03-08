package com.lning.melireader.core.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.lning.melireader.core.R;
import com.lning.melireader.core.app.base.lifecycleable.FragmentLifecycleable;
import com.lning.melireader.core.app.constant.TokenInvalid;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.mvp.IView;
import com.lning.melireader.core.ui.activity.SimpleActivity;
import com.lning.melireader.core.utils.DaggerUtils;
import com.lning.melireader.core.utils.DialogUtils;
import com.lning.melireader.core.utils.NetworkUtils;
import com.lning.melireader.core.utils.ToastUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by Ning on 2018/11/15.
 */

public abstract class BaseFragment<P extends BasePresenter> extends SwipeBackFragment
        implements IView, FragmentLifecycleable, Toolbar.OnMenuItemClickListener {

    private BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();

    protected ProgressDialog mProgressDialog;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected Unbinder mUnBinder;
    protected boolean isInited = false;
    private Toolbar mToolbar;
    private AppCompatTextView mToolbarTitle;
    private ImageButton mToolbarLeftBtn;
    private ImageButton mToolbarRightBtn;
    private AppCompatTextView mToolbarRightTv;

    @Inject
    @Nullable
    protected P mPresenter; // 处理业务请求以及控制页面显示

    @Override
    public void onAttach(Context context) {
        this.mActivity = (Activity) context;
        this.mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        setupFragmentComponent(DaggerUtils.obtainAppComponentFromContext(mActivity));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        mUnBinder = ButterKnife.bind(this, mView);
        return attachToSwipeBack(mView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        isInited = true;
        setSwipeBackEnable(false);
        init(savedInstanceState, mView);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onAttach(this);
        }
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.show(getContext(), msg);
    }

    @Override
    public void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void showDialog(String message) {
        dismissDialog();
        mProgressDialog = DialogUtils.showLoadingDialog(getContext(), message);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String msg) {
        ToastUtils.show(getContext(), msg);
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showMain() {

    }

    protected void initToolbar(View rootView, String title, @DrawableRes int leftResId, String rightStr) {
        mToolbar = rootView.findViewById(R.id.toolbar);
        mToolbarTitle = rootView.findViewById(R.id.toolbar_title);
        mToolbarLeftBtn = rootView.findViewById(R.id.toolbar_left_btn);
        mToolbarRightTv = rootView.findViewById(R.id.toolbar_right_btn);
        if (mToolbar != null) {
            mToolbar.setTitle("");
            if (getMenuId() > 0) {
                mToolbar.inflateMenu(getMenuId());
            }
            mToolbar.setOnMenuItemClickListener(this);
            mToolbar.setNavigationIcon(null);
        }
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
        if (mToolbarLeftBtn != null) {
            mToolbarLeftBtn.setImageResource(leftResId);
            mToolbarLeftBtn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            RxView.clicks(mToolbarLeftBtn).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object object) throws Exception {
                    onNavigationClickListener(mToolbarLeftBtn);
                }
            });
        }
        if (mToolbarRightTv != null) {
            mToolbarRightTv.setText(rightStr);
            RxView.clicks(mToolbarRightTv).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    onToolbarRightClickListener(mToolbarRightTv);
                }
            });
        }
    }

    protected void initToolbar(View rootView, @StringRes int titleStrId,
                               @DrawableRes int leftResId, @StringRes int rightStrId) {
        initToolbar(rootView, getString(titleStrId), leftResId, getString(rightStrId));
    }

    protected void initToolbar(View rootView, @StringRes int titleStrId, @DrawableRes int leftResId) {
        initToolbar(rootView, getString(titleStrId), leftResId);
    }

    protected void initToolbar(View rootView, String title, @DrawableRes int leftResId) {
        mToolbar = rootView.findViewById(R.id.toolbar);
        mToolbarTitle = rootView.findViewById(R.id.toolbar_title);
        mToolbarLeftBtn = rootView.findViewById(R.id.toolbar_left_btn);
        if (mToolbar != null) {
            mToolbar.setTitle("");
            if (getMenuId() > 0) {
                mToolbar.inflateMenu(getMenuId());
            }
            mToolbar.setOnMenuItemClickListener(this);
            mToolbar.setNavigationIcon(null);
        }
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
        if (mToolbarLeftBtn != null) {
            mToolbarLeftBtn.setImageResource(leftResId);
            mToolbarLeftBtn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            RxView.clicks(mToolbarLeftBtn).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object object) throws Exception {
                    onNavigationClickListener(mToolbarLeftBtn);
                }
            });
        }
    }

    protected void initToolbar(View rootView, String title, @DrawableRes int leftResId, @DrawableRes int rightResId) {
        mToolbar = rootView.findViewById(R.id.toolbar);
        mToolbarTitle = rootView.findViewById(R.id.toolbar_title);
        mToolbarLeftBtn = rootView.findViewById(R.id.toolbar_left_btn);
        if (mToolbar != null) {
            mToolbar.setTitle("");
            if (getMenuId() > 0) {
                mToolbar.inflateMenu(getMenuId());
            }
            mToolbar.setOnMenuItemClickListener(this);
            mToolbar.setNavigationIcon(null);
        }
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
        if (mToolbarLeftBtn != null) {
            mToolbarLeftBtn.setImageResource(leftResId);
            mToolbarLeftBtn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            RxView.clicks(mToolbarLeftBtn).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object object) throws Exception {
                    onNavigationClickListener(mToolbarLeftBtn);
                }
            });
        }
        if (mToolbarRightBtn != null) {
            mToolbarRightBtn.setImageResource(leftResId);
            mToolbarRightBtn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            RxView.clicks(mToolbarRightBtn).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object object) throws Exception {
                    onToolbarRightClickListener(mToolbarRightBtn);
                }
            });
        }
    }


    protected void onNavigationClickListener(View view) {

    }

    protected void onToolbarRightClickListener(View view) {

    }

    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        mPresenter = null;
    }

    protected boolean useEventBus() {
        return false;
    }

    protected int getMenuId() {
        return 0;
    }

    protected void onTokenError(TokenInvalid invalid) {
    }

    protected void setupFragmentComponent(AppComponent appComponent) {
    }

    public boolean isNetworkAvailable() {
        return NetworkUtils.isConnected(getContext());
    }


    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState, View view);

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public final SimpleActivity getSimpleActivity() {
        return (SimpleActivity) _mActivity;
    }
}
