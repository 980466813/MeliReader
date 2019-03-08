package com.lning.melireader.presenter;

import android.text.TextUtils;

import com.lning.melireader.contact.LauncherContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.FavouriteChannel;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.core.utils.RandomUtils;
import com.lning.melireader.model.impl.FavouriteModel;
import com.lning.melireader.model.impl.ContentModel;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.LauncherFragment;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/6.
 */

public class LauncherPresenter extends BasePresenter<LauncherFragment, ContentModel>
        implements LauncherContact.Presenter<LauncherFragment> {

    private String mac;

    @Inject
    protected UserModel userModel;

    @Inject
    protected FavouriteModel favouriteModel;

    @Inject
    public LauncherPresenter(ContentModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void skipToIndex() {
        mac = userModel.getLocalPhoneMac();
        if (TextUtils.isEmpty(mac)) {
            mac = RandomUtils.getRandomStr(16);
            userModel.saveLocalPhoneMac(mac);
        }
        userModel.addSubscribe(userModel.getUserFavouriteChannels(mac)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mMvpView.goToIndexPage(s);
                    }
                }));
    }

    @Override
    public void getDefaultChannels() {
        mMvpView.showLoading();
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            mMvpView.showMain();
            return;
        }
        boolean isFirstLauncher = favouriteModel.getRepositoryManager().getFirstLauncherStatus();
        if (!isFirstLauncher) {
            mMvpView.showMain();
            return;
        }
        favouriteModel.getRepositoryManager().updateFirstLauncherStatus(false);
        favouriteModel.addSubscribe(favouriteModel.getDefaultFavouriteChannels()
                .subscribe(new Consumer<List<FavouriteChannel>>() {
                    @Override
                    public void accept(List<FavouriteChannel> favouriteChannels) throws Exception {
                        if (favouriteChannels != null && favouriteChannels.size() > 0) {
                            favouriteModel.getRepositoryManager().updateFavouriteChannels(JsonUtils.objectToJson(favouriteChannels));
                        }
                        mMvpView.showMain();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }
}
