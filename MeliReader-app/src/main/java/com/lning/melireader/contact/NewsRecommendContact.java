package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.NewsListVo;

import java.util.List;

/**
 * Created by Ning on 2019/2/20.
 */

public interface NewsRecommendContact {
    interface View {
        void onGetRecommendNewsListSuccess(List<NewsListVo> newsListVos);
    }

    interface Presenter<V extends View> {
        void getRecommendNewsList(String dislikeIds, String ctype);
    }
}
