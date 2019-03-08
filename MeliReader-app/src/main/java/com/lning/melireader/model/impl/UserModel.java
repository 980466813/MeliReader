package com.lning.melireader.model.impl;

import android.text.TextUtils;

import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.constant.ResponseCode;
import com.lning.melireader.core.mvp.BaseModel;
import com.lning.melireader.core.repository.RepositoryManager;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.repository.http.exception.ApiException;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.RandomUtils;
import com.lning.melireader.core.utils.RxUtils;
import com.lning.melireader.model.IUserModel;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2018/11/20.
 */
public class UserModel extends BaseModel
        implements IUserModel {

    @Inject
    public UserModel(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public String getLocalUserId() {
        return mRepositoryManager.getLoginUserId();
    }

    @Override
    public String getLocalPhoneMac() {
        return mRepositoryManager.getLocalPhoneMac();
    }

    @Override
    public String getLocalLoginUserToken() {
        return mRepositoryManager.getLoginUserToken();
    }

    @Override
    public Integer getLocalTextSize() {
        return mRepositoryManager.getLocalTextSize();
    }

    @Override
    public void updateLocalTextSize(int textSize) {
        mRepositoryManager.updateLocalTextSize(textSize);
    }

    @Override
    public Boolean getLocalWifiTip() {
        return mRepositoryManager.getLocalWifiTip();
    }

    @Override
    public void updateLocalWifiTip(boolean wifiTip) {
        mRepositoryManager.updateLocalWifiTip(wifiTip);
    }

    @Override
    public void saveLoginUserToken(String token) {
        mRepositoryManager.saveLoginUserToken(token);
    }

    @Override
    public void saveLocalPhoneMac(String mac) {
        mRepositoryManager.updateLocalPhoneMac(mac);
    }

    @Override
    public Single<UserVo> getUserInfo(final String type, final String token) {
        return mRepositoryManager.getUserInfo(type, token)
                .compose(RxUtils.<UserVo>mappingResultToData())
                .doOnSuccess(new Consumer<UserVo>() {
                    @Override
                    public void accept(UserVo userVo) throws Exception {
                        mRepositoryManager.saveLocalUserInfo(type
                                , token, userVo);
                    }
                });
    }

    @Override
    public Single<UserVo> getUserInfoByUserId(final String type, final String token, final String userId, final String ownerId) {
        return mRepositoryManager.getUserInfoByUserId(type, token, userId, ownerId)
                .compose(RxUtils.<UserVo>mappingResultToData())
                .doOnSuccess(new Consumer<UserVo>() {
                    @Override
                    public void accept(UserVo userVo) throws Exception {
//                        mRepositoryManager.saveLocalUserInfo(type
//                                , token, userVo);
                    }
                });
    }

    @Override
    public Single<UserVo> login(final String username, final String password, final String mac) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                if (!CommonUtils.verifyLength(username, 6, 16)) {
                    emitter.onSuccess(Result.build(1, "输入账号长度应为6-16位"));
                } else if (!CommonUtils.verifyLength(password, 6, 16)) {
                    emitter.onSuccess(Result.build(2, "输入密码长度应为6-16位"));
                } else if (CommonUtils.verifySpecialSign(password)) {
                    emitter.onSuccess(Result.build(2, "输入密码包含特殊符号"));
                } else {
                    emitter.onSuccess(Result.success());
                }
            }
        }).flatMap(new Function<Result, Single<UserVo>>() {
            @Override
            public Single<UserVo> apply(Result result) throws Exception {
                if (result.getStatus() != 2000) {
                    throw new ApiException(result.getStatus(), result.getMsg());
                }
                return mRepositoryManager.login(username, password, mac)
                        .compose(RxUtils.<String>mappingResultToData())
                        .doOnSuccess(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                mRepositoryManager.saveLoginUserToken(s);
                            }
                        }).flatMap(new Function<String, SingleSource<UserVo>>() {
                            @Override
                            public SingleSource<UserVo> apply(String s) throws Exception {
                                return getUserInfo("basic", s);
                            }
                        }).doOnSuccess(new Consumer<UserVo>() {
                            @Override
                            public void accept(UserVo userVo) throws Exception {
                                mRepositoryManager.saveLoginUserId(userVo.getId());
                            }
                        });
            }
        });
    }

    @Override
    public Single<Boolean> register(final String username, final String password, final String repassword, final String nickname, final String code) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(SingleEmitter<Result> emitter) throws Exception {
                if (!CommonUtils.verifyPhoneNumber(username)) {
                    emitter.onSuccess(Result.build(1, "输入账号不符合手机号码规则"));
                } else if (!CommonUtils.verifyLength(password, 6, 16)) {
                    emitter.onSuccess(Result.build(2, "输入密码长度应为6-16位"));
                } else if (!CommonUtils.verifySpecialSign(password)) {
                    emitter.onSuccess(Result.build(2, "输入密码包含特殊符号"));
                } else if (!TextUtils.isEmpty(repassword) && repassword.equals(password)) {
                    emitter.onSuccess(Result.build(3, "两次密码不一致"));
                } else if (!TextUtils.isEmpty(nickname) && nickname.length() <= 8) {
                    emitter.onSuccess(Result.build(4, "输入昵称不能为空且长度小于8个字符"));
                } else if (!TextUtils.isEmpty(code)) {
                    emitter.onSuccess(Result.build(5, "输入验证码不能为空"));
                } else {
                    emitter.onSuccess(Result.success());
                }
            }
        }).flatMap(new Function<Result, SingleSource<? extends Boolean>>() {
            @Override
            public SingleSource<? extends Boolean> apply(Result result) throws Exception {
                if (result.getStatus() != 2000) {
                    throw new ApiException(result.getStatus(), result.getMsg());
                }
                return mRepositoryManager.register(username, password, nickname, code)
                        .map(new Function<Result, Boolean>() {
                            @Override
                            public Boolean apply(Result result) throws Exception {
                                return result.getStatus() == 2000;
                            }
                        });
            }
        });
    }

    @Override
    public Single<String> getUserProfileByUsername(final String username) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(final SingleEmitter<String> emitter) throws Exception {
                if (!TextUtils.isEmpty(username)) {
                    mRepositoryManager.getUserProfile(username)
                            .subscribe(new Consumer<Result>() {
                                @Override
                                public void accept(Result result) throws Exception {
                                    if (result.getStatus() == ResponseCode.ERROR.getStatus()) {
                                        emitter.onSuccess((String) result.getData());
                                    } else {
                                        emitter.onError(new ApiException(ResponseCode.NOT_NEED_SHOW_MESSAGE));
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    emitter.onError(throwable);
                                }
                            });
                } else {
                    emitter.onError(new ApiException(ResponseCode.NOT_NEED_SHOW_MESSAGE));
                }
            }
        });
    }

    @Override
    public Single<Boolean> checkUserUseful(final String username) {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final SingleEmitter<Boolean> emitter) throws Exception {
                if (!TextUtils.isEmpty(username)) {
                    mRepositoryManager.checkUserExist(username)
                            .subscribe(new Consumer<Result>() {
                                @Override
                                public void accept(Result result) throws Exception {
                                    if (result.getStatus() == ResponseCode.SUCCESS.getStatus()) {
                                        emitter.onSuccess(true);
                                    } else {
                                        emitter.onError(new ApiException(result.getStatus(), result.getMsg()));
                                    }
                                }
                            });
                } else {
                    emitter.onError(new ApiException(0000, "用户名不能为空值"));
                }
            }
        });
    }

    @Override
    public Single<String> getUserFavouriteChannels(final String mac) {
        return Single.create(new SingleOnSubscribe<Wrapper>() {
            @Override
            public void subscribe(SingleEmitter<Wrapper> emitter) throws Exception {
                String token = mRepositoryManager.getLoginUserToken();
                emitter.onSuccess(new Wrapper(token, mac));
            }
        }).flatMap(new Function<Wrapper, SingleSource<String>>() {
            @Override
            public SingleSource<String> apply(final Wrapper wrapper) throws Exception {
                return Single.create(new SingleOnSubscribe<String>() {
                    @Override
                    public void subscribe(final SingleEmitter<String> emitter) throws Exception {
                        if (!wrapper.isLogin) {
                            wrapper.token = wrapper.mac;
                        }
                        getUserInfoFromLocal("basic", wrapper.token, wrapper.isLogin)
                                .subscribe(new Consumer<UserVo>() {
                                    @Override
                                    public void accept(UserVo userVo) throws Exception {
                                        emitter.onSuccess(userVo.getFavouriteChannels());
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        emitter.onError(throwable);
                                    }
                                });
                    }
                });
            }
        });
    }

    @Override
    public Single<UserVo> getUserInfoFromLocal(final String type, final String token, final boolean isLogin) {
        return mRepositoryManager.getUserInfoFromLocal(type, token)
                .flatMap(new Function<Result, SingleSource<UserVo>>() {
                    @Override
                    public SingleSource<UserVo> apply(Result result) throws Exception {
                        if (result.getStatus() == ResponseCode.SUCCESS.getStatus()) {
                            return Single.just(((UserVo) result.getData()));
                        } else {
                            if (isLogin) {
                                return getUserInfo(type, token);
                            } else {
                                String virtualId = AppConstant.VIRTUAL_ID + "-"
                                        + RandomUtils.getRandomStr(8);
                                String json = mRepositoryManager.getFavouriteChannels();
                                mRepositoryManager.saveLocalUserInfo(type, token
                                        , new UserVo(virtualId, json));
                                mRepositoryManager.saveLoginUserId(virtualId);
                                LogUtils.d(mRepositoryManager.getLoginUserId());
                                return Single.just(new UserVo(virtualId, json));
                            }
                        }
                    }
                });
    }


    @Override
    public Single<Boolean> logout(final String username, final String mac) {
        return mRepositoryManager.logout(username, mac)
                .map(new Function<Result, Boolean>() {
                    @Override
                    public Boolean apply(Result result) throws Exception {
                        return result.getStatus() == 2000;
                    }
                });
    }

    @Override
    public Single<Boolean> updateUserInfo(String userId, String nickname, byte gender, String image, Date birthday, String address, String signature) {
        return mRepositoryManager.updateUserInfo(userId, nickname, gender, image, birthday, address, signature)
                .compose(RxUtils.mappingResultToCheck());
    }

    public static class Wrapper {
        public String token;
        public String mac;
        public boolean isLogin;

        public Wrapper(String token, String mac) {
            this.token = token;
            this.mac = mac;
            this.isLogin = !TextUtils.isEmpty(token);
        }

    }
//    f8379c6e391c487e f8379c6e391c487e
}