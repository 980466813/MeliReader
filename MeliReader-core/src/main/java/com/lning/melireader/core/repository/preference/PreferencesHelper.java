package com.lning.melireader.core.repository.preference;

/**
 * Created by Ning on 2018/11/15.
 */

public interface PreferencesHelper {

    Boolean getFirstLauncherStatus();

    void updateFirstLauncherStatus(boolean firstLauncher);

    void saveLoginUserToken(String token);

    String getLoginUserToken();

    String getLocalPhoneMac();

    void updateLocalPhoneMac(String mac);

    void saveLoginUserId(String userId);

    String getLoginUserId();

    Integer getLocalTextSize();

    void updateLocalTextSize(Integer textSize);

    Boolean getLocalWifiTip();

    void updateLocalWifiTip(boolean wifiTip);

    String getLocalFavouriteChannels();

    void updateLocalFavouriteChannels(String favouriteChannels);
}
