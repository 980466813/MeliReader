package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.UserVo;

/**
 * Created by Ning on 2018/11/20.
 */

public interface LoginContact {

    interface View {
        void showUserProfile(String imagePath);

        void onLoginSuccess(UserVo userVo);
    }

    interface Presenter<V extends View> {
        void getUserProfileByUsername(String username);

        void login(String username, String password);
    }
}
