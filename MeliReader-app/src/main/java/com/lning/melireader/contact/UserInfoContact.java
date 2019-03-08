package com.lning.melireader.contact;

import android.content.Context;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.lning.melireader.core.repository.http.bean.UserVo;

import java.io.File;
import java.util.Date;

public interface UserInfoContact {
    interface View {
        void onGetUserDetailInfoSuccess(UserVo userVo);

        void onGetPermissionSuccess(int type);
    }

    interface Presenter<V extends View> {
        void getUserDetailInfo();

        void uploadProfile(UserVo userVo, File file);

        void updateUserBirthday(UserVo userVo, Date date);

        void showTimePickerDialog(Context context, String title, Date date, OnTimeSelectListener listener);

        void clearAllLoginUserInfo();

        void requestPermission(int type);
    }
}
