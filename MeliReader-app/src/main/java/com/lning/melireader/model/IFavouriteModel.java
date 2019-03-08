package com.lning.melireader.model;

import com.lning.melireader.core.repository.http.bean.FavouriteChannel;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ning on 2019/2/27.
 */

public interface IFavouriteModel {

    Single<List<FavouriteChannel>> getDefaultFavouriteChannels();

}
