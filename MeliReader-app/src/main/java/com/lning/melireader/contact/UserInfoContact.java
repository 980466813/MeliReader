package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.UserVo;

public interface UserInfoContact {
    interface View {
        void onGetUserDetailInfoSuccess(UserVo userVo);
    }

    interface Presenter<V extends View> {
        void updateUserInfo(UserVo userVo);
    }
}
