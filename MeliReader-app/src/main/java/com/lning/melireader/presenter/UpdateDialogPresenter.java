package com.lning.melireader.presenter;

import com.lning.melireader.app.event.UpdateUserEvent;
import com.lning.melireader.contact.UserUpdateContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.main.user.info.UpdateDialogFragment;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class UpdateDialogPresenter extends BasePresenter<UpdateDialogFragment, UserModel>
        implements UserUpdateContact.Presenter<UpdateDialogFragment> {

    @Inject
    public UpdateDialogPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void updateUserInfo(final int resId, final String userId, final String nickname, final byte gender, final String address,
                               final Date birthday, final String signature) {
        boolean networkAvailable = mMvpView.isNetworkAvailable();
        if (!networkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
        }
        mMvpView.showDialog("正在更改...");
//        mMvpModel.addSubscribe(mMvpModel.updateUserInfo(userId, nickname, gender, null, birthday, address, signature)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        mMvpView.dismissDialog();
//                        UpdateUserEvent event = null;
//                        if (resId == UpdateUserEvent.NICKNAME_TYPE) {
//                            event = UpdateUserEvent.createNicknameEvent(nickname);
//                        } else if (resId == UpdateUserEvent.ADDRESS_TYPE) {
//                            event = UpdateUserEvent.createAddressEvent(address);
//                        } else if (resId == UpdateUserEvent.BIRTHDAY_TYPE) {
//                            event = UpdateUserEvent.createBirthdayEvent(CommonUtils.formatDate("yyyy-MM-dd", birthday));
//                        } else if (resId == UpdateUserEvent.GENDER_TYPE) {
//                            event = UpdateUserEvent.createGenderEvent(gender == 0 ? "男" : gender == 1 ? "女" : "保密");
//                        } else {
//                            event = UpdateUserEvent.createSignatureEvent(signature);
//                        }
//                        mMvpView.onUpdateInfoSuccess(event);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        handlerApiError(throwable);
//                    }
//                }));
        Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                mMvpView.dismissDialog();
                UpdateUserEvent event = null;
                if (resId == UpdateUserEvent.NICKNAME_TYPE) {
                    event = UpdateUserEvent.createNicknameEvent(nickname);
                } else if (resId == UpdateUserEvent.ADDRESS_TYPE) {
                    event = UpdateUserEvent.createAddressEvent(address);
                } else if (resId == UpdateUserEvent.BIRTHDAY_TYPE) {
                    event = UpdateUserEvent.createBirthdayEvent(CommonUtils.formatDate("yyyy-MM-dd", birthday));
                } else if (resId == UpdateUserEvent.GENDER_TYPE) {
                    event = UpdateUserEvent.createGenderEvent(gender+"");
                } else {
                    event = UpdateUserEvent.createSignatureEvent(signature);
                }
                mMvpView.onUpdateInfoSuccess(event);
            }
        });
    }
}
