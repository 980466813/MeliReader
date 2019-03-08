package com.lning.melireader.presenter;

import com.lning.melireader.contact.RegisterContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.register.RegisterFragment;

import javax.inject.Inject;

/**
 * Created by Ning on 2019/2/12.
 */

public class RegisterPresenter extends BasePresenter<RegisterFragment,UserModel>
    implements RegisterContact.Presenter<RegisterFragment> {

    @Inject
    public RegisterPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }
}
