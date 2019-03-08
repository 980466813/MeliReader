package com.lning.melireader.core.mvp;

import com.lning.melireader.core.repository.RepositoryManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ning on 2018/11/19.
 */

public class BaseModel implements IModel {

    protected RepositoryManager mRepositoryManager;
    protected CompositeDisposable mCompositeDisposable;

    public BaseModel(RepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
    }

    @Override
    public boolean canCancelable() {
        return false;
    }

    /**
     * 加入存放 RxJava 任务的容器
     *
     * @param disposable
     */
    @Override
    public void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    @Override
    public void cancelSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }

    @Override
    public void onDestroy() {
        cancelSubscribe();
        mCompositeDisposable = null;
    }

    public RepositoryManager getRepositoryManager() {
        return mRepositoryManager;
    }
}
