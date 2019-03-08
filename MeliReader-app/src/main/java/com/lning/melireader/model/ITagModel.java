package com.lning.melireader.model;

import com.lning.melireader.core.repository.db.pojo.TagPojo;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ning on 2019/2/16.
 */

public interface ITagModel {


    Single<List<TagPojo>> getUserTagList();

    Single<Boolean> insertTagPojo(String tag);
}
