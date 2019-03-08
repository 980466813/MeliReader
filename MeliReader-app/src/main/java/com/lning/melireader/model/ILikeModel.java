package com.lning.melireader.model;

import io.reactivex.Single;

/**
 * Created by Ning on 2019/2/21.
 */

public interface ILikeModel {

    Single<Boolean> likeObject(String objectId);

}
