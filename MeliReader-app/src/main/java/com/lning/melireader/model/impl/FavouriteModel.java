package com.lning.melireader.model.impl;

import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.http.bean.FavouriteChannel;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.model.IFavouriteModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/27.
 */

public class FavouriteModel extends BaseModel
        implements IFavouriteModel {

    @Inject
    public FavouriteModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Single<List<FavouriteChannel>> getDefaultFavouriteChannels() {
        return mRepositoryManager.getDefaultFavouriteChannels()
                .map(new Function<Result, List<FavouriteChannel>>() {
                    @Override
                    public List<FavouriteChannel> apply(Result result) throws Exception {
                        return (List<FavouriteChannel>) result.getData();
                    }
                });
    }
}
