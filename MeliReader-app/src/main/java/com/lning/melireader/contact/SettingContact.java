package com.lning.melireader.contact;

/**
 * Created by Ning on 2019/2/11.
 */

public interface SettingContact {

    interface Presenter<V extends View> {

        void onVersionControlClick();

        void onLogoutClick(String username);
    }

    interface View {

        void onLogoutSuccess();
    }

}
