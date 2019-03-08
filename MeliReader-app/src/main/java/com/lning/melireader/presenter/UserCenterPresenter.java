package com.lning.melireader.presenter;

import android.text.TextUtils;

import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.contact.UserCenterContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.main.user.UserCenterFragment;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/8.
 */

public class UserCenterPresenter extends BasePresenter<UserCenterFragment, UserModel>
        implements UserCenterContact.Presenter<UserCenterFragment> {

    @Inject
    public UserCenterPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }


    @Override
    public void getUserBasicInfo() {
        mMvpModel.addSubscribe(Single.create(new SingleOnSubscribe<UserModel.Wrapper>() {
            @Override
            public void subscribe(SingleEmitter<UserModel.Wrapper> emitter) throws Exception {
                String token = mMvpModel.getLocalLoginUserToken();
                String mac = mMvpModel.getLocalPhoneMac();
                emitter.onSuccess(new UserModel.Wrapper(token, mac));
            }
        }).flatMap(new Function<UserModel.Wrapper, SingleSource<UserVo>>() {
            @Override
            public SingleSource<UserVo> apply(UserModel.Wrapper wrapper) throws Exception {
                if (!wrapper.isLogin) {
                    wrapper.token = wrapper.mac;
                }
                return mMvpModel.getUserInfoFromLocal("basic", wrapper.token, wrapper.isLogin);
            }
        }).subscribe(new Consumer<UserVo>() {
            @Override
            public void accept(UserVo userVo) throws Exception {
                mMvpView.showUserInfo(userVo.getId().startsWith(AppConstant.VIRTUAL_ID + "-") ? null : userVo);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                handlerApiError(throwable);
            }
        }));
    }
}
