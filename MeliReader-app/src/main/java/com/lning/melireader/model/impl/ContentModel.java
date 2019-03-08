package com.lning.melireader.model.impl;

import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.model.IContentModel;

import javax.inject.Inject;

/**
 * Created by Ning on 2019/2/6.
 */

public class ContentModel extends BaseModel
        implements IContentModel {

    @Inject
    public ContentModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
