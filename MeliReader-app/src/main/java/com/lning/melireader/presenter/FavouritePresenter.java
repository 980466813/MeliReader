package com.lning.melireader.presenter;

import com.lning.melireader.contact.FavouriteContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.model.impl.ChannelModel;
import com.lning.melireader.ui.favourite.FavouriteFragment;

import javax.inject.Inject;

/**
 * Created by Ning on 2019/2/5.
 */

public class FavouritePresenter extends BasePresenter<FavouriteFragment, ChannelModel>
        implements FavouriteContact.Presenter<FavouriteFragment> {

    @Inject
    public FavouritePresenter(ChannelModel mMvpModel) {
        super(mMvpModel);
    }
}
