package com.lning.melireader.presenter;

import com.lning.melireader.contact.UserContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.model.impl.AttentionModel;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.main.user.detail.UserFragment;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/24.
 */

public class UserPresenter extends BasePresenter<UserFragment, UserModel>
        implements UserContact.Presenter<UserFragment> {

    @Inject
    AttentionModel attentionModel;

    @Inject
    public UserPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getUserCommonInfo(String id, String userId) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.getUserInfoByUserId("basic", mMvpModel.getLocalLoginUserToken(), id, userId)
                .subscribe(new Consumer<UserVo>() {
                    @Override
                    public void accept(UserVo userVo) throws Exception {
                        mMvpView.showUserCommonInfo(userVo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void operateAttention(final boolean checked, String objectId) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpView.showDialog("正在请求");
        attentionModel.addSubscribe(attentionModel.operateAttention(objectId)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.onOperateAttentionSuccess(checked);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }
}
