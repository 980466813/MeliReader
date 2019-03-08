package com.lning.melireader.model;

import com.lning.melireader.core.repository.http.bean.UserVo;

import java.util.Date;

import io.reactivex.Single;

/**
 * Created by Ning on 2018/11/20.
 */

public interface IUserModel {


    String getLocalUserId();

    String getLocalPhoneMac();

    String getLocalLoginUserToken();

    Integer getLocalTextSize();

    void updateLocalTextSize(int textSize);

    Boolean getLocalWifiTip();

    void updateLocalWifiTip(boolean wifiTip);

    void saveLoginUserToken(String token);

    void saveLocalPhoneMac(String mac);

    Single<UserVo> getUserInfo(String type, String token);

    Single<UserVo> getUserInfoByUserId(String type, String token, String userId, String ownerId);

    Single<UserVo> login(String username, String password, String mac);

    Single<Boolean> register(String username, String password, String repassword, String nickname, String code);

    Single<String> getUserProfileByUsername(String username);

    Single<Boolean> checkUserUseful(String username);

    Single<UserVo> getUserInfoFromLocal(String type, String token, boolean isLogin);

    /**
     * 需要携带Cookie
     *
     * @return
     */
    Single<Boolean> logout(String username, String mac);

    /**
     * 需要携带Cookie信息
     *
     * @return
     */
    Single<Boolean> updateUserInfo(String userId, String nickname, byte gender, String image, Date birthday, String address, String signature);

    Single<String> getUserFavouriteChannels(String mac);
}
