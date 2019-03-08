package com.lning.melireader.presenter;

import com.lning.melireader.contact.CollectionListContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.CollectionVo;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.model.impl.CollectionModel;
import com.lning.melireader.ui.main.user.collection.CollectionListFragment;
import com.lning.melireader.ui.provider.bean.CollectionPro;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/13.
 */

public class CollectionListPresenter extends BasePresenter<CollectionListFragment, CollectionModel>
        implements CollectionListContact.Presenter<CollectionListFragment> {

    private int mode;
    private ItemListVo mItemList;

    @Inject
    public CollectionListPresenter(CollectionModel mMvpModel) {
        super(mMvpModel);
        mItemList = new ItemListVo();
        mItemList.setPage(1);
        mItemList.setLimit(10);
        mItemList.setEnd(false);
    }


    @Override
    public void initCollectionList(final int mode, String val) {
        isCurRefreshError = false;
        mMvpView.showLoading();
        onLoadMoreCollectionList(mode, val, false);
    }

    @Override
    public void onLoadMoreCollectionList(int mode, final String val, final boolean isCurRefresh) {
        if (isCurRefresh) {
            mItemList.setPage(1);
            mItemList.setLimit(10);
            mItemList.setEnd(false);
        }
        if (mItemList.isEnd()) {
            mMvpView.showMessage("暂无更多收藏记录");
            mMvpView.dismissDialog();
            return;
        }
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
        }
        int limit = mItemList.getLimit();
        mItemList.setLimit(limit <= 0 || limit >= 30 ? 10 : limit);
        mMvpModel.addSubscribe(
                mMvpModel.getCollectionList(mode == 0 ? val : null, mode == 1 ? val : null
                        , isNetworkAvailable, mItemList.getPage(), mItemList.getLimit())
                        .doOnSuccess(new Consumer<ItemListVo>() {
                            @Override
                            public void accept(ItemListVo itemListVo) throws Exception {
                                LogUtils.d(itemListVo.getRows().toString());
                                mItemList.setPage(itemListVo.getPage() + 1);
                                mItemList.setTotalPage(itemListVo.getTotalPage());
                                mItemList.setTotalRows(itemListVo.getTotalRows());
                                mItemList.setEnd(itemListVo.isEnd());
                                mItemList.setFirst(itemListVo.isFirst());
                            }
                        }).map(new Function<ItemListVo, List<CollectionPro>>() {
                    @Override
                    public List<CollectionPro> apply(ItemListVo itemListVo) throws Exception {
                        List<CollectionVo> rows = JsonUtils.jsonToList(JsonUtils.objectToJson(itemListVo.getRows()), CollectionVo.class);
                        List<CollectionPro> collectionPros = new ArrayList<>();
                        for (CollectionVo collectionVo : rows) {
                            CollectionPro collectionPro = new CollectionPro(collectionVo);
                            collectionPros.add(collectionPro);
                        }
                        return collectionPros;
                    }
                })
                        .subscribe(new Consumer<List<CollectionPro>>() {
                            @Override
                            public void accept(List<CollectionPro> rows) throws Exception {
                                mMvpView.dismissDialog();
                                if (rows != null && rows.size() > 0) {
                                    mMvpView.showCollectionList(val, rows, isCurRefresh);
                                    mMvpView.showMain();
                                } else {
                                    mMvpView.showEmpty();
                                    setCurRefreshError(true);
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                handlerApiError(throwable);
                            }
                        }));
    }

    @Override
    public void deleteCollection(String newsId) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.deleteCollection(newsId)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.showMessage("取消收藏成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }


}
