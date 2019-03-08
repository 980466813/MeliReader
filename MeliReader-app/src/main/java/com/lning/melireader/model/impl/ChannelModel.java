package com.lning.melireader.model.impl;

import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.model.IChannelModel;

import javax.inject.Inject;

/**
 * Created by Ning on 2019/2/5.
 */

public class ChannelModel extends BaseModel
        implements IChannelModel {

    @Inject
    public ChannelModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }


}
