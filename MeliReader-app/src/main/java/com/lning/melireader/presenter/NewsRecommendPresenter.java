package com.lning.melireader.presenter;

import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.contact.NewsRecommendContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.model.impl.NewsModel;
import com.lning.melireader.ui.main.news.recommend.NewsRecommendFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/20.
 */

public class NewsRecommendPresenter extends BasePresenter<NewsRecommendFragment, NewsModel>
        implements NewsRecommendContact.Presenter<NewsRecommendFragment> {

    @Inject
    public NewsRecommendPresenter(NewsModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getRecommendNewsList(final String dislikeIds, final String ctype) {
        setCurRefreshError(false);
        mMvpView.showLoading();
        boolean networkAvailable = mMvpView.isNetworkAvailable();
        if (!networkAvailable) {
            mMvpView.showError("当前位置电波无法到达...");
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.getRecommendNewsList(dislikeIds, ctype, 1, 6, 0)
                .subscribe(new Consumer<ItemListVo>() {
                    @Override
                    public void accept(ItemListVo itemListVo) throws Exception {
                        List<NewsListVo> rows = JsonUtils.jsonToList(itemListVo.getRows().toString(), NewsListVo.class);
                        mMvpView.onGetRecommendNewsListSuccess(rows);
                        mMvpView.showMain();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        handlerApiError(throwable);
                        mMvpView.showMain();
                    }
                }));
    }
}
