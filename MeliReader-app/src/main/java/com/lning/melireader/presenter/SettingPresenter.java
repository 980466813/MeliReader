package com.lning.melireader.presenter;

import com.lning.melireader.contact.SettingContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.main.user.setting.SettingFragment;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/17.
 */

public class SettingPresenter extends BasePresenter<SettingFragment, UserModel>
        implements SettingContact.Presenter<SettingFragment> {

    @Inject
    public SettingPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }


    @Override
    public void onVersionControlClick() {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpView.showDialog("正在查询");
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                mMvpView.showMessage("暂无最新版本信息");
                mMvpView.dismissDialog();
            }
        });
    }

    @Override
    public void onLogoutClick(String username) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpView.showDialog("正在查询");
        mMvpModel.addSubscribe(mMvpModel.logout(username, getModel().getLocalPhoneMac())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.onLogoutSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }
}
