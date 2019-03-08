package com.lning.melireader.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.LauncherContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.ToastUtils;
import com.lning.melireader.presenter.LauncherPresenter;
import com.lning.melireader.ui.main.MainFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ning on 2018/11/16.
 */

public class LauncherFragment extends BaseFragment<LauncherPresenter>
        implements LauncherContact.View {

    @BindView(R.id.launcher_show_iv)
    ImageView launcherShowIv;

    @BindView(R.id.launcher_loading_pg)
    ProgressBar launcherLoadingPg;

    @BindView(R.id.launcher_skip_btn)
    AppCompatButton launcherSkipBtn;

    @Inject
    protected RxPermissions rxPermissions;

    public static LauncherFragment newInstance() {
        return new LauncherFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_launcher;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        mPresenter.getDefaultChannels();
    }

    @OnClick(value = R.id.launcher_skip_btn)
    protected void onClick(View view) {
        ToastUtils.show(getContext(), "欢迎使用");
        mPresenter.skipToIndex();
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent
                .builder().appComponent(appComponent)
                .fragmentModule(new FragmentModule(this)).build()
                .inject(this);
    }

    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }

    @Override
    public void showLoading() {
        launcherLoadingPg.setVisibility(View.VISIBLE);
        launcherSkipBtn.setVisibility(View.GONE);
    }

    @Override
    public void showMain() {
        launcherLoadingPg.setVisibility(View.GONE);
        launcherSkipBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void goToIndexPage(String json) {
        startWithPop(MainFragment.newInstance(json));
    }
}
