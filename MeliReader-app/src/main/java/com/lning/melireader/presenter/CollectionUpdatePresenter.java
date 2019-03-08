package com.lning.melireader.presenter;

import com.lning.melireader.contact.CollectionUpdateContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.model.impl.CollectionModel;
import com.lning.melireader.model.impl.TagModel;
import com.lning.melireader.ui.main.user.collection.CollectionUpdateFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/16.
 */

public class CollectionUpdatePresenter extends BasePresenter<CollectionUpdateFragment, CollectionModel>
        implements CollectionUpdateContact.Presenter<CollectionUpdateFragment> {

    @Inject
    TagModel tagModel;

    @Inject
    public CollectionUpdatePresenter(CollectionModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getUserTagList() {
        mMvpModel.addSubscribe(tagModel.getUserTagList()
                .subscribe(new Consumer<List<TagPojo>>() {
                    @Override
                    public void accept(List<TagPojo> tagPojos) throws Exception {
                        mMvpView.showAllUserTagList(tagPojos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void insertTagPojo( final String tag) {
        mMvpModel.addSubscribe(tagModel.insertTagPojo(tag)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LogUtils.d("插入标签成功 : " + tag);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void updateCollection(String newsId, String tag) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpView.showDialog("正在更改");
        mMvpModel.addSubscribe(mMvpModel.updateCollection(newsId, tag)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.onUpdateCollectionTagSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }
}
