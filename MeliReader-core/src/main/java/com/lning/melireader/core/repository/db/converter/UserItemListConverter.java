package com.lning.melireader.core.repository.db.converter;

import android.text.TextUtils;

import com.lning.melireader.core.repository.http.bean.ItemListVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.utils.JsonUtils;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by Ning on 2019/2/12.
 */

public class UserItemListConverter implements PropertyConverter<ItemListVo, String> {
    @Override
    public ItemListVo convertToEntityProperty(String databaseValue) {
        if (!TextUtils.isEmpty(databaseValue)) {
            ItemListVo itemListVo = JsonUtils.jsonToPojo(databaseValue, ItemListVo.class);
            if (itemListVo.getTotalRows() > 0) {
                itemListVo.setRows(JsonUtils.jsonToList(JsonUtils.objectToJson(itemListVo.getRows()), NewsListVo.class));
            }
            return itemListVo;
        }
        return null;
    }

    @Override
    public String convertToDatabaseValue(ItemListVo entityProperty) {
        return entityProperty != null ? JsonUtils.objectToJson(entityProperty) : null;
    }
}
