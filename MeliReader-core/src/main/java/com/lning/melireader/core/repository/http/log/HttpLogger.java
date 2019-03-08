package com.lning.melireader.core.repository.http.log;

import com.lning.melireader.core.utils.LogUtils;

import java.util.List;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * Created by Ning on 2018/11/20.
 */

public interface HttpLogger {

    void printHeaders(String contentType, long contentLength, Headers headers);

    void printRequest(String protocol, String method, String params);

    void printResponse(long spendTime, long contentLength, int code, String message);


    HttpLogger DEFAULT = new HttpLogger() {

        private static final String REQUEST = "Protocol: %s ; Method: @ %s ; Params: %s ";
        private static final String BODY = "Body: ";
        private static final String HEADERS = "Headers: ";
        private static final String STATUS_CODE = "Status Code: %s";
        private static final String CONTENT_TYPE = "Content-Type: %s ; Content-Length: %s";

        @Override
        public void printHeaders(String content_type, long content_length, Headers headers) {
            LogUtils.d(String.format(CONTENT_TYPE, content_type, content_length + " b"));
            LogUtils.d(HEADERS);
            for (int i = 0; i < headers.size(); i++) {
                LogUtils.d(headers.name(i) + " : " + headers.value(i));
            }
        }

        @Override
        public void printRequest(String protocol, String method, String params) {
            LogUtils.d(String.format(REQUEST, protocol, method, params));
        }

        @Override
        public void printResponse(long spendTime, long contentLength, int code, String message) {
            LogUtils.d(String.format(STATUS_CODE, code));
            LogUtils.d(String.format(BODY, message));
        }
    };
}
