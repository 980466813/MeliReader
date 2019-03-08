package com.lning.melireader.core.repository.http.interceptor;

import android.text.TextUtils;

import com.lning.melireader.core.app.constant.ResponseCode;
import com.lning.melireader.core.repository.http.bean.Result;
import com.lning.melireader.core.repository.http.params.ParamsAdder;
import com.lning.melireader.core.repository.http.validator.DataValidator;
import com.lning.melireader.core.repository.preference.PreferencesHelper;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.core.utils.LogUtils;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Ning on 2019/2/4.
 */

public class RequestInterceptor implements Interceptor {

    private static final String COOKIE_NAME = "Cookie-Name";

    @Inject
    List<DataValidator> dataValidators;

    @Inject
    List<ParamsAdder> paramsAdders;

    @Inject
    @Named("validate")
    boolean validate;

    @Inject
    PreferencesHelper mPreferencesHelper;


    @Inject
    public RequestInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();

        for (ParamsAdder paramsAdder : paramsAdders) {
            if (paramsAdder.needAddParams(request)) {
                // TODO 执行添加公共参数
            }
        }
        // 是否添加Token至Cookie
        String cookieName = obtainCookieNameFromHeaders(request);
        if (!TextUtils.isEmpty(cookieName)) {
            String token = mPreferencesHelper.getLoginUserToken();
            newBuilder.removeHeader(COOKIE_NAME);
            if (TextUtils.isEmpty(token)) {
                return getResponse(request, JsonUtils.objectToJson(Result.build(ResponseCode.TOKEN_ERROR)));
            }
            String domain = request.url().host();
            if ("localhost".equals(domain)) {
                domain = "";
            }
            LogUtils.d(domain);
            newBuilder.addHeader("Cookie", new Cookie.Builder()
                    .domain(domain)
                    .name(cookieName)
                    .value(token)
                    .path("/")
                    .secure()
                    .build().toString());
        }
        return chain.proceed(newBuilder.build());
    }

    private Response getResponse(Request request, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private String obtainCookieNameFromHeaders(Request request) {
        List<String> headers = request.headers(COOKIE_NAME);
        if (headers == null || headers.size() == 0) {
            return null;
        }
        if (headers.size() > 1) {
            throw new IllegalArgumentException("Only one Cookie-Name in the headers");
        }
        return request.header(COOKIE_NAME);
    }
}
