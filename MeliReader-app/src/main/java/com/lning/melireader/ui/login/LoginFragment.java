package com.lning.melireader.ui.login;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.LoginContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.repository.http.bean.UserBasicVo;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.ScreenUtils;
import com.lning.melireader.presenter.LoginPresenter;
import com.lning.melireader.ui.main.MainFragment;
import com.lning.melireader.ui.register.RegisterFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by Ning on 2018/11/20.
 */

public class LoginFragment extends BaseFragment<LoginPresenter>
        implements LoginContact.View {
    @BindView(R.id.login_password_et)
    AppCompatEditText loginPasswordEt;

    @BindView(R.id.login_username_et)
    AppCompatEditText loginUsernameEt;

    @BindView(R.id.login_user_icon_iv)
    AppCompatImageView loginUserIconIv;

    @Inject
    RxPermissions rxPermissions;

    private int width;
    private String username;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        width = ScreenUtils.dip2px(getContext(), 40.0F);
    }

    @OnClick(R.id.login_btn)
    public void onLoginClick() {
        String username = loginUsernameEt.getText().toString();
        String password = loginPasswordEt.getText().toString();
        mPresenter.login(username, password);
    }

    @OnClick(R.id.login_register_tv)
    public void onRegisterClick() {
        start(RegisterFragment.newsInstance());
    }

    @OnCheckedChanged(R.id.login_password_cb)
    public void onPasswordShowCbCheck(boolean isChecked) {
        int pos = loginPasswordEt.getSelectionStart();
        loginPasswordEt.setTransformationMethod(isChecked ? HideReturnsTransformationMethod.getInstance()
                : PasswordTransformationMethod.getInstance());            //如果选中，显示密码
        LogUtils.d(isChecked + "");
        loginPasswordEt.setSelection(pos);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent
                .builder().appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @OnFocusChange(R.id.login_username_et)
    public void onLoginUsernameFocusChange(boolean isFocus) {
        LogUtils.d(isFocus + "");
        if (!isFocus) {
            if (loginUsernameEt.getText().toString().equals(username)) {
                return;
            }
            username = loginUsernameEt.getText().toString();
            LogUtils.d(username);
            mPresenter.getUserProfileByUsername(username);
        }
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void showUserProfile(String imagePath) {
        if (!TextUtils.isEmpty(imagePath))
            GlideUtils.loadUserHead(loginUserIconIv, String.format(AppConstant.IMAGE_URL, imagePath), width, width);
    }

    @Override
    public void onLoginSuccess(UserVo userVo) {
        LogUtils.d(userVo.toString());
        EventBus.getDefault().post(userVo);
        start(MainFragment.newInstance(userVo.getFavouriteChannels()), SINGLETASK);
    }

    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }
}
