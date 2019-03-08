package com.lning.melireader.contact;

import com.lning.melireader.app.event.UpdateUserEvent;

import java.util.Date;

public interface UserUpdateContact {

    interface View {
        void onUpdateInfoSuccess(UpdateUserEvent userEvent);
    }

    interface Presenter<V extends View> {

        void updateUserInfo(int resId, String userId, Long roleId, String nickname, byte gender, String address,
                            Date birthday, String signature);
    }

}
