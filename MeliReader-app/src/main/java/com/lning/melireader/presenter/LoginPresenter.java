package com.lning.melireader.presenter;

import android.text.TextUtils;

import com.lning.melireader.contact.LoginContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.UserBasicVo;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.RandomUtils;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.login.LoginFragment;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2018/11/20.
 */

public class LoginPresenter extends BasePresenter<LoginFragment, UserModel>
        implements LoginContact.Presenter<LoginFragment> {

    private String mac;

    @Inject
    public LoginPresenter(UserModel userModel) {
        super(userModel);
    }


    @Override
    public void getUserProfileByUsername(String username) {
        mMvpModel.addSubscribe(mMvpModel.getUserProfileByUsername(username)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mMvpView.showUserProfile(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.d(throwable.toString());
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void login(String username, String password) {
        mMvpView.showLoading();
        mac = mMvpModel.getLocalPhoneMac();
        if (TextUtils.isEmpty(mac)) {
            mac = RandomUtils.getRandomStr(16);
            mMvpModel.saveLocalPhoneMac(mac);
        }
        mMvpModel.addSubscribe(mMvpModel.login(username, password, mac)
                .subscribe(new Consumer<UserVo>() {
                    @Override
                    public void accept(UserVo userVo) throws Exception {
                        mMvpView.onLoginSuccess(userVo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }
}

//        if (TextUtils.isEmpty(mac)) {
//            PermissionUtils.readPhoneState(new PermissionUtils.RequestPermission() {
//                @Override
//                public void onRequestPermissionSuccess() {
//                    mac = PhoneUtils.getIMEI(mMvpView.getContext());
//                    LogUtils.d("Request permissions success");
//                }
//
//                @Override
//                public void onRequestPermissionFailure(List<String> permissions) {
//                    LogUtils.d("Request permissions failure");
//                }
//
//                @Override
//                public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
//                    LogUtils.d("Need to go to the settings");
//                }
//            }, mMvpView.getRxPermissions());
//            mac = PhoneUtils.getIMEI(mMvpView.getContext());
//            if (TextUtils.isEmpty(mac)) {
//                mac = RandomUtils.getRandomStr(16);
//            }
//        }
