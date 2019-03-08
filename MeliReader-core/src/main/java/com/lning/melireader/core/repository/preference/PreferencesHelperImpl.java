package com.lning.melireader.core.repository.preference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lning.melireader.annotation.qualifier.PreferencesInfo;
import com.lning.melireader.core.app.MRApplication;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Ning on 2018/11/15.
 */
@Singleton
public class PreferencesHelperImpl implements PreferencesHelper {

    private final String SP_APP_LOCAL_WIFITIP = "SP_APP_LOCAL_WIFITIP";
    private final String SP_APP_LOGIN_USERNAME = "SP_APP_LOGIN_USERNAME";
    private final String SP_APP_FIRST_LAUNCH = "SP_APP_FIRST_LAUNCH";
    private final String SP_APP_LOGIN_TOKEN = "SP_APP_LOGIN_TOKEN";
    private final String SP_APP_PHONE_MAC = "SP_APP_PHONE_MAC";
    private final String SP_APP_LOCAL_TEXTSIZE = "SP_APP_LOCAL_TEXTSIZE";
    private final String SP_APP_LOCAL_FAVOURITE_CHANNELS = "SP_APP_LOCAL_FAVOURITE_CHANNELS";
    public static final String DEFAULT_JSON = "[{ \"channel_id\":\"today\",\"channel_name\":\"推荐\"},{\"channel_id\":\"qzNwaOiu\",\"channel_name\":\"视频\"},{\"channel_id\":\"uZfzng8D\",\"channel_name\":\"娱乐\"},{\"channel_id\":\"uzdwCPr9\",\"channel_name\":\"体育\"},{\"channel_id\":\"ginz8RMu\",\"channel_name\":\"科技\"},{	\"channel_id\":\"DhuTLzPY\",\"channel_name\":\"财经\"},{\"channel_id\":\"FWEyYjbA\",\"channel_name\":\"游戏\"},{\"channel_id\":\"3ldvGc9t\",\"channel_name\":\"旅游\"},{\"channel_id\":\"hgX2tkbi\",\"channel_name\":\"时尚\"},{\"channel_id\":\"cpHEIeqL\",\"channel_name\":\"历史\"}]";

    private final SharedPreferences mPreferences;

    @Inject
    public PreferencesHelperImpl(Application application, @PreferencesInfo String spName) {
        mPreferences = application.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    @Override
    public Boolean getFirstLauncherStatus() {
        return mPreferences.getBoolean(SP_APP_FIRST_LAUNCH, true);
    }

    @Override
    public void updateFirstLauncherStatus(boolean firstLauncher) {
        mPreferences.edit().putBoolean(SP_APP_FIRST_LAUNCH, firstLauncher).apply();
    }

    @Override
    public void saveLoginUserToken(String token) {
        mPreferences.edit().putString(SP_APP_LOGIN_TOKEN, token).apply();
    }

    @Override
    public String getLoginUserToken() {
        return mPreferences.getString(SP_APP_LOGIN_TOKEN, "");
    }

    @Override
    public String getLocalPhoneMac() {
        return mPreferences.getString(SP_APP_PHONE_MAC, "");
    }

    @Override
    public void updateLocalPhoneMac(String mac) {
        mPreferences.edit().putString(SP_APP_PHONE_MAC, mac).apply();
    }

    @Override
    public void saveLoginUserId(String username) {
        mPreferences.edit().putString(SP_APP_LOGIN_USERNAME, username).apply();
    }

    @Override
    public String getLoginUserId() {
        return mPreferences.getString(SP_APP_LOGIN_USERNAME, "");
    }

    @Override
    public Integer getLocalTextSize() {
        return mPreferences.getInt(SP_APP_LOCAL_TEXTSIZE, 1);
    }

    @Override
    public void updateLocalTextSize(Integer textSize) {
        mPreferences.edit().putInt(SP_APP_LOCAL_TEXTSIZE, textSize).apply();
    }

    @Override
    public Boolean getLocalWifiTip() {
        return mPreferences.getBoolean(SP_APP_LOCAL_WIFITIP, true);
    }

    @Override
    public void updateLocalWifiTip(boolean wifiTip) {
        mPreferences.edit().putBoolean(SP_APP_LOCAL_WIFITIP, wifiTip).apply();
    }

    @Override
    public String getLocalFavouriteChannels() {
        return mPreferences.getString(SP_APP_LOCAL_FAVOURITE_CHANNELS, DEFAULT_JSON);
    }

    @Override
    public void updateLocalFavouriteChannels(String favouriteChannels) {
        mPreferences.edit().putString(SP_APP_LOCAL_FAVOURITE_CHANNELS, favouriteChannels).apply();
    }
}
