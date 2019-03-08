package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.UserBasicVo;
import com.lning.melireader.core.repository.http.bean.UserVo;

/**
 * Created by Ning on 2019/2/8.
 */

public interface UserCenterContact {

    interface View {
        void showUserInfo(UserVo userVo);

        void hideUserBasicLayout();
    }

    interface Presenter<V extends View> {
        void getUserBasicInfo();

    }

}
