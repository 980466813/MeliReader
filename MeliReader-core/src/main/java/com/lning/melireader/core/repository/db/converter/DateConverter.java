package com.lning.melireader.core.repository.db.converter;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Date;

/**
 * Created by Ning on 2019/2/13.
 */

public class DateConverter implements PropertyConverter<Date, Long> {

    @Override
    public Date convertToEntityProperty(Long databaseValue) {
        return databaseValue == null || databaseValue < 0 ? new Date(databaseValue) : new Date();
    }

    @Override
    public Long convertToDatabaseValue(Date entityProperty) {
        return entityProperty == null ? System.currentTimeMillis() : entityProperty.getTime();
    }
}
