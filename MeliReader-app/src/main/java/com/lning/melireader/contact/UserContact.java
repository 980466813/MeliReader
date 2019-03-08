package com.lning.melireader.contact;

import com.lning.melireader.core.repository.http.bean.UserVo;

/**
 * Created by Ning on 2019/2/24.
 */

public interface UserContact {

    interface Presenter<V extends View> {
        void getUserCommonInfo(String id, String userId);

        void operateAttention(boolean checked, String objectId);
    }

    interface View {
        void onOperateAttentionSuccess(boolean attention);

        void showUserCommonInfo(UserVo userVo);
    }
}
