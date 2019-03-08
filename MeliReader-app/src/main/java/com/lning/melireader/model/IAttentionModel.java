package com.lning.melireader.model;

import io.reactivex.Single;

/**
 * Created by Ning on 2019/2/25.
 */

public interface IAttentionModel {

    Single<Boolean> operateAttention(String objectId);
}
