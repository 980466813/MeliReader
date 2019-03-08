package com.lning.melireader.ui.register;

import android.os.Bundle;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.contact.RegisterContact;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.presenter.RegisterPresenter;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by Ning on 2019/2/12.
 */

public class RegisterFragment extends BaseFragment<RegisterPresenter>
        implements RegisterContact.View {


    public static RegisterFragment newsInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {

    }
}
