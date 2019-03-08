package com.lning.melireader.presenter;

import com.lning.melireader.contact.UserInfoContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.main.user.info.UserInfoFragment;

import javax.inject.Inject;

public class UserInfoPresenter extends BasePresenter<UserInfoFragment, UserModel>
        implements UserInfoContact {

    @Inject
    public UserInfoPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }
}
