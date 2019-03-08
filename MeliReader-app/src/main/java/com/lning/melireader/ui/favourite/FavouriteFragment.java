package com.lning.melireader.ui.favourite;

import android.os.Bundle;
import android.view.View;

import com.lning.melireader.contact.FavouriteContact;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.presenter.FavouritePresenter;

/**
 * Created by Ning on 2019/2/5.
 */

public class FavouriteFragment extends BaseFragment<FavouritePresenter>
    implements FavouriteContact.View{
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {

    }
}
