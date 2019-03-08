package com.lning.melireader.core.utils;

import com.lning.melireader.core.app.constant.ResponseCode;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.core.repository.http.exception.ApiException;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ning on 2018/11/15.
 */

public class RxUtils {

    private RxUtils() {
    }

    public static <T> SingleTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> MaybeTransformer<T, T> maybeRxSchedulerHelper() {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Function<String, Result> mappingResponseToResult(final Class<T> clazz) {
        return new Function<String, Result>() {
            @Override
            public Result apply(String s) throws Exception {
                LogUtils.d(s);
                Result result = Result.formatToPojo(s, clazz);
                if (result.getStatus() != 2000) {
                    throw new ApiException(result.getStatus(), result.getMsg());
                }
                return result;
            }
        };
    }

    public static <T> Function<String, Result> mappingResponseToResultList(final Class<T> clazz) {
        return new Function<String, Result>() {
            @Override
            public Result apply(String s) throws Exception {
                Result result = Result.formatToList(s, clazz);
                if (result.getStatus() != 2000) {
                    throw new ApiException(result.getStatus(), result.getMsg());
                }
                return result;
            }
        };
    }

    public static <T> SingleTransformer<Result, T> transformResultToData() {
        return new SingleTransformer<Result, T>() {
            @Override
            public SingleSource<T> apply(Single<Result> upstream) {
                return upstream.map(new Function<Result, T>() {
                    @Override
                    public T apply(Result result) throws Exception {
                        if (result.getStatus() != 2000) {
                            throw new ApiException(result.getStatus(), result.getMsg());
                        }
                        if (result.getData() != null) {
                            return (T) result.getData();
                        } else {
                            throw new ApiException(ResponseCode.DATA_NULL);
                        }
                    }
                });
            }
        };
    }

    public static <T> SingleTransformer<Result, T> mappingResultToData() {
        return new SingleTransformer<Result, T>() {
            @Override
            public SingleSource<T> apply(Single<Result> upstream) {
                return upstream.map(new Function<Result, T>() {
                    @Override
                    public T apply(Result result) throws Exception {
                        if (result.getData() != null) {
                            return (T) result.getData();
                        } else {
                            throw new ApiException(ResponseCode.DATA_NULL);
                        }
                    }
                });
            }
        };
    }

    public static SingleTransformer<Result, Boolean> mappingResultToCheck() {
        return new SingleTransformer<Result, Boolean>() {
            @Override
            public SingleSource<Boolean> apply(Single<Result> upstream) {
                return upstream.map(new Function<Result, Boolean>() {
                    @Override
                    public Boolean apply(Result result) throws Exception {
                        if (result.getStatus() == 2000) {
                            return Boolean.TRUE;
                        } else {
                            throw new ApiException(result.getStatus(), result.getMsg());
                        }
                    }
                });
            }
        };
    }

    public static <T> SingleTransformer<Result, List<T>> mappingResultToList() {
        return new SingleTransformer<Result, List<T>>() {
            @Override
            public SingleSource<List<T>> apply(Single<Result> upstream) {
                return upstream.map(new Function<Result, List<T>>() {
                    @Override
                    public List<T> apply(Result result) throws Exception {
                        if (result.getData() != null) {
                            return (List<T>) result.getData();
                        } else {
                            throw new ApiException(ResponseCode.DATA_NULL);
                        }
                    }
                });
            }
        };
    }

}
