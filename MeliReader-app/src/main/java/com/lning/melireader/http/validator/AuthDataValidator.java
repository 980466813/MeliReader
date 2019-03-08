package com.lning.melireader.http.validator;

import com.lning.melireader.core.repository.http.validator.DataValidator;

import java.util.Map;

/**
 * Created by Ning on 2018/11/27.
 */

public class AuthDataValidator implements DataValidator {

    @Override
    public String validateUrl() {
        return null;
    }

    @Override
    public boolean isRequestParamsValidated(Map<String, Object> params) {
        return false;
    }
}
