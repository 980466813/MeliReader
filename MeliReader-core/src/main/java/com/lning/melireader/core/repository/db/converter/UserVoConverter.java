package com.lning.melireader.core.repository.db.converter;

import android.text.TextUtils;

import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.utils.JsonUtils;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by Ning on 2019/2/12.
 */

public class UserVoConverter implements PropertyConverter<UserVo, String> {
    @Override
    public UserVo convertToEntityProperty(String databaseValue) {
        if (!TextUtils.isEmpty(databaseValue)) {
            return JsonUtils.jsonToPojo(databaseValue, UserVo.class);
        }
        return null;
    }

    @Override
    public String convertToDatabaseValue(UserVo entityProperty) {
        return entityProperty != null ? JsonUtils.objectToJson(entityProperty) : null;
    }
}
